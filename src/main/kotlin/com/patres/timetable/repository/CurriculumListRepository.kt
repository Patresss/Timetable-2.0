package com.patres.timetable.repository

import com.patres.timetable.domain.CurriculumList
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CurriculumListRepository : DivisionOwnerRepository<CurriculumList> {

    @Query("""
        select
            curriculumList
        from
            CurriculumList curriculumList
            left join fetch curriculumList.curriculums
            left join fetch curriculumList.period
            left join fetch curriculumList.divisionOwner
        where
            curriculumList.id =:id
        """)
    fun findOneWithEagerRelationships(@Param("id") id: Long?): CurriculumList?


}
