package com.patres.timetable.excpetion

enum class ExceptionMessage(val message: String) {

    TIMETABLES_MUST_HAVE_THIS_SAME_DAY("Timetables must have this same day of week"),
    TIMETABLES_MUST_HAVE_THIS_SAME_DAY_AND_DIVISION("Timetables must have this same day of week and division"),
    TIMETABLES_MUST_HAVE_THIS_SAME_DIVISION("Timetables must have this samedivision")
}
