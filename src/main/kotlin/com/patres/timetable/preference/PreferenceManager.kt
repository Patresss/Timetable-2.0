package com.patres.timetable.preference

import com.patres.timetable.repository.PlaceRepository
import com.patres.timetable.repository.TimetableRepository
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.Month

@Component
open class PreferenceManager(private var placeRepository: PlaceRepository, private var timetableRepository: TimetableRepository) {

    fun calculatePreference(preferenceDependency: PreferenceDependency): Preference {
        val preference = Preference()
        calculatePreferredTeacher(preferenceDependency, preference)
        calculatePreferredPlace(preferenceDependency, preference)
        calculatePreferredDivision(preferenceDependency, preference)
        calculatePreferredSubject(preferenceDependency, preference)
        calculateTooSmallPlace(preferenceDependency, preference)
        calculateTaken(preferenceDependency, preference)
        return preference
    }

    private fun calculatePreferredTeacher(preferenceDependency: PreferenceDependency, preference : Preference) {
        val preferredByDivision = preferenceDependency.division?.preferredTeachers?.mapNotNull { it.id }?.toSet()
        preferredByDivision?.let { preference.getTeacherPreferenceHierarchy(it).forEach { it.preferredByDivision = PreferenceHierarchy.PREFFERRED_POINTS } }

        val preferredByPlace = preferenceDependency.place?.preferredTeachers?.mapNotNull { it.id }?.toSet()
        preferredByPlace?.let { preference.getTeacherPreferenceHierarchy(it).forEach { it.preferredByPlace = PreferenceHierarchy.PREFFERRED_POINTS } }

        val preferredBySubject = preferenceDependency.subject?.preferredTeachers?.mapNotNull { it.id }?.toSet()
        preferredBySubject?.let { preference.getTeacherPreferenceHierarchy(it).forEach { it.preferredBySubject = PreferenceHierarchy.PREFFERRED_POINTS } }
    }

    private fun calculatePreferredPlace(preferenceDependency: PreferenceDependency, preference : Preference) {
        val preferredByDivision = preferenceDependency.division?.preferredPlaces?.mapNotNull { it.id }?.toSet()
        preferredByDivision?.let { preference.getPlacePreferenceHierarchy(it).forEach { it.preferredByDivision = PreferenceHierarchy.PREFFERRED_POINTS } }

        val preferredByTeacher = preferenceDependency.teacher?.preferredPlaces?.mapNotNull { it.id }?.toSet()
        preferredByTeacher?.let { preference.getPlacePreferenceHierarchy(it).forEach { it.preferredByTeacher = PreferenceHierarchy.PREFFERRED_POINTS } }

        val preferredBySubject = preferenceDependency.subject?.preferredPlaces?.mapNotNull { it.id }?.toSet()
        preferredBySubject?.let { preference.getPlacePreferenceHierarchy(it).forEach { it.preferredBySubject = PreferenceHierarchy.PREFFERRED_POINTS } }
    }

    private fun calculatePreferredDivision(preferenceDependency: PreferenceDependency, preference : Preference) {
        val preferredByPlace = preferenceDependency.place?.preferredDivisions?.mapNotNull { it.id }?.toSet()
        preferredByPlace?.let { preference.geDivisionPreferenceHierarchy(it).forEach { it.preferredByPlace = PreferenceHierarchy.PREFFERRED_POINTS } }

        val preferredByTeacher = preferenceDependency.teacher?.preferredDivisions?.mapNotNull { it.id }?.toSet()
        preferredByTeacher?.let { preference.geDivisionPreferenceHierarchy(it).forEach { it.preferredByTeacher = PreferenceHierarchy.PREFFERRED_POINTS } }

        val preferredBySubject = preferenceDependency.subject?.preferredDivisions?.mapNotNull { it.id }?.toSet()
        preferredBySubject?.let { preference.geDivisionPreferenceHierarchy(it).forEach { it.preferredBySubject = PreferenceHierarchy.PREFFERRED_POINTS } }

    }

    private fun calculatePreferredSubject(preferenceDependency: PreferenceDependency, preference : Preference) {
        val preferredByPlace = preferenceDependency.place?.preferredSubjects?.mapNotNull { it.id }?.toSet()
        preferredByPlace?.let { preference.getSubjectPreferenceHierarchy(it).forEach { it.preferredByPlace = PreferenceHierarchy.PREFFERRED_POINTS } }

        val preferredByTeacher = preferenceDependency.teacher?.preferredSubjects?.mapNotNull { it.id }?.toSet()
        preferredByTeacher?.let { preference.getSubjectPreferenceHierarchy(it).forEach { it.preferredByTeacher = PreferenceHierarchy.PREFFERRED_POINTS } }

        val preferredByDivision = preferenceDependency.division?.preferredSubjects?.mapNotNull { it.id }?.toSet()
        preferredByDivision?.let { preference.getSubjectPreferenceHierarchy(it).forEach { it.preferredByDivision = PreferenceHierarchy.PREFFERRED_POINTS } }
    }

    private fun calculateTooSmallPlace(preferenceDependency: PreferenceDependency, preference : Preference) {
        preferenceDependency.division?.numberOfPeople?.let { numberOfPeople ->
            val idOfTooSmallPlaces = placeRepository.findIdByDivisionOwnerIdAndNumberOfSeatsLessThan(preferenceDependency.divisionOwnerId, numberOfPeople)
            preference.getPlacePreferenceHierarchy(idOfTooSmallPlaces).forEach { it.tooSmallPlace = PreferenceHierarchy.TOO_SMALL_PLACE }
        }
    }

    private fun calculateTaken(preferenceDependency: PreferenceDependency, preference : Preference) {
        timetableRepository.findTakenTimetable(preferenceDependency, setOf(LocalDate.of(2018, Month.MARCH, 3)))
    }

}
