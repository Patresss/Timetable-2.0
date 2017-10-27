package com.patres.timetable.config

import io.github.jhipster.config.JHipsterConstants
import io.github.jhipster.config.liquibase.AsyncSpringLiquibase
import liquibase.integration.spring.SpringLiquibase
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.core.task.TaskExecutor
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories("com.patres.timetable.repository")
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableTransactionManagement
open class DatabaseConfiguration(private val env: Environment) {

    private val log = LoggerFactory.getLogger(DatabaseConfiguration::class.java)

    @Bean
    open fun liquibase(@Qualifier("taskExecutor") taskExecutor: TaskExecutor, dataSource: DataSource, liquibaseProperties: LiquibaseProperties): SpringLiquibase {

        // Use liquibase.integration.spring.SpringLiquibase if you don't want Liquibase to start asynchronously
        val liquibase = AsyncSpringLiquibase(taskExecutor, env)
        liquibase.dataSource = dataSource
        liquibase.changeLog = "classpath:config/liquibase/master.xml"
        liquibase.contexts = liquibaseProperties.contexts
        liquibase.defaultSchema = liquibaseProperties.defaultSchema
        liquibase.isDropFirst = liquibaseProperties.isDropFirst
        if (env.acceptsProfiles(JHipsterConstants.SPRING_PROFILE_NO_LIQUIBASE)) {
            liquibase.setShouldRun(false)
        } else {
            liquibase.setShouldRun(liquibaseProperties.isEnabled)
            log.debug("Configuring Liquibase")
        }
        return liquibase
    }
}
