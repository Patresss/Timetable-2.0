package com.patres.timetable.service

import com.patres.timetable.service.mapper.EntityMapper
import org.assertj.core.api.Assertions
import org.junit.Test
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder

import java.util.ArrayList

import org.assertj.core.api.Assertions.assertThat


class EntityMapperUnitTest {

    @Test
    fun `should return HIS when history`() {
        val expected = "HIS"
        val result = EntityMapper.getShortName("history")
        assertThat(expected).isEqualTo(result)
    }

    @Test
    fun `should return HI when hi`() {
        val expected = "HI"
        val result = EntityMapper.getShortName("hi")
        assertThat(expected).isEqualTo(result)
    }

    @Test
    fun `should return JdotP when j dot polski`() {
        val expected = "J.P"
        val result = EntityMapper.getShortName("j. polski")
        assertThat(expected).isEqualTo(result)
    }

    @Test
    fun `should return empty string when null`() {
        val expected = ""
        val result = EntityMapper.getShortName(null)
        assertThat(expected).isEqualTo(result)
    }

}
