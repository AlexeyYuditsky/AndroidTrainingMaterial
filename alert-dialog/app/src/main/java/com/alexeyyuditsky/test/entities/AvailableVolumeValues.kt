package com.alexeyyuditsky.test.entities

data class AvailableVolumeValues(
    val values: List<Int>,
    val currentIndex: Int
) {

    companion object {
        fun createVolumeValues(currentValue: Int): AvailableVolumeValues {
            val values = (100 downTo 0 step 10)
            val currentIndex = values.indexOf(currentValue)
            return if (currentIndex == -1) {
                val list = (values + currentValue).sortedDescending()
                AvailableVolumeValues(list, list.indexOf(currentValue))
            } else AvailableVolumeValues(values.toList(), currentIndex)
        }
    }

}