package com.patres.timetable.preference

class PreferenceManager(private val preferenceDependency: PreferenceDependency) {

    private val preference = Preference()

    fun calculatePreference(): Preference {
        calculatePreferredTeacher()
        return preference
    }

    private fun calculatePreferredTeacher() {
        val preferredTeachersIdByDivision = preferenceDependency.division?.preferredTeachers?.map { it.id }
        preferredTeachersIdByDivision?.let { preference.preferredTeachersId.plus(it) }

        val preferredTeachersIdByPlace = preferenceDependency.place?.preferredTeachers?.map {it.id }
        preferredTeachersIdByPlace?.let { preference.preferredTeachersId.plus(it) }

        val preferredSubjectIdBySubject = preferenceDependency.subject?.preferredTeachers?.map {it.id }
        preferredSubjectIdBySubject?.let { preference.preferredTeachersId.plus(it) }
    }

}
