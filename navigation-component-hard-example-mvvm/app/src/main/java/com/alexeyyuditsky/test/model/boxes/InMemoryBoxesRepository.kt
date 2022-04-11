package com.alexeyyuditsky.test.model.boxes

import android.graphics.Color
import android.util.Log
import com.alexeyyuditsky.test.R
import com.alexeyyuditsky.test.model.accounts.AccountsRepository
import com.alexeyyuditsky.test.model.boxes.entities.Box
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class InMemoryBoxesRepository(
    private val accountsRepository: AccountsRepository
) : BoxesRepository {

    private val boxes = listOf(
        Box(1, R.string.green, Color.rgb(0, 128, 0)),
        Box(2, R.string.red, Color.rgb(128, 0, 0)),
        Box(3, R.string.blue, Color.rgb(0, 0, 128)),
        Box(4, R.string.yellow, Color.rgb(128, 128, 0)),
        Box(5, R.string.black, Color.rgb(0, 0, 0)),
        Box(6, R.string.violet, Color.rgb(128, 0, 255))
    )

    private val allActiveBoxes: MutableMap<String, MutableSet<Int>> = mutableMapOf()
    private val reconstructFlow = MutableSharedFlow<Unit>(replay = 1).apply { this.tryEmit(Unit) }

    private val activeBoxesFlow: Flow<Set<Int>> = combine(reconstructFlow, accountsRepository.getAccount()) { _, acc ->
        if (acc == null) return@combine emptySet<Int>()
        val activeIds = allActiveBoxes[acc.email] ?: let {
            val newActiveIdsSet = HashSet<Int>(boxes.map { it.id })
            allActiveBoxes[acc.email] = newActiveIdsSet
            newActiveIdsSet
        }
        return@combine HashSet<Int>(activeIds)
    }

    override fun getBoxes(onlyActive: Boolean): Flow<List<Box>> = activeBoxesFlow.map { activeIdentifiers ->
        boxes.filter { if (onlyActive) activeIdentifiers.contains(it.id) else true }
    }

}