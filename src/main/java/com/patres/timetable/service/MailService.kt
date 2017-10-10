package com.patres.timetable.service

import com.patres.timetable.domain.User
import io.github.jhipster.config.JHipsterProperties
import org.apache.commons.lang3.CharEncoding
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.MessageSource
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring4.SpringTemplateEngine
import java.util.*

@Service
open class MailService(
    private val jHipsterProperties: JHipsterProperties,
    private val javaMailSender: JavaMailSender,
    private val messageSource: MessageSource,
    private val templateEngine: SpringTemplateEngine) {

    companion object {
        private val USER = "user"
        private val BASE_URL = "baseUrl"
        private val log: Logger = LoggerFactory.getLogger(MailService::class.java)
    }

    @Async
    open fun sendEmail(to: String?, subject: String, content: String, isMultipart: Boolean, isHtml: Boolean) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content)

        // Prepare message using a Spring helper
        val mimeMessage = javaMailSender.createMimeMessage()
        try {
            val message = MimeMessageHelper(mimeMessage, isMultipart, CharEncoding.UTF_8)
            message.setTo(to!!)
            message.setFrom(jHipsterProperties.mail.from)
            message.setSubject(subject)
            message.setText(content, isHtml)
            javaMailSender.send(mimeMessage)
            log.debug("Sent email to User '{}'", to)
        } catch (e: Exception) {
            if (log.isDebugEnabled) {
                log.warn("Email could not be sent to user '{}'", to, e)
            } else {
                log.warn("Email could not be sent to user '{}': {}", to, e.message)
            }
        }

    }

    @Async
    open fun sendEmailFromTemplate(user: User, templateName: String, titleKey: String) {
        val locale = Locale.forLanguageTag(user.langKey!!)
        val context = Context(locale)
        context.setVariable(USER, user)
        context.setVariable(BASE_URL, jHipsterProperties.mail.baseUrl)
        val content = templateEngine.process(templateName, context)
        val subject = messageSource.getMessage(titleKey, null, locale)
        sendEmail(user.email, subject, content, false, true)

    }

    @Async
    open fun sendActivationEmail(user: User) {
        log.debug("Sending activation email to '{}'", user.email)
        sendEmailFromTemplate(user, "activationEmail", "email.activation.title")
    }

    @Async
    open fun sendCreationEmail(user: User) {
        log.debug("Sending creation email to '{}'", user.email)
        sendEmailFromTemplate(user, "creationEmail", "email.activation.title")
    }

    @Async
    open fun sendPasswordResetMail(user: User) {
        log.debug("Sending password reset email to '{}'", user.email)
        sendEmailFromTemplate(user, "passwordResetEmail", "email.reset.title")
    }


}
