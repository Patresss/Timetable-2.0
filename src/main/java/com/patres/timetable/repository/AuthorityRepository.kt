package com.patres.timetable.repository

import com.patres.timetable.domain.Authority

import org.springframework.data.jpa.repository.JpaRepository

interface AuthorityRepository : JpaRepository<Authority, String>
