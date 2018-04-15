package com.patres.timetable.preference.hierarchy

class PreferenceLessonHierarchy : PreferenceHierarchy() {

    var preferredByDayOfWeek = 0

    override var points = 0
        get() = preferredByDayOfWeek

}
