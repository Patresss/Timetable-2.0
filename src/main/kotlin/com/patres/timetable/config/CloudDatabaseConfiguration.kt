package com.patres.timetable.config

import io.github.jhipster.config.JHipsterConstants

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.config.java.AbstractCloudConfig
import org.springframework.context.annotation.*

import javax.sql.DataSource

@Configuration
@Profile(JHipsterConstants.SPRING_PROFILE_CLOUD)
open class CloudDatabaseConfiguration : AbstractCloudConfig() {

    private val log = LoggerFactory.getLogger(CloudDatabaseConfiguration::class.java)

    @Bean
    open fun dataSource(): DataSource {
        log.info("Configuring JDBC datasource from a cloud provider")
        return connectionFactory().dataSource()
    }
}
