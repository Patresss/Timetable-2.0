package com.patres.timetable.web.rest

import com.patres.timetable.TimetableApp
import com.patres.timetable.domain.Division
import com.patres.timetable.domain.enumeration.DivisionType
import com.patres.timetable.repository.DivisionRepository
import com.patres.timetable.service.DivisionService
import com.patres.timetable.service.dto.DivisionDTO
import com.patres.timetable.service.mapper.DivisionMapper
import com.patres.timetable.web.rest.errors.ExceptionTranslator
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.*
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
 * Test class for the DivisionResource REST controller.
 *
 * @see DivisionResource
 */
@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(TimetableApp::class))
open class DivisionResourceIntTest {

    @Autowired private
    lateinit var divisionRepository: DivisionRepository

    @Autowired
    lateinit var divisionMapper: DivisionMapper

    @Autowired
    lateinit var divisionService: DivisionService

    @Autowired
    lateinit var jacksonMessageConverter: MappingJackson2HttpMessageConverter

    @Autowired
    lateinit var pageableArgumentResolver: PageableHandlerMethodArgumentResolver

    @Autowired
    lateinit var exceptionTranslator: ExceptionTranslator

    lateinit private var restDivisionMockMvc: MockMvc

    private var division: Division = createEntity()

    companion object {

        private val DEFAULT_NAME = "AAAAAAAAAA"
        private val UPDATED_NAME = "BBBBBBBBBB"

        private val DEFAULT_SHORT_NAME = "AAAAAAAAAA"
        private val UPDATED_SHORT_NAME = "BBBBBBBBBB"

        private val DEFAULT_NUMBER_OF_PEOPLE = 1L
        private val UPDATED_NUMBER_OF_PEOPLE = 2L

        private val DEFAULT_DIVISION_TYPE = DivisionType.SCHOOL
        private val UPDATED_DIVISION_TYPE = DivisionType.CLASS

        private val DEFAULT_COLOR_BACKGROUND = "AAAAAAAAAA"
        private val UPDATED_COLOR_BACKGROUND = "BBBBBBBBBB"

        private val DEFAULT_COLOR_TEXT = "AAAAAAAAAA"
        private val UPDATED_COLOR_TEXT = "BBBBBBBBBB"

        fun createEntity(): Division {
            return Division(
                name = DEFAULT_NAME,
                shortName = DEFAULT_SHORT_NAME,
                numberOfPeople = DEFAULT_NUMBER_OF_PEOPLE,
                divisionType = DEFAULT_DIVISION_TYPE,
                colorBackground = DEFAULT_COLOR_BACKGROUND,
                colorText = DEFAULT_COLOR_TEXT)
        }

        fun createEntityDto(): DivisionDTO {
            return DivisionDTO(
                name = DEFAULT_NAME,
                shortName = DEFAULT_SHORT_NAME,
                numberOfPeople = DEFAULT_NUMBER_OF_PEOPLE,
                divisionType = DEFAULT_DIVISION_TYPE,
                colorBackground = DEFAULT_COLOR_BACKGROUND,
                colorText = DEFAULT_COLOR_TEXT)
        }
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        restDivisionMockMvc = initMockMvc(DivisionResource(divisionService))
    }

    private fun initMockMvc(divisionResource: DivisionResource): MockMvc {
        return MockMvcBuilders.standaloneSetup(divisionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build()
    }

    @Before
    fun initTest() {
        division = createEntity()
    }


    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `Create Division`() {
        val databaseSizeBeforeCreate = divisionRepository.findAll().size

        // Create the Division
        val divisionDTO = divisionMapper.toDto(division)
        restDivisionMockMvc.perform(post("/api/divisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(divisionDTO)))
            .andExpect(status().isCreated)

        // Validate the Division in the database
        val divisionList = divisionRepository.findAll()
        assertThat(divisionList).hasSize(databaseSizeBeforeCreate + 1)
        val testDivision = divisionList[divisionList.size - 1]
        assertThat(testDivision.name).isEqualTo(DEFAULT_NAME)
        assertThat(testDivision.shortName).isEqualTo(DEFAULT_SHORT_NAME)
        assertThat(testDivision.numberOfPeople).isEqualTo(DEFAULT_NUMBER_OF_PEOPLE)
        assertThat(testDivision.divisionType).isEqualTo(DEFAULT_DIVISION_TYPE)
        assertThat(testDivision.colorBackground).isEqualTo(DEFAULT_COLOR_BACKGROUND)
        assertThat(testDivision.colorText).isEqualTo(DEFAULT_COLOR_TEXT)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `Create Division With Existing Id`() {
        val databaseSizeBeforeCreate = divisionRepository.findAll().size
        division.id = 1L
        val divisionDTO = divisionMapper.toDto(division)

        // An entity with an existing ID cannot be created, so this API call must fail
        restDivisionMockMvc.perform(post("/api/divisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(divisionDTO)))
            .andExpect(status().isBadRequest)

        val divisionList = divisionRepository.findAll()
        assertThat(divisionList).hasSize(databaseSizeBeforeCreate)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `check Name Is Required`() {
        val databaseSizeBeforeTest = divisionRepository.findAll().size
        // set the field null
        division.name = null

        // Create the Division, which fails.
        val divisionDTO = divisionMapper.toDto(division)

        restDivisionMockMvc.perform(post("/api/divisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(divisionDTO)))
            .andExpect(status().isBadRequest)

        val divisionList = divisionRepository.findAll()
        assertThat(divisionList).hasSize(databaseSizeBeforeTest)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `Check Division Type is required`() {
        val databaseSizeBeforeTest = divisionRepository.findAll().size
        division.divisionType = null

        val divisionDTO = divisionMapper.toDto(division)

        restDivisionMockMvc.perform(post("/api/divisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(divisionDTO)))
            .andExpect(status().isBadRequest)

        val divisionList = divisionRepository.findAll()
        assertThat(divisionList).hasSize(databaseSizeBeforeTest)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `Get all Divisions`() {
        divisionRepository.saveAndFlush<Division>(division)

        restDivisionMockMvc.perform(get("/api/divisions?sort=id,desc"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(division.id?.toInt())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].shortName").value(hasItem(DEFAULT_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].numberOfPeople").value(hasItem(DEFAULT_NUMBER_OF_PEOPLE.toInt())))
            .andExpect(jsonPath("$.[*].divisionType").value(hasItem(DEFAULT_DIVISION_TYPE.toString())))
    }


    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `Get all by Division Type`() {
        val school1 = createEntity().apply { divisionType = DivisionType.SCHOOL }
        val school2 = createEntity().apply { divisionType = DivisionType.SCHOOL }
        val class1 = createEntity().apply { divisionType = DivisionType.CLASS }
        val class2 = createEntity().apply { divisionType = DivisionType.CLASS }
        val subGroup1 = createEntity().apply { divisionType = DivisionType.SUBGROUP }
        val subGroup2 = createEntity().apply { divisionType = DivisionType.SUBGROUP }

        divisionRepository.saveAndFlush<Division>(school1)
        divisionRepository.saveAndFlush<Division>(school2)
        divisionRepository.saveAndFlush<Division>(class1)
        divisionRepository.saveAndFlush<Division>(class2)
        divisionRepository.saveAndFlush<Division>(subGroup1)
        divisionRepository.saveAndFlush<Division>(subGroup2)

        restDivisionMockMvc.perform(get("/api/divisions/type/{divisionType}", DivisionType.SCHOOL))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*", hasSize<Int>(2)))
            .andExpect(jsonPath("$.[*].id").value(hasItem(school1.id?.toInt())))
            .andExpect(jsonPath("$.[*].id").value(hasItem(school2.id?.toInt())))

        restDivisionMockMvc.perform(get("/api/divisions/type/{divisionType}", DivisionType.CLASS))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*", hasSize<Int>(2)))
            .andExpect(jsonPath("$.[*].id").value(hasItem(class1.id?.toInt())))
            .andExpect(jsonPath("$.[*].id").value(hasItem(class2.id?.toInt())))

        restDivisionMockMvc.perform(get("/api/divisions/type/{divisionType}", DivisionType.SUBGROUP))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*", hasSize<Int>(2)))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subGroup1.id?.toInt())))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subGroup2.id?.toInt())))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `Get Classes Division by parentId`() {
        val school1 = createEntity().apply { divisionType = DivisionType.SCHOOL }
        val school2 = createEntity().apply { divisionType = DivisionType.SCHOOL; divisionOwner = school1 }
        val class1 = createEntity().apply { divisionType = DivisionType.CLASS; divisionOwner = school1}
        val class2 = createEntity().apply { divisionType = DivisionType.CLASS; divisionOwner = school1}
        val class3 = createEntity().apply { divisionType = DivisionType.CLASS; divisionOwner = school2}

        divisionRepository.saveAndFlush<Division>(school1)
        divisionRepository.saveAndFlush<Division>(school2)
        divisionRepository.saveAndFlush<Division>(class1)
        divisionRepository.saveAndFlush<Division>(class2)
        divisionRepository.saveAndFlush<Division>(class3)

        restDivisionMockMvc.perform(get("/api/divisions/parent/class/{parentId}", school1.id))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*", hasSize<Int>(2)))
            .andExpect(jsonPath("$.[*].id").value(hasItem(class1.id?.toInt())))
            .andExpect(jsonPath("$.[*].id").value(hasItem(class2.id?.toInt())))
            .andExpect(jsonPath("$.[*].id").value(not(hasItem(class3.id?.toInt()))))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `Get Subgroups Division by parentId`() {
        val class1 = createEntity().apply { divisionType = DivisionType.CLASS}
        val class2 = createEntity().apply { divisionType = DivisionType.CLASS}
        val subgroup1 = createEntity().apply { divisionType = DivisionType.SUBGROUP; parents = hashSetOf(class1)}
        val subgroup2 = createEntity().apply { divisionType = DivisionType.SUBGROUP; parents = hashSetOf(class1)}
        val subgroup3 = createEntity().apply { divisionType = DivisionType.SUBGROUP; parents = hashSetOf(class2)}
        val subgroup4 = createEntity().apply { divisionType = DivisionType.SUBGROUP; parents = hashSetOf(subgroup1)}

        divisionRepository.saveAndFlush<Division>(class1)
        divisionRepository.saveAndFlush<Division>(class2)
        divisionRepository.saveAndFlush<Division>(subgroup1)
        divisionRepository.saveAndFlush<Division>(subgroup2)
        divisionRepository.saveAndFlush<Division>(subgroup3)
        divisionRepository.saveAndFlush<Division>(subgroup4)

        restDivisionMockMvc.perform(get("/api/divisions/parent/subgroup/{parentId}", class1.id))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.*", hasSize<Int>(2)))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subgroup1.id?.toInt())))
            .andExpect(jsonPath("$.[*].id").value(hasItem(subgroup2.id?.toInt())))
            .andExpect(jsonPath("$.[*].id").value(not(hasItem(subgroup3.id?.toInt()))))
            .andExpect(jsonPath("$.[*].id").value(not(hasItem(subgroup4.id?.toInt()))))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `Get Division`() {
        divisionRepository.saveAndFlush<Division>(division)

        restDivisionMockMvc.perform(get("/api/divisions/{id}", division.id))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(division.id))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.shortName").value(DEFAULT_SHORT_NAME))
            .andExpect(jsonPath("$.numberOfPeople").value(DEFAULT_NUMBER_OF_PEOPLE.toInt()))
            .andExpect(jsonPath("$.divisionType").value(DEFAULT_DIVISION_TYPE.toString()))
            .andExpect(jsonPath("$.colorBackground").value(DEFAULT_COLOR_BACKGROUND))
            .andExpect(jsonPath("$.colorText").value(DEFAULT_COLOR_TEXT))
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `Get non existing Division`() {
        restDivisionMockMvc.perform(get("/api/divisions/{id}", java.lang.Long.MAX_VALUE))
            .andExpect(status().isNotFound)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `Update Division`() {
        divisionRepository.saveAndFlush<Division>(division)
        val databaseSizeBeforeUpdate = divisionRepository.findAll().size

        val updatedDivision = divisionRepository.findOne(division.id)

        updatedDivision.name = UPDATED_NAME
        updatedDivision.shortName = UPDATED_SHORT_NAME
        updatedDivision.numberOfPeople = UPDATED_NUMBER_OF_PEOPLE
        updatedDivision.divisionType = UPDATED_DIVISION_TYPE
        updatedDivision.colorBackground = UPDATED_COLOR_BACKGROUND
        updatedDivision.colorText = UPDATED_COLOR_TEXT
        val divisionDTO = divisionMapper.toDto(updatedDivision)

        restDivisionMockMvc.perform(put("/api/divisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(divisionDTO)))
            .andExpect(status().isOk)

        val divisionList = divisionRepository.findAll()
        assertThat(divisionList).hasSize(databaseSizeBeforeUpdate)
        val testDivision = divisionList[divisionList.size - 1]
        assertThat(testDivision.name).isEqualTo(UPDATED_NAME)
        assertThat(testDivision.shortName).isEqualTo(UPDATED_SHORT_NAME)
        assertThat(testDivision.numberOfPeople).isEqualTo(UPDATED_NUMBER_OF_PEOPLE)
        assertThat(testDivision.divisionType).isEqualTo(UPDATED_DIVISION_TYPE)
        assertThat(testDivision.colorBackground).isEqualTo(UPDATED_COLOR_BACKGROUND)
        assertThat(testDivision.colorText).isEqualTo(UPDATED_COLOR_TEXT)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `Update non existing division`() {
        val databaseSizeBeforeUpdate = divisionRepository.findAll().size

        val divisionDTO = divisionMapper.toDto(division)

        restDivisionMockMvc.perform(put("/api/divisions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(divisionDTO)))
            .andExpect(status().isCreated)

        val divisionList = divisionRepository.findAll()
        assertThat(divisionList).hasSize(databaseSizeBeforeUpdate + 1)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    open fun `Delete Division`() {
        divisionRepository.saveAndFlush<Division>(division)
        val databaseSizeBeforeDelete = divisionRepository.findAll().size

        restDivisionMockMvc.perform(delete("/api/divisions/{id}", division.id)
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk)

        val divisionList = divisionRepository.findAll()
        assertThat(divisionList).hasSize(databaseSizeBeforeDelete - 1)
    }

}
