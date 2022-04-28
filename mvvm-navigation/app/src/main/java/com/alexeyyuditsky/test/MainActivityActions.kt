package com.alexeyyuditsky.test

import android.util.Log

typealias MainActivityAction = (MainActivity) -> Unit // лямбда, которая определяет сами действия

// класс позволяет запускать действия на MainActivity, только тогда когда MainActivity будет доступна,
// если MainActivity недоступна, действие откладывается и ждет пока MainActivity снова станет доступной
class MainActivityActions {

    // поле хранящее ссылку на MainActivity. После сохранения ссылки на MainActivity позволяет
    // выполнять все действия которые хранятся в actions
    var mainActivity: MainActivity? = null
        set(activity) {
            Log.d("MyLog", "$activity")
            field = activity
            if (activity != null) {
                actions.forEach { it(activity) } // все действия будут выполняться с актуальной MainActivity
                actions.clear()
            }
        }

    private val actions = mutableListOf<MainActivityAction>() // список действий

    operator fun invoke(action: MainActivityAction) { // определяется логика по добавлению действий в actions
        val activity = this.mainActivity
        if (activity == null) {
            actions += action
        } else {
            action(activity)
        }
    }

    fun clear() {
        actions.clear()
    }

}