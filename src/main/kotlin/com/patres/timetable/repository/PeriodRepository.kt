package com.patres.timetable.repository

import com.patres.timetable.domain.Period
import com.patres.timetable.domain.Place
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PeriodRepository : DivisionOwnerRepository<Period> {

    @Query("select period from Period period left join fetch period.intervalTimes where period.id =:id")
    fun findOneWithIntervals(@Param("id") id: Long?): Period?

}
