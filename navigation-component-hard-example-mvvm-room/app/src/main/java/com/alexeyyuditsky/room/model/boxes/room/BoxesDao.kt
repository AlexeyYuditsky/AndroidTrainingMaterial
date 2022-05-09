package com.alexeyyuditsky.room.model.boxes.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.alexeyyuditsky.room.model.boxes.room.entities.AccountBoxSettingDbEntity
import com.alexeyyuditsky.room.model.boxes.room.entities.BoxDbEntity

@Dao
interface BoxesDao {

    @Query("SELECT * FROM boxes " +
            "LEFT JOIN accounts_boxes_settings " +
            "ON boxes.id = accounts_boxes_settings.box_id AND accounts_boxes_settings.account_id = :accountId")
    fun getBoxesAndSettings(accountId: Long): Flow<Map<BoxDbEntity, AccountBoxSettingDbEntity?>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setActiveFlagForBox(accountBoxSetting: AccountBoxSettingDbEntity)

}
