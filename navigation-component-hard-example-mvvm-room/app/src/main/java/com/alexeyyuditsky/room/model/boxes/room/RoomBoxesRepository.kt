package com.alexeyyuditsky.room.model.boxes.room

import android.graphics.Color
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import com.alexeyyuditsky.room.model.AuthException
import com.alexeyyuditsky.room.model.accounts.AccountsRepository
import com.alexeyyuditsky.room.model.boxes.BoxesRepository
import com.alexeyyuditsky.room.model.boxes.entities.Box
import com.alexeyyuditsky.room.model.boxes.entities.BoxAndSettings
import com.alexeyyuditsky.room.model.boxes.room.entities.AccountBoxSettingDbEntity
import com.alexeyyuditsky.room.model.room.wrapSQLiteException

class RoomBoxesRepository(
    private val accountsRepository: AccountsRepository,
    private val boxesDao: BoxesDao,
    private val ioDispatcher: CoroutineDispatcher
) : BoxesRepository {

    override suspend fun getBoxesAndSettings(onlyActive: Boolean): Flow<List<BoxAndSettings>> {
        return accountsRepository.getAccount()
            .flatMapLatest { account ->
                if (account == null) {
                    flowOf(emptyList())
                } else {
                    queryBoxesAndSettings(account.id)
                }
            }
            .mapLatest { boxAndSettings ->
                if (onlyActive) {
                    boxAndSettings.filter { it.isActive }
                } else {
                    boxAndSettings
                }
            }
    }

    override suspend fun activateBox(box: Box) = wrapSQLiteException(ioDispatcher) {
        setActiveFlagForBox(box, true)
    }

    override suspend fun deactivateBox(box: Box) = wrapSQLiteException(ioDispatcher) {
        setActiveFlagForBox(box, false)
    }

    private fun queryBoxesAndSettings(accountId: Long): Flow<List<BoxAndSettings>> {
        return boxesDao.getBoxesAndSettings(accountId)
            .map { entities ->
                entities.map {
                    BoxAndSettings(
                        box = Box(
                            id = it.boxId,
                            colorName = it.colorName,
                            colorValue = Color.parseColor(it.colorValue)
                        ),
                        isActive = it.isActive,
                    )
                }
            }
    }

    private suspend fun setActiveFlagForBox(box: Box, isActive: Boolean) {
        val account = accountsRepository.getAccount().first() ?: throw AuthException()
        boxesDao.setActiveFlagForBox(
            AccountBoxSettingDbEntity(
                accountId = account.id,
                boxId = box.id,
                isActive = isActive
            )
        )
    }

}