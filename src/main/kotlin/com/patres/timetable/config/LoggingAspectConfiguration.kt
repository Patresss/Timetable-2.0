package com.patres.timetable.config

import com.patres.timetable.aop.logging.LoggingAspect

import io.github.jhipster.config.JHipsterConstants

import org.springframework.context.annotation.*
import org.springframework.core.env.Environment

@Configuration
@EnableAspectJAutoProxy
open class LoggingAspectConfiguration {

    @Bean
    @Profile(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)
    open fun loggingAspect(env: Environment): LoggingAspect {
        return LoggingAspect(env)
    }
}
