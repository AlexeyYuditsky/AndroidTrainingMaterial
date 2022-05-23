package com.alexeyyuditsky.room.model.boxes.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.alexeyyuditsky.room.model.boxes.room.entities.AccountBoxSettingDbEntity
import com.alexeyyuditsky.room.model.boxes.room.entities.SettingWithEntitiesTuple

@Dao
interface BoxesDao {

    @Transaction
    @Query(
        "SELECT " +
                "accounts.id as account_id, " +
                "boxes.id as box_id, " +
                "ifnull(accounts_boxes_settings.is_active, 1) as is_active " +
                "FROM accounts " +
                "JOIN boxes " +
                "LEFT JOIN accounts_boxes_settings " +
                "ON accounts_boxes_settings.account_id = accounts.id " +
                "AND accounts_boxes_settings.box_id = boxes.id " +
                "WHERE accounts.id = :accountId"
    )
    fun getBoxesAndSettings(accountId: Long): Flow<List<SettingWithEntitiesTuple>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setActiveFlagForBox(accountBoxSetting: AccountBoxSettingDbEntity)

}
