package com.patres.timetable.preference.hierarchy

class PreferenceDayOfWeekHierarchy : PreferenceHierarchy() {

    var preferredByLesson = 0

    override var points = 0
        get() = preferredByLesson

}
