package com.patres.timetable.web.rest

import com.patres.timetable.TimetableApp
import com.patres.timetable.domain.Property
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.repository.PropertyRepository
import com.patres.timetable.service.PropertyService
import com.patres.timetable.service.dto.PropertyDTO
import com.patres.timetable.service.mapper.PropertyMapper
import com.patres.timetable.web.rest.errors.ExceptionTranslator
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.hasItem
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.web.PageableHandlerMethodArgumentResolver
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional


/**
 * Test class for the PropertiesResource REST controller.
 *
 * @see PropertyResource
 */
@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(TimetableApp::class))
open class PropertyResourceIntTest {

    @Autowired
    private lateinit var propertyRepository: PropertyRepository

    @Autowired
    private lateinit var divisionRepository: DivisionRepository

    @Autowired
    private lateinit var propertyMapper: PropertyMapper

    @Autowired
    private lateinit var propertyService: PropertyService

    @Autowired
    private lateinit var jacksonMessageConverter: MappingJackson2HttpMessageConverter

    @Autowired
    private lateinit var pageableArgumentResolver: PageableHandlerMethodArgumentResolver

    @Autowired
    private lateinit var exceptionTranslator: ExceptionTranslator

    private lateinit var restPropertiesMockMvc: MockMvc

    private var property: Property = createEntity()

    companion object {

        private val DEFAULT_PROPERTY_KEY = "AAAAAAAAAA"
        private val UPDATED_PROPERTY_KEY = "BBBBBBBBBB"

        private val DEFAULT_PROPERTY_VALUE = "AAAAAAAAAA"
        private val UPDATED_PROPERTY_VALUE = "BBBBBBBBBB"

        fun createEntity(): Property {
            return Property(
                propertyKey = DEFAULT_PROPERTY_KEY,
                propertyValue = DEFAULT_PROPERTY_VALUE
            )
        }

        fun createEntityDto(): PropertyDTO {
            return PropertyDTO(
                propertyKey = DEFAULT_PROPERTY_KEY,
                propertyValue = DEFAULT_PROPERTY_VALUE
            )
        }
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        restPropertiesMockMvc = initMockMvc(PropertyResource(propertyService))
    }

    private fun initMockMvc(propertyResource: PropertyResource): MockMvc {
        return MockMvcBuilders.standaloneSetup(propertyResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build()
    }

    @Before
    fun initTest() {
        property = createEntity()
        val divisionOwner = DivisionResourceIntTest.createEntity()
        divisionRepository.saveAndFlush(divisionOwner)
        property.divisionOwner = divisionOwner
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun createProperties() {
        val databaseSizeBeforeCreate = propertyRepository.findAll().size

        // Create the Property
        val propertyDTO = propertyMapper.toDto(property)
        restPropertiesMockMvc.perform(post("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyDTO)))
            .andExpect(status().isCreated)

        // Validate the Property in the database
        val propertyList = propertyRepository.findAll()
        assertThat(propertyList).hasSize(databaseSizeBeforeCreate + 1)
        val testProperty = propertyList[propertyList.size - 1]
        assertThat(testProperty.propertyKey).isEqualTo(DEFAULT_PROPERTY_KEY)
        assertThat(testProperty.propertyValue).isEqualTo(DEFAULT_PROPERTY_VALUE)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun createPropertiesWithExistingId() {
        val databaseSizeBeforeCreate = propertyRepository.findAll().size

        // Create the Property with an existing ID
        property.id = 1L
        val propertyDTO = propertyMapper.toDto(property)

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropertiesMockMvc.perform(post("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyDTO)))
            .andExpect(status().isBadRequest)

        // Validate the Alice in the database
        val propertyList = propertyRepository.findAll()
        assertThat(propertyList).hasSize(databaseSizeBeforeCreate)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun checkPropertyKeyIsRequired() {
        val databaseSizeBeforeTest = propertyRepository.findAll().size
        // set the field null
        property.propertyKey = null

        // Create the Property, which fails.
        val propertyDTO = propertyMapper.toDto(property)

        restPropertiesMockMvc.perform(post("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyDTO)))
            .andExpect(status().isBadRequest)

        val propertyList = propertyRepository.findAll()
        assertThat(propertyList).hasSize(databaseSizeBeforeTest)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getAllProperties() {
        // Initialize the database
        propertyRepository.saveAndFlush<Property>(property)

        // Get all the propertiesList
        restPropertiesMockMvc.perform(get("/api/properties?sort=id,desc"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(property.id?.toInt())))
            .andExpect(jsonPath("$.[*].propertyKey").value(hasItem(DEFAULT_PROPERTY_KEY)))
            .andExpect(jsonPath("$.[*].propertyValue").value(hasItem(DEFAULT_PROPERTY_VALUE)))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getProperties() {
        // Initialize the database
        propertyRepository.saveAndFlush<Property>(property)

        // Get the property
        restPropertiesMockMvc.perform(get("/api/properties/{id}", property.id))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(property.id))
            .andExpect(jsonPath("$.propertyKey").value(DEFAULT_PROPERTY_KEY))
            .andExpect(jsonPath("$.propertyValue").value(DEFAULT_PROPERTY_VALUE))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getNonExistingProperties() {
        // Get the property
        restPropertiesMockMvc.perform(get("/api/properties/{id}", java.lang.Long.MAX_VALUE))
            .andExpect(status().isNotFound)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun updateProperties() {
        // Initialize the database
        propertyRepository.saveAndFlush<Property>(property)
        val databaseSizeBeforeUpdate = propertyRepository.findAll().size

        // Update the property
        val updatedProperty = propertyRepository.findOne(property.id)
        updatedProperty.propertyKey = UPDATED_PROPERTY_KEY
        updatedProperty.propertyValue = UPDATED_PROPERTY_VALUE
        val propertyDTO = propertyMapper.toDto(updatedProperty)

        restPropertiesMockMvc.perform(put("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyDTO)))
            .andExpect(status().isOk)

        // Validate the Property in the database
        val propertyList = propertyRepository.findAll()
        assertThat(propertyList).hasSize(databaseSizeBeforeUpdate)
        val testProperty = propertyList[propertyList.size - 1]
        assertThat(testProperty.propertyKey).isEqualTo(UPDATED_PROPERTY_KEY)
        assertThat(testProperty.propertyValue).isEqualTo(UPDATED_PROPERTY_VALUE)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun updateNonExistingProperties() {
        val databaseSizeBeforeUpdate = propertyRepository.findAll().size

        // Create the Property
        val propertyDTO = propertyMapper.toDto(property)

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPropertiesMockMvc.perform(put("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyDTO)))
            .andExpect(status().isCreated)

        // Validate the Property in the database
        val propertyList = propertyRepository.findAll()
        assertThat(propertyList).hasSize(databaseSizeBeforeUpdate + 1)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun deleteProperties() {
        // Initialize the database
        propertyRepository.saveAndFlush<Property>(property)
        val databaseSizeBeforeDelete = propertyRepository.findAll().size

        // Get the property
        restPropertiesMockMvc.perform(delete("/api/properties/{id}", property.id)
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk)

        // Validate the database is empty
        val propertyList = propertyRepository.findAll()
        assertThat(propertyList).hasSize(databaseSizeBeforeDelete - 1)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun equalsVerifier() {
        val property1 = createEntity()
        property1.id = 1L
        val property2 = createEntity()
        property2.id = property1.id
        assertThat(property1).isEqualTo(property2)
        property2.id = 2L
        assertThat(property1).isNotEqualTo(property2)
        property1.id = null
        assertThat(property1).isNotEqualTo(property2)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun dtoEqualsVerifier() {
        val propertyDTO1 = createEntityDto()
        propertyDTO1.id = 1L
        val propertyDTO2 = createEntityDto()
        assertThat(propertyDTO1).isNotEqualTo(propertyDTO2)
        propertyDTO2.id = propertyDTO1.id
        assertThat(propertyDTO1).isEqualTo(propertyDTO2)
        propertyDTO2.id = 2L
        assertThat(propertyDTO1).isNotEqualTo(propertyDTO2)
        propertyDTO1.id = null
        assertThat(propertyDTO1).isNotEqualTo(propertyDTO2)
    }


}
