package com.patres.timetable.repository

import com.patres.timetable.domain.Timetable
import com.patres.timetable.preference.PreferenceDependency
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext


interface TimetableCustomRepository {

    fun findTakenTimetable(preferenceDependency: PreferenceDependency, dates: Set<LocalDate>): Set<Timetable>

}
