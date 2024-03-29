package com.patres.timetable.config

import io.github.jhipster.config.JHipsterProperties
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import org.ehcache.expiry.Duration
import org.ehcache.expiry.Expirations
import org.ehcache.jsr107.Eh107Configuration
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
@EnableCaching
@AutoConfigureAfter(value = [(MetricsConfiguration::class)])
@AutoConfigureBefore(value = [(WebConfigurer::class), (DatabaseConfiguration::class)])
open class CacheConfiguration(jHipsterProperties: JHipsterProperties) {

    private val jcacheConfiguration: javax.cache.configuration.Configuration<Any, Any>

    init {
        val ehcache = jHipsterProperties.cache.ehcache

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Any::class.java, Any::class.java,
                ResourcePoolsBuilder.heap(ehcache.maxEntries))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.timeToLiveSeconds.toLong(), TimeUnit.SECONDS)))
                .build())
    }

    @Bean
    open fun cacheManagerCustomizer(): JCacheManagerCustomizer {
        return JCacheManagerCustomizer {
            it.createCache(com.patres.timetable.domain.User::class.java.name, jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.User::class.java.name + ".divisions", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Authority::class.java.name, jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.User::class.java.name + ".authorities", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Division::class.java.name, jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Division::class.java.name + ".timetables", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Division::class.java.name + ".divisionPlaces", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Division::class.java.name + ".divisionTeachers", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Division::class.java.name + ".divisionSubjects", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Division::class.java.name + ".divisionLessons", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Division::class.java.name + ".divisionPeriods", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Division::class.java.name + ".divisionProperties", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Division::class.java.name + ".parents", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Division::class.java.name + ".users", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Division::class.java.name + ".preferencesTeacherByDivision", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Division::class.java.name + ".preferencesSubjectByDivision", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Division::class.java.name + ".preferenceDivisionByPlace", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Division::class.java.name + ".preferencesDateTimeForDivision", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Property::class.java.name, jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Teacher::class.java.name, jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Teacher::class.java.name + ".timetables", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Teacher::class.java.name + ".preferenceSubjectByTeacher", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Teacher::class.java.name + ".preferencesTeacherByDivision", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Teacher::class.java.name + ".preferenceTeacherByPlace", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Teacher::class.java.name + ".preferenceDateTimeForTeachers", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Subject::class.java.name, jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Subject::class.java.name + ".timetables", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Subject::class.java.name + ".preferenceSubjectByTeacher", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Subject::class.java.name + ".preferencesSubjectByDivision", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Subject::class.java.name + ".preferenceSubjectByPlace", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Subject::class.java.name + ".preferencesDateTimeForSubject", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Place::class.java.name, jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Place::class.java.name + ".timetables", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Place::class.java.name + ".preferenceSubjectByPlace", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Place::class.java.name + ".preferenceDivisionByPlace", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Place::class.java.name + ".preferenceTeacherByPlace", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Place::class.java.name + ".preferencesDateTimeForPlace", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Timetable::class.java.name, jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Lesson::class.java.name, jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Lesson::class.java.name + ".timetables", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Period::class.java.name, jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Period::class.java.name + ".intervalTimes", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Period::class.java.name + ".timetables", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Interval::class.java.name, jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.CurriculumList::class.java.name, jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.CurriculumList::class.java.name + ".curriculums", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Curriculum::class.java.name, jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.Curriculum::class.java.name + ".curriculumnListes", jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.preference.PreferenceDataTimeForTeacher::class.java.name, jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.preference.PreferenceDataTimeForDivision::class.java.name, jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.preference.PreferenceDataTimeForSubject::class.java.name, jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.preference.PreferenceDataTimeForPlace::class.java.name, jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.preference.PreferenceSubjectByTeacher::class.java.name, jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.preference.PreferenceSubjectByPlace::class.java.name, jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.preference.PreferenceDivisionByPlace::class.java.name, jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.preference.PreferenceTeacherByPlace::class.java.name, jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.preference.PreferenceTeacherByDivision::class.java.name, jcacheConfiguration)
            it.createCache(com.patres.timetable.domain.preference.PreferenceSubjectByDivision::class.java.name, jcacheConfiguration)
        }
    }
}
