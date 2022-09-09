package com.alexeyyuditsky.test.di

import com.alexeyyuditsky.domain.usecase.GetUserNameUseCase
import com.alexeyyuditsky.domain.usecase.SaveUserNameUseCase
import org.koin.dsl.module

val domainModule = module {

    factory<GetUserNameUseCase> {
        GetUserNameUseCase(userRepository = get())
    }

    factory<SaveUserNameUseCase> {
        SaveUserNameUseCase(userRepository = get())
    }

}