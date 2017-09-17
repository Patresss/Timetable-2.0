package com.patres.timetable

import com.patres.timetable.config.ApplicationProperties
import com.patres.timetable.config.DefaultProfileUtil
import io.github.jhipster.config.JHipsterConstants
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.actuate.autoconfigure.MetricFilterAutoConfiguration
import org.springframework.boot.actuate.autoconfigure.MetricRepositoryAutoConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.ComponentScan
import org.springframework.core.env.Environment

import javax.annotation.PostConstruct
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.Arrays

@ComponentScan
@EnableAutoConfiguration(exclude = arrayOf(MetricFilterAutoConfiguration::class, MetricRepositoryAutoConfiguration::class))
@EnableConfigurationProperties(LiquibaseProperties::class, ApplicationProperties::class)
@EnableDiscoveryClient
open class TimetableApp(private val env: Environment) {

    /**
     * Initializes Timetable.
     *
     *
     * Spring profiles can be configured with a program arguments --spring.profiles.active=your-active-profile
     *
     *
     * You can find more information on how profiles work with JHipster on [https://jhipster.github.io/profiles/](https://jhipster.github.io/profiles/).
     */
    @PostConstruct
    fun initApplication() {
        val activeProfiles = Arrays.asList(*env.activeProfiles)
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
            log.error("You have misconfigured your application! It should not run " + "with both the 'dev' and 'prod' profiles at the same time.")
        }
        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT) && activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_CLOUD)) {
            log.error("You have misconfigured your application! It should not " + "run with both the 'dev' and 'cloud' profiles at the same time.")
        }
    }

    companion object {
        internal val log = LoggerFactory.getLogger(TimetableApp::class.java)
    }
}


/**
 * Main method, used to run the application.

 * @param args the command line arguments
 * *
 * @throws UnknownHostException if the local host name could not be resolved into an address
 */
fun main(args: Array<String>) {
    val app = SpringApplication(TimetableApp::class.java)
    DefaultProfileUtil.addDefaultProfile(app)
    val env = app.run(*args).environment
    var protocol = "http"
    if (env.getProperty("server.ssl.key-store") != null) {
        protocol = "https"
    }
    TimetableApp.log.info("\n----------------------------------------------------------\n\t" +
        "Application '{}' is running! Access URLs:\n\t" +
        "Local: \t\t{}://localhost:{}\n\t" +
        "External: \t{}://{}:{}\n\t" +
        "Profile(s): \t{}\n----------------------------------------------------------",
        env.getProperty("spring.application.name"),
        protocol,
        env.getProperty("server.port"),
        protocol,
        InetAddress.getLocalHost().hostAddress,
        env.getProperty("server.port"),
        env.activeProfiles)
}
