package com.patres.timetable.preference

class PreferenceManager(private val preferenceDependency: PreferenceDependency) {

    private val preference = Preference()

    fun calculatePreference(): Preference {
        calculatePreferredTeacher()
        calculatePreferredPlace()
        calculatePreferredDivision()
        calculatePreferredSubject()
        return preference
    }

    private fun calculatePreferredTeacher() {
        val preferredByDivision = preferenceDependency.division?.preferredTeachers?.mapNotNull {it.id }?.toSet()
        preferredByDivision?.let { preference.getTeacherPreferenceHierarchy(it).forEach { it.preferredByDivision = PreferenceHierarchy.PREFFERRED_POINTS } }

        val preferredByPlace = preferenceDependency.place?.preferredTeachers?.mapNotNull {it.id }?.toSet()
        preferredByPlace?.let { preference.getTeacherPreferenceHierarchy(it).forEach { it.preferredByPlace = PreferenceHierarchy.PREFFERRED_POINTS } }

        val preferredBySubject = preferenceDependency.subject?.preferredTeachers?.mapNotNull {it.id }?.toSet()
        preferredBySubject?.let { preference.getTeacherPreferenceHierarchy(it).forEach { it.preferredBySubject = PreferenceHierarchy.PREFFERRED_POINTS } }
    }

    private fun calculatePreferredPlace() {
        val preferredByDivision = preferenceDependency.division?.preferredPlaces?.mapNotNull { it.id }?.toSet()
        preferredByDivision?.let { preference.getPlacePreferenceHierarchy(it).forEach { it.preferredByDivision = PreferenceHierarchy.PREFFERRED_POINTS } }

        val preferredByTeacher = preferenceDependency.teacher?.preferredPlaces?.mapNotNull { it.id }?.toSet()
        preferredByTeacher?.let { preference.getPlacePreferenceHierarchy(it).forEach { it.preferredByTeachers = PreferenceHierarchy.PREFFERRED_POINTS } }

        val preferredBySubject = preferenceDependency.subject?.preferredPlaces?.mapNotNull { it.id }?.toSet()
        preferredBySubject?.let { preference.getPlacePreferenceHierarchy(it).forEach { it.preferredBySubject = PreferenceHierarchy.PREFFERRED_POINTS } }
    }

    private fun calculatePreferredDivision() {
        val preferredByPlace = preferenceDependency.place?.preferredDivisions?.mapNotNull { it.id }?.toSet()
        preferredByPlace?.let { preference.geDivisionPreferenceHierarchy(it).forEach { it.preferredByPlace = PreferenceHierarchy.PREFFERRED_POINTS } }

        val preferredByTeacher = preferenceDependency.teacher?.preferredDivisions?.mapNotNull { it.id }?.toSet()
        preferredByTeacher?.let { preference.geDivisionPreferenceHierarchy(it).forEach { it.preferredByTeachers = PreferenceHierarchy.PREFFERRED_POINTS } }

        val preferredBySubject = preferenceDependency.subject?.preferredDivisions?.mapNotNull { it.id }?.toSet()
        preferredBySubject?.let { preference.geDivisionPreferenceHierarchy(it).forEach { it.preferredBySubject = PreferenceHierarchy.PREFFERRED_POINTS } }

    }

    private fun calculatePreferredSubject() {
        val preferredByPlace = preferenceDependency.place?.preferredSubjects?.mapNotNull { it.id }?.toSet()
        preferredByPlace?.let { preference.getSubjectPreferenceHierarchy(it).forEach { it.preferredByPlace = PreferenceHierarchy.PREFFERRED_POINTS } }

        val preferredByTeacher = preferenceDependency.teacher?.preferredSubjects?.mapNotNull { it.id }?.toSet()
        preferredByTeacher?.let { preference.getSubjectPreferenceHierarchy(it).forEach { it.preferredByTeachers = PreferenceHierarchy.PREFFERRED_POINTS } }

        val preferredByDivision = preferenceDependency.division?.preferredSubjects?.mapNotNull { it.id }?.toSet()
        preferredByDivision?.let { preference.getSubjectPreferenceHierarchy(it).forEach { it.preferredByDivision = PreferenceHierarchy.PREFFERRED_POINTS } }
    }

}
