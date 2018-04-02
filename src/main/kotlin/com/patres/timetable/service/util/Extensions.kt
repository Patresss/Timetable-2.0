package com.patres.timetable.service.util

import org.springframework.data.jpa.repository.JpaRepository
import java.io.Serializable

fun <T, ID : Serializable> JpaRepository<T, ID>.getOneOrNull(id: ID?): T? {
    return if (id == null) null else getOne(id)
}

