package com.patres.timetable.config

import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
open class DateTimeFormatConfiguration : WebMvcConfigurerAdapter() {

    override fun addFormatters(registry: FormatterRegistry?) {
        val registrar = DateTimeFormatterRegistrar()
        registrar.setUseIsoFormat(true)
        registrar.registerFormatters(registry!!)
    }
}
