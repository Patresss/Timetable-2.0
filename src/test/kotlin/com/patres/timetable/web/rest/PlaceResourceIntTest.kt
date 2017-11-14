package com.patres.timetable.web.rest

import com.patres.timetable.TimetableApp
import com.patres.timetable.domain.Place
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.repository.PlaceRepository
import com.patres.timetable.service.PlaceService
import com.patres.timetable.service.dto.PlaceDTO
import com.patres.timetable.service.mapper.PlaceMapper
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
 * Test class for the PlaceResource REST controller.
 *
 * @see PlaceResource
 */
@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(TimetableApp::class))
open class PlaceResourceIntTest {

    @Autowired
    lateinit private var placeRepository: PlaceRepository

    @Autowired
    lateinit private var divisionRepository: DivisionRepository

    @Autowired
    lateinit private var placeMapper: PlaceMapper

    @Autowired
    lateinit private var placeService: PlaceService

    @Autowired
    lateinit private var jacksonMessageConverter: MappingJackson2HttpMessageConverter

    @Autowired
    lateinit private var pageableArgumentResolver: PageableHandlerMethodArgumentResolver

    @Autowired
    lateinit private var exceptionTranslator: ExceptionTranslator

    lateinit private var restPlaceMockMvc: MockMvc

    private var place: Place = createEntity()

    companion object {

        private val DEFAULT_NAME = "AAAAAAAAAA"
        private val UPDATED_NAME = "BBBBBBBBBB"

        private val DEFAULT_NUMBER_OF_SEATS = 1L
        private val UPDATED_NUMBER_OF_SEATS = 2L

        private val DEFAULT_SHORT_NAME = "AAAAAAAAAA"
        private val UPDATED_SHORT_NAME = "BBBBBBBBBB"

        private val DEFAULT_COLOR_BACKGROUND = "AAAAAAAAAA"
        private val UPDATED_COLOR_BACKGROUND = "BBBBBBBBBB"

        private val DEFAULT_COLOR_TEXT = "AAAAAAAAAA"
        private val UPDATED_COLOR_TEXT = "BBBBBBBBBB"

        fun createEntity(): Place {
            return Place(
                name = DEFAULT_NAME
            ).apply {
                numberOfSeats = DEFAULT_NUMBER_OF_SEATS
                shortName = DEFAULT_SHORT_NAME
                colorBackground = DEFAULT_COLOR_BACKGROUND
                colorText = DEFAULT_COLOR_TEXT
            }
        }

        fun createEntityDto(): PlaceDTO {
            return PlaceDTO(
                name = DEFAULT_NAME
            ).apply {
                numberOfSeats = DEFAULT_NUMBER_OF_SEATS
                shortName = DEFAULT_SHORT_NAME
                colorBackground = DEFAULT_COLOR_BACKGROUND
                colorText = DEFAULT_COLOR_TEXT
            }
        }
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        restPlaceMockMvc = initMockMvc(PlaceResource(placeService))
    }

    private fun initMockMvc(placeResource: PlaceResource): MockMvc {
        return MockMvcBuilders.standaloneSetup(placeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build()
    }

    @Before
    fun initTest() {
        place = createEntity()
        val divisionOwner = DivisionResourceIntTest.createEntity()
        divisionRepository.saveAndFlush(divisionOwner)
        place.divisionOwner = divisionOwner
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun createPlace() {
        val databaseSizeBeforeCreate = placeRepository.findAll().size

        // Create the Place
        val placeDTO = placeMapper.toDto(place)
        restPlaceMockMvc.perform(post("/api/places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(placeDTO)))
            .andExpect(status().isCreated)

        // Validate the Place in the database
        val placeList = placeRepository.findAll()
        assertThat(placeList).hasSize(databaseSizeBeforeCreate + 1)
        val testPlace = placeList[placeList.size - 1]
        assertThat(testPlace.name).isEqualTo(DEFAULT_NAME)
        assertThat(testPlace.numberOfSeats).isEqualTo(DEFAULT_NUMBER_OF_SEATS)
        assertThat(testPlace.shortName).isEqualTo(DEFAULT_SHORT_NAME)
        assertThat(testPlace.colorBackground).isEqualTo(DEFAULT_COLOR_BACKGROUND)
        assertThat(testPlace.colorText).isEqualTo(DEFAULT_COLOR_TEXT)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun createPlaceWithExistingId() {
        val databaseSizeBeforeCreate = placeRepository.findAll().size

        // Create the Place with an existing ID
        place.id = 1L
        val placeDTO = placeMapper.toDto(place)

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlaceMockMvc.perform(post("/api/places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(placeDTO)))
            .andExpect(status().isBadRequest)

        // Validate the Alice in the database
        val placeList = placeRepository.findAll()
        assertThat(placeList).hasSize(databaseSizeBeforeCreate)
    }


    @Test
    @Transactional
    @Throws(Exception::class)
    fun checkNameIsRequired() {
        val databaseSizeBeforeTest = placeRepository.findAll().size
        // set the field null
        place.name = null

        // Create the Place, which fails.
        val placeDTO = placeMapper.toDto(place)

        restPlaceMockMvc.perform(post("/api/places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(placeDTO)))
            .andExpect(status().isBadRequest)

        val placeList = placeRepository.findAll()
        assertThat(placeList).hasSize(databaseSizeBeforeTest)
    }


    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getAllPlaces() {
        // Initialize the database
        placeRepository.saveAndFlush<Place>(place)

        // Get all the placeList
        restPlaceMockMvc.perform(get("/api/places?sort=id,desc"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(place.id?.toInt())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].numberOfSeats").value(hasItem(DEFAULT_NUMBER_OF_SEATS.toInt())))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].colorBackground").value(hasItem(DEFAULT_COLOR_BACKGROUND)))
            .andExpect(jsonPath("$.[*].colorText").value(hasItem(DEFAULT_COLOR_TEXT)))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getPlace() {
        // Initialize the database
        placeRepository.saveAndFlush<Place>(place)

        // Get the place
        restPlaceMockMvc.perform(get("/api/places/{id}", place.id))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(place.id))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.numberOfSeats").value(DEFAULT_NUMBER_OF_SEATS.toInt()))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME))
            .andExpect(jsonPath("$.colorBackground").value(DEFAULT_COLOR_BACKGROUND))
            .andExpect(jsonPath("$.colorText").value(DEFAULT_COLOR_TEXT))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun getNonExistingPlace() {
        // Get the place
        restPlaceMockMvc.perform(get("/api/places/{id}", java.lang.Long.MAX_VALUE))
            .andExpect(status().isNotFound)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun updatePlace() {
        // Initialize the database
        placeRepository.saveAndFlush<Place>(place)
        val databaseSizeBeforeUpdate = placeRepository.findAll().size

        // Update the place
        val updatedPlace = placeRepository.findOne(place.id)

        updatedPlace.name = UPDATED_NAME
        updatedPlace.numberOfSeats = UPDATED_NUMBER_OF_SEATS
        updatedPlace.shortName = UPDATED_SHORT_NAME
        updatedPlace.colorBackground = UPDATED_COLOR_BACKGROUND
        updatedPlace.colorText = UPDATED_COLOR_TEXT
        val placeDTO = placeMapper.toDto(updatedPlace)

        restPlaceMockMvc.perform(put("/api/places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(placeDTO)))
            .andExpect(status().isOk)

        // Validate the Place in the database
        val placeList = placeRepository.findAll()
        assertThat(placeList).hasSize(databaseSizeBeforeUpdate)
        val testPlace = placeList[placeList.size - 1]
        assertThat(testPlace.name).isEqualTo(UPDATED_NAME)
        assertThat(testPlace.numberOfSeats).isEqualTo(UPDATED_NUMBER_OF_SEATS)
        assertThat(testPlace.shortName).isEqualTo(UPDATED_SHORT_NAME)
        assertThat(testPlace.colorBackground).isEqualTo(UPDATED_COLOR_BACKGROUND)
        assertThat(testPlace.colorText).isEqualTo(UPDATED_COLOR_TEXT)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun updateNonExistingPlace() {
        val databaseSizeBeforeUpdate = placeRepository.findAll().size

        // Create the Place
        val placeDTO = placeMapper.toDto(place)

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlaceMockMvc.perform(put("/api/places")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(placeDTO)))
            .andExpect(status().isCreated)

        // Validate the Place in the database
        val placeList = placeRepository.findAll()
        assertThat(placeList).hasSize(databaseSizeBeforeUpdate + 1)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun deletePlace() {
        // Initialize the database
        placeRepository.saveAndFlush<Place>(place)
        val databaseSizeBeforeDelete = placeRepository.findAll().size

        // Get the place
        restPlaceMockMvc.perform(delete("/api/places/{id}", place.id)
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk)

        // Validate the database is empty
        val placeList = placeRepository.findAll()
        assertThat(placeList).hasSize(databaseSizeBeforeDelete - 1)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun equalsVerifier() {
        val place1 = createEntity()
        place1.id = 1L
        val place2 = createEntity()
        place2.id = place1.id
        assertThat(place1).isEqualTo(place2)
        place2.id = 2L
        assertThat(place1).isNotEqualTo(place2)
        place1.id = null
        assertThat(place1).isNotEqualTo(place2)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun dtoEqualsVerifier() {
        val placeDTO1 = createEntityDto()
        placeDTO1.id = 1L
        val placeDTO2 = createEntityDto()
        assertThat(placeDTO1).isNotEqualTo(placeDTO2)
        placeDTO2.id = placeDTO1.id
        assertThat(placeDTO1).isEqualTo(placeDTO2)
        placeDTO2.id = 2L
        assertThat(placeDTO1).isNotEqualTo(placeDTO2)
        placeDTO1.id = null
        assertThat(placeDTO1).isNotEqualTo(placeDTO2)
    }


}
