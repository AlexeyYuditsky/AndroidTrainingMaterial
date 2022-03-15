package com.alexeyyuditsky.test.foundation

import androidx.annotation.MainThread

typealias SingletonsFactory = () -> List<Any>

object SingletonScopeDependencies {

    private var factory: SingletonsFactory? = null
    private var dependencies: List<Any>? = null

    @MainThread
    fun init(factory: SingletonsFactory) {
        if (this.factory != null) return
        this.factory = factory
    }

    @MainThread
    fun getSingletonScopeDependencies(): List<Any> {
        return dependencies ?: factory?.invoke()
        ?: throw IllegalStateException("Call init() before getting singleton dependencies")
    }

}