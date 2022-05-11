package com.alexeyyuditsky.room.model.boxes.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import com.alexeyyuditsky.room.model.boxes.room.entities.AccountBoxSettingDbEntity
import com.alexeyyuditsky.room.model.boxes.room.views.SettingsDbView

@Dao
interface BoxesDao {

    @Query("SELECT * FROM settings_view WHERE account_id = :accountId")
    fun getBoxesAndSettings(accountId: Long): Flow<List<SettingsDbView>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setActiveFlagForBox(accountBoxSetting: AccountBoxSettingDbEntity)

}
