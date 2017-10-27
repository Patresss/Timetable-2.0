package com.patres.timetable.config

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module
import com.fasterxml.jackson.module.afterburner.AfterburnerModule

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.zalando.problem.ProblemModule
import org.zalando.problem.validation.ConstraintViolationProblemModule

@Configuration
open class JacksonConfiguration {

    /*
     * Support for Hibernate types in Jackson.
     */
    @Bean
    open fun hibernate5Module(): Hibernate5Module {
        return Hibernate5Module()
    }

    /*
     * Jackson Afterburner module to speed up serialization/deserialization.
     */
    @Bean
    open fun afterburnerModule(): AfterburnerModule {
        return AfterburnerModule()
    }

    /*
     * Module for serialization/deserialization of RFC7807 Problem.
     */
    @Bean
    internal open fun problemModule(): ProblemModule {
        return ProblemModule()
    }

    /*
     * Module for serialization/deserialization of ConstraintViolationProblem.
     */
    @Bean
    internal open fun constraintViolationProblemModule(): ConstraintViolationProblemModule {
        return ConstraintViolationProblemModule()
    }

}
