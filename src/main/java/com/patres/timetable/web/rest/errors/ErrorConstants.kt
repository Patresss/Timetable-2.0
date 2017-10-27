package com.patres.timetable.web.rest.errors

import java.net.URI

object ErrorConstants {

    val ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure"
    val ERR_VALIDATION = "error.validation"
    val DEFAULT_TYPE: URI = URI.create("http://www.jhipster.tech/problem/problem-with-message")
    val CONSTRAINT_VIOLATION_TYPE: URI = URI.create("http://www.jhipster.tech/problem/contraint-violation")
    val PARAMETERIZED_TYPE: URI = URI.create("http://www.jhipster.tech/problem/parameterized")

}
