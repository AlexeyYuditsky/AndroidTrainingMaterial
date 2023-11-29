package com.alexeyyuditsky.recyclerviewtypesoop

interface DVTObject {

    fun type(): DVTType
    fun map(ui: DVTUi)

    class TypeA(
        private val title: String
    ) : DVTObject {
        override fun type(): DVTType = DVTType.TYPE_A
        override fun map(ui: DVTUi) = ui.mapTypeA(title)
    }

    class TypeB(
        private val checked: Boolean,
        private val url: String
    ) : DVTObject {
        override fun type(): DVTType = DVTType.TYPE_B
        override fun map(ui: DVTUi) = ui.mapTypeB(checked, url)
    }
}

enum class DVTType {
    TYPE_A,
    TYPE_B
}

interface DVTUi : DVTUiTypeA, DVTUiTypeB

interface DVTUiTypeA {
    fun mapTypeA(
        title: String
    ): Unit = throw IllegalStateException("cannot be used if type is not A")
}

interface DVTUiTypeB {
    fun mapTypeB(
        checked: Boolean,
        url: String
    ): Unit = throw IllegalStateException("cannot be used if type is not B")
}
