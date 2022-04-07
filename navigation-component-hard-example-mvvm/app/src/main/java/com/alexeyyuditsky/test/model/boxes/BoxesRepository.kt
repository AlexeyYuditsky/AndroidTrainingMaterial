package com.alexeyyuditsky.test.model.boxes

import com.alexeyyuditsky.test.model.boxes.entities.Box
import kotlinx.coroutines.flow.Flow

interface BoxesRepository {

    fun getBoxes(onlyActive: Boolean = false): Flow<List<Box>>

}