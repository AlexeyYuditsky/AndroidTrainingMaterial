package com.alexeyyuditsky.room.model.boxes.room

import android.util.Log
import com.alexeyyuditsky.room.model.AuthException
import com.alexeyyuditsky.room.model.accounts.AccountsRepository
import com.alexeyyuditsky.room.model.boxes.BoxesRepository
import com.alexeyyuditsky.room.model.boxes.entities.Box
import com.alexeyyuditsky.room.model.boxes.entities.BoxAndSettings
import com.alexeyyuditsky.room.model.boxes.room.entities.AccountBoxSettingDbEntity
import com.alexeyyuditsky.room.model.room.wrapSQLiteException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

class RoomBoxesRepository(
    private val accountsRepository: AccountsRepository,
    private val boxesDao: BoxesDao,
    private val ioDispatcher: CoroutineDispatcher
) : BoxesRepository {

    override suspend fun getBoxesAndSettings(onlyActive: Boolean): Flow<List<BoxAndSettings>> {
        return accountsRepository.getAccount() // = Flow(Account(id=1, username=admin, email=admin, createdAt=0))
            .flatMapLatest { account ->
                if (account == null) return@flatMapLatest flowOf(emptyList())
                queryBoxesAndSettings(account.id)
            }  // Flow<List<BoxAndSettings>>
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
        return boxesDao.getBoxesAndSettings(accountId) // Flow<List<SettingWithEntitiesTuple>>
            .map { entities ->
                entities.map {
                    val boxEntity = it.boxDbEntity
                    val accountBoxSettings = it.accountBoxSettingDbEntity
                    BoxAndSettings(
                        box = boxEntity.toBox(),
                        isActive = accountBoxSettings.isActive
                    )
                }
            } // Flow<List<BoxAndSettings>>
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