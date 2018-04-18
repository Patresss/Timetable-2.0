package com.patres.timetable.repository

import com.patres.timetable.domain.Place
import com.patres.timetable.domain.Subject
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface SubjectRepository : DivisionOwnerRepository<Subject> {

    @Query("select subject from Subject subject left join fetch subject.preferredPlaces left join fetch subject.preferredDivisions left join fetch subject.preferenceSubjectByTeacher where subject.id =:id")
    fun findOneWithPreference(@Param("id") id: Long?): Subject?

}
