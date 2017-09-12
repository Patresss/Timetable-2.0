package com.patres.timetable.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Property specific to JHipster.
 * <p>
 * Property are configured in the application.yml file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

}
