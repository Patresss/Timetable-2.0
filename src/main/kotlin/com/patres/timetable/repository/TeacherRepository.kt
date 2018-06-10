package com.patres.timetable.repository

import com.patres.timetable.domain.Place
import com.patres.timetable.domain.Teacher
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TeacherRepository : DivisionOwnerRepository<Teacher> {

    @Query("""
        select distinct
            teacher
        from
            Teacher teacher
            left join fetch teacher.preferenceDateTimeForTeachers
            left join fetch teacher.preferenceTeacherByPlace
            left join fetch teacher.preferenceSubjectByTeacher
        """)
    fun findAllWithEagerRelationships(): List<Teacher>

    @Query("""
        select
            teacher
        from
            Teacher teacher
            left join fetch teacher.preferenceDateTimeForTeachers
            left join fetch teacher.preferenceTeacherByPlace
            left join fetch teacher.preferenceSubjectByTeacher
        where
            teacher.id =:id
        """)
    fun findOneWithEagerRelationships(@Param("id") id: Long?): Teacher?

    @Query("""
        select
            teacher
        from
            Teacher teacher
            left join fetch teacher.preferenceDateTimeForTeachers
            left join fetch teacher.preferencesTeacherByDivision
            left join fetch teacher.preferenceTeacherByPlace
            left join fetch teacher.preferenceSubjectByTeacher
        where
            teacher.id =:id
            """)
    fun findOneWithPreference(@Param("id") id: Long?): Teacher?

    @Query("""
        select
            teacher
        from
            Teacher teacher
            left join fetch teacher.divisionOwner
            left join fetch teacher.preferenceDateTimeForTeachers
            left join fetch teacher.preferencesTeacherByDivision
            left join fetch teacher.preferenceTeacherByPlace
            left join fetch teacher.preferenceSubjectByTeacher
        where
            teacher.divisionOwner.id =:divisionOwnerId
            """)
    fun findByDivisionOwnerIdWithPreference(@Param("divisionOwnerId") id: Long?): Place?

}
