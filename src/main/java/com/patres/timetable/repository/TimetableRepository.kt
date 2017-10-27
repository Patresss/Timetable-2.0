package com.patres.timetable.repository

import com.patres.timetable.domain.Timetable
import org.springframework.stereotype.Repository

import org.springframework.data.jpa.repository.*

@Repository
interface TimetableRepository : JpaRepository<Timetable, Long>
