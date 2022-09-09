package com.alexeyyuditsky.test.di

import com.alexeyyuditsky.data.repository.UserRepositoryImpl
import com.alexeyyuditsky.data.storage.UserStorage
import com.alexeyyuditsky.data.storage.sharedpref.SharedPrefUserStorage
import com.alexeyyuditsky.domain.repository.UserRepository
import org.koin.dsl.module

val dataModule = module {

    single<UserStorage> {
        SharedPrefUserStorage(context = get())
    }

    single<UserRepository> {
        UserRepositoryImpl(userStorage = get())
    }

}