package com.patres.timetable.config

import org.apache.commons.lang3.CharEncoding
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.*
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

@Configuration
open class ThymeleafConfiguration {

    companion object {
        private val log = LoggerFactory.getLogger(ThymeleafConfiguration::class.java)
    }


    @Bean
    @Description("Thymeleaf template resolver serving HTML 5 emails")
    open fun emailTemplateResolver(): ClassLoaderTemplateResolver {
        val emailTemplateResolver = ClassLoaderTemplateResolver()
        emailTemplateResolver.prefix = "mails/"
        emailTemplateResolver.suffix = ".html"
        emailTemplateResolver.templateMode = "HTML5"
        emailTemplateResolver.characterEncoding = CharEncoding.UTF_8
        emailTemplateResolver.order = 1
        return emailTemplateResolver
    }
}
