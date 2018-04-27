package com.patres.timetable.repository

import com.patres.timetable.domain.Place
import com.patres.timetable.domain.Subject
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface SubjectRepository : DivisionOwnerRepository<Subject> {

    @Query("""
        select
            subject
        from
            Subject subject
            left join fetch subject.preferenceSubjectByPlace
            left join fetch subject.preferencesSubjectByDivision
            left join fetch subject.preferenceSubjectByTeacher
            left join fetch subject.preferencesDataTimeForSubject
        where
            subject.id =:id
        """)
    fun findOneWithPreference(@Param("id") id: Long?): Subject?

    @Query("""
        select
            subject
        from
            Subject subject
            left join fetch subject.divisionOwner
            left join fetch subject.preferenceSubjectByPlace
            left join fetch subject.preferencesSubjectByDivision
            left join fetch subject.preferenceSubjectByTeacher
            left join fetch subject.preferencesDataTimeForSubject
        where
            subject.divisionOwner.id =:divisionOwnerId
        """)
    fun findByDivisionOwnerIdWithPreference(@Param("divisionOwnerId") id: Long?): Place?

}
