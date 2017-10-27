package com.patres.timetable.repository

import com.patres.timetable.domain.Subject
import com.patres.timetable.domain.Teacher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

import org.springframework.data.jpa.repository.*

@Repository
interface SubjectRepository : DivisionOwnerRepository<Subject>
