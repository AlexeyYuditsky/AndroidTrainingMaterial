package com.alexeyyuditsky.test.data.storage.sharedpref

import android.content.Context
import com.alexeyyuditsky.test.data.storage.model.User
import com.alexeyyuditsky.test.data.storage.UserStorage

class SharedPrefUserStorage(context: Context) : UserStorage {

    private val sharedPref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    override fun save(user: User): Boolean {
        sharedPref.edit().putString(KEY_NAME, user.firstName).apply()
        return true
    }

    override fun get(): User {
        val firstName = sharedPref.getString(KEY_NAME, "")!!
        return User(firstName = firstName, lastName = "")
    }

    private companion object {
        const val SHARED_PREF_NAME = "shared_pref"
        const val KEY_NAME = "name"
    }

}