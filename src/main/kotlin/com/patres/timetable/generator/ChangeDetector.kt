package com.patres.timetable.generator

class ChangeDetector {

    private var currentValue: Int = 0
    private var previousValue: Int = 0

    var diffrentInValue: Int = 0
        get() = previousValue - currentValue

    fun updateValue(newValue: Int) {
        previousValue = currentValue
        currentValue = newValue
    }

    fun hasChange(): Boolean {
        return previousValue != currentValue
    }
}
