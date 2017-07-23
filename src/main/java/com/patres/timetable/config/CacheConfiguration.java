package com.patres.timetable.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.patres.timetable.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.User.class.getName() + ".divisions", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Division.class.getName(), jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Division.class.getName() + ".timetables", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Division.class.getName() + ".divisionPlaces", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Division.class.getName() + ".divisionTeachers", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Division.class.getName() + ".divisionSubjects", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Division.class.getName() + ".divisionLessons", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Division.class.getName() + ".divisionPeriods", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Division.class.getName() + ".divisionProperties", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Division.class.getName() + ".parents", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Division.class.getName() + ".users", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Division.class.getName() + ".preferredTeachers", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Division.class.getName() + ".preferredSubjects", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Division.class.getName() + ".preferredPlaces", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Properties.class.getName(), jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Teacher.class.getName(), jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Teacher.class.getName() + ".timetables", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Teacher.class.getName() + ".preferredSubjects", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Teacher.class.getName() + ".preferredDivisions", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Teacher.class.getName() + ".preferredPlaces", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Subject.class.getName(), jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Subject.class.getName() + ".timetables", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Subject.class.getName() + ".preferredTeachers", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Subject.class.getName() + ".preferredDivisions", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Subject.class.getName() + ".preferredPlaces", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Place.class.getName(), jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Place.class.getName() + ".timetables", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Place.class.getName() + ".preferredSubjects", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Place.class.getName() + ".preferredDivisions", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Place.class.getName() + ".preferredTeachers", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Timetable.class.getName(), jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Lesson.class.getName(), jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Lesson.class.getName() + ".timetables", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Period.class.getName(), jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Period.class.getName() + ".intervalTimes", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Period.class.getName() + ".timetables", jcacheConfiguration);
            cm.createCache(com.patres.timetable.domain.Interval.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
