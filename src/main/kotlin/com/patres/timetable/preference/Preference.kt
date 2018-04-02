package com.patres.timetable.preference

class Preference {

    val preferredTeachersId: Set<Long> = HashSet()
    val preferredSubjectsId: Set<Long> = HashSet()
    val preferredPlacesId: Set<Long> = HashSet()
    val preferredDivisionsId: Set<Long> = HashSet()

    val tooSmallPlacesId: Set<Long> = HashSet()

    val takenTeachersId: Set<Long> = HashSet()
    val takenPlacesId: Set<Long> = HashSet()
    val takenDivisionsId: Set<Long> = HashSet()

}
