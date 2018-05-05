package com.patres.timetable.generator

class ChangeDetector {

    private var currentValue: Int? = null
    private var previousValue: Int? = null

    fun updateValue(newValue: Int) {
        previousValue = currentValue
        currentValue = newValue
    }

    fun hasChange(): Boolean {
        return previousValue != currentValue
    }
}
