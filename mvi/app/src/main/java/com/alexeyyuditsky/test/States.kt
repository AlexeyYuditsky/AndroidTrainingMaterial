package com.alexeyyuditsky.test

sealed class State {
    object Loading : State()
    class Loaded(val message: Int) : State()
    class Error(val message: Int) : State()
}