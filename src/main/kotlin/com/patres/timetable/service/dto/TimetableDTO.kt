package com.patres.timetable.service.dto


import com.patres.timetable.domain.enumeration.EventType
import com.patres.timetable.preference.Preference
import com.patres.timetable.preference.hierarchy.PreferenceTimetableHierarchy
import java.io.Serializable
import java.time.LocalDate
import java.util.*
import javax.persistence.Column
import javax.persistence.Transient
import javax.validation.constraints.NotNull

class TimetableDTO(

    var title: String? = null,

    var startTimeString: String? = null,

    var endTimeString: String? = null,

    var startDate: LocalDate? = null,

    var endDate: LocalDate? = null,

    var date: LocalDate? = null,

    @get:NotNull
    var type: EventType? = null,

    var everyWeek: Long = 1,

    var startWithWeek: Long = 1,

    var description: String? = null,

    var colorBackground: String? = null,

    var colorBackgroundForTeacher: String? = null,

    var colorBackgroundForDivision: String? = null,

    var colorBackgroundForSubject: String? = null,

    var colorBackgroundForPlace: String? = null,

    var colorText: String? = null,

    var dayOfWeek: Int? = null,

    var placeId: Long? = null,

    var placeName: String? = null,

    var placeShortName: String? = null,

    var subjectId: Long? = null,

    var subjectName: String? = null,

    var subjectShortName: String? = null,

    var teacherId: Long? = null,

    var teacherFullName: String? = null,

    var teacherShortName: String? = null,

    var divisionId: Long? = null,

    var divisionName: String? = null,

    var divisionShortName: String? = null,

    var lessonId: Long? = null,

    var lessonName: String? = null,

    var periodId: Long? = null,

    var periodName: String? = null,

    var points: Int = 0,

    var preferenceTimetableHierarchy: PreferenceTimetableHierarchy = PreferenceTimetableHierarchy(),

    divisionOwnerId: Long? = null

) : AbstractDivisionOwnerDTO(divisionOwnerId = divisionOwnerId), Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || javaClass != other.javaClass) {
            return false
        }

        val timetableDTO = other as TimetableDTO?
        if (timetableDTO!!.id == null || id == null) {
            return false
        }
        return id == timetableDTO.id
    }

    override fun hashCode(): Int {
        return Objects.hashCode(id)
    }

    override fun toString(): String {
        return "TimetableDTO(title=$title, startTimeString=$startTimeString, endTimeString=$endTimeString, startDate=$startDate, endDate=$endDate, date=$date, value=$type, everyWeek=$everyWeek, startWithWeek=$startWithWeek, description=$description, colorBackground=$colorBackground, colorText=$colorText, dayOfWeek=$dayOfWeek, placeId=$placeId, placeName=$placeName, subjectId=$subjectId, subjectName=$subjectName, subjectShortName=$subjectShortName, teacherId=$teacherId, teacherFullName=$teacherFullName, divisionId=$divisionId, divisionName=$divisionName, lessonId=$lessonId, lessonName=$lessonName, periodId=$periodId, periodName=$periodName)"
    }


}
