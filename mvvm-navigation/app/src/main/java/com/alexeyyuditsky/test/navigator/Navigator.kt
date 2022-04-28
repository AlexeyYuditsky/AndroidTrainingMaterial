package com.alexeyyuditsky.test.navigator

import androidx.annotation.StringRes
import com.alexeyyuditsky.test.base.BaseScreen

interface Navigator {

    // т.к. viewModel не должна иметь ссылки на андройд зависимости (активити ил фрагменты), мы
    // используем абстракцию (интерфейс) BaseScreen благодаря которой определяем родителя для запускаемых экранов
    fun launch(screen: BaseScreen)

    fun goBack(result: Any? = null)

    fun toast(@StringRes messageRes: Int)

    fun getString(@StringRes messageRes: Int): String

}