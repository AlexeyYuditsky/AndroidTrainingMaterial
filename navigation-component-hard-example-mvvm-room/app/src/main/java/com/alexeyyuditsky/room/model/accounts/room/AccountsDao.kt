package com.alexeyyuditsky.room.model.accounts.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.alexeyyuditsky.room.model.accounts.room.entities.AccountDbEntity
import com.alexeyyuditsky.room.model.accounts.room.entities.AccountSignInTuple
import com.alexeyyuditsky.room.model.accounts.room.entities.AccountUpdateUsernameTuple

@Dao
interface AccountsDao {

    @Query("SELECT id, password FROM accounts WHERE email = :email")
    suspend fun findByEmail(email: String): AccountSignInTuple?

    @Update(entity = AccountDbEntity::class)
    suspend fun updateUsername(account: AccountUpdateUsernameTuple)

    @Insert
    suspend fun createAccount(accountDbEntity: AccountDbEntity)

    @Query("SELECT * FROM accounts WHERE id = :accountId")
    fun getById(accountId: Long): Flow<AccountDbEntity?>

}