package com.patres.timetable.web.rest;

import com.patres.timetable.TimetableApp;

import com.patres.timetable.domain.Timetable;
import com.patres.timetable.repository.TimetableRepository;
import com.patres.timetable.service.TimetableService;
import com.patres.timetable.service.dto.TimetableDTO;
import com.patres.timetable.service.mapper.TimetableMapper;
import com.patres.timetable.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.patres.timetable.domain.enumeration.EventType;
/**
 * Test class for the TimetableResource REST controller.
 *
 * @see TimetableResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TimetableApp.class)
public class TimetableResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final Long DEFAULT_START_TIME = 1L;
    private static final Long UPDATED_START_TIME = 2L;

    private static final Long DEFAULT_END_TIME = 1L;
    private static final Long UPDATED_END_TIME = 2L;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final EventType DEFAULT_TYPE = EventType.LESSON;
    private static final EventType UPDATED_TYPE = EventType.SUBSTITUTION;

    private static final Long DEFAULT_EVERY_WEEK = 1L;
    private static final Long UPDATED_EVERY_WEEK = 2L;

    private static final Long DEFAULT_START_WITH_WEEK = 1L;
    private static final Long UPDATED_START_WITH_WEEK = 2L;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR_BACKGROUND = "AAAAAAAAAA";
    private static final String UPDATED_COLOR_BACKGROUND = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_COLOR_TEXT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IN_MONDAY = false;
    private static final Boolean UPDATED_IN_MONDAY = true;

    private static final Boolean DEFAULT_IN_TUESDAY = false;
    private static final Boolean UPDATED_IN_TUESDAY = true;

    private static final Boolean DEFAULT_IN_WEDNESDAY = false;
    private static final Boolean UPDATED_IN_WEDNESDAY = true;

    private static final Boolean DEFAULT_IN_THURSDAY = false;
    private static final Boolean UPDATED_IN_THURSDAY = true;

    private static final Boolean DEFAULT_IN_FRIDAY = false;
    private static final Boolean UPDATED_IN_FRIDAY = true;

    private static final Boolean DEFAULT_IN_SATURDAY = false;
    private static final Boolean UPDATED_IN_SATURDAY = true;

    private static final Boolean DEFAULT_IN_SUNDAY = false;
    private static final Boolean UPDATED_IN_SUNDAY = true;

    @Autowired
    private TimetableRepository timetableRepository;

    @Autowired
    private TimetableMapper timetableMapper;

    @Autowired
    private TimetableService timetableService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTimetableMockMvc;

    private Timetable timetable;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TimetableResource timetableResource = new TimetableResource(timetableService);
        this.restTimetableMockMvc = MockMvcBuilders.standaloneSetup(timetableResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Timetable createEntity(EntityManager em) {
        Timetable timetable = new Timetable()
            .title(DEFAULT_TITLE)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .date(DEFAULT_DATE)
            .type(DEFAULT_TYPE)
            .everyWeek(DEFAULT_EVERY_WEEK)
            .startWithWeek(DEFAULT_START_WITH_WEEK)
            .description(DEFAULT_DESCRIPTION)
            .colorBackground(DEFAULT_COLOR_BACKGROUND)
            .colorText(DEFAULT_COLOR_TEXT)
            .inMonday(DEFAULT_IN_MONDAY)
            .inTuesday(DEFAULT_IN_TUESDAY)
            .inWednesday(DEFAULT_IN_WEDNESDAY)
            .inThursday(DEFAULT_IN_THURSDAY)
            .inFriday(DEFAULT_IN_FRIDAY)
            .inSaturday(DEFAULT_IN_SATURDAY)
            .inSunday(DEFAULT_IN_SUNDAY);
        return timetable;
    }

    @Before
    public void initTest() {
        timetable = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimetable() throws Exception {
        int databaseSizeBeforeCreate = timetableRepository.findAll().size();

        // Create the Timetable
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);
        restTimetableMockMvc.perform(post("/api/timetables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timetableDTO)))
            .andExpect(status().isCreated());

        // Validate the Timetable in the database
        List<Timetable> timetableList = timetableRepository.findAll();
        assertThat(timetableList).hasSize(databaseSizeBeforeCreate + 1);
        Timetable testTimetable = timetableList.get(timetableList.size() - 1);
        assertThat(testTimetable.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTimetable.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testTimetable.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testTimetable.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTimetable.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testTimetable.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testTimetable.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTimetable.getEveryWeek()).isEqualTo(DEFAULT_EVERY_WEEK);
        assertThat(testTimetable.getStartWithWeek()).isEqualTo(DEFAULT_START_WITH_WEEK);
        assertThat(testTimetable.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTimetable.getColorBackground()).isEqualTo(DEFAULT_COLOR_BACKGROUND);
        assertThat(testTimetable.getColorText()).isEqualTo(DEFAULT_COLOR_TEXT);
        assertThat(testTimetable.isInMonday()).isEqualTo(DEFAULT_IN_MONDAY);
        assertThat(testTimetable.isInTuesday()).isEqualTo(DEFAULT_IN_TUESDAY);
        assertThat(testTimetable.isInWednesday()).isEqualTo(DEFAULT_IN_WEDNESDAY);
        assertThat(testTimetable.isInThursday()).isEqualTo(DEFAULT_IN_THURSDAY);
        assertThat(testTimetable.isInFriday()).isEqualTo(DEFAULT_IN_FRIDAY);
        assertThat(testTimetable.isInSaturday()).isEqualTo(DEFAULT_IN_SATURDAY);
        assertThat(testTimetable.isInSunday()).isEqualTo(DEFAULT_IN_SUNDAY);
    }

    @Test
    @Transactional
    public void createTimetableWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timetableRepository.findAll().size();

        // Create the Timetable with an existing ID
        timetable.setId(1L);
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimetableMockMvc.perform(post("/api/timetables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timetableDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Timetable> timetableList = timetableRepository.findAll();
        assertThat(timetableList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = timetableRepository.findAll().size();
        // set the field null
        timetable.setTitle(null);

        // Create the Timetable, which fails.
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);

        restTimetableMockMvc.perform(post("/api/timetables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timetableDTO)))
            .andExpect(status().isBadRequest());

        List<Timetable> timetableList = timetableRepository.findAll();
        assertThat(timetableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = timetableRepository.findAll().size();
        // set the field null
        timetable.setType(null);

        // Create the Timetable, which fails.
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);

        restTimetableMockMvc.perform(post("/api/timetables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timetableDTO)))
            .andExpect(status().isBadRequest());

        List<Timetable> timetableList = timetableRepository.findAll();
        assertThat(timetableList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTimetables() throws Exception {
        // Initialize the database
        timetableRepository.saveAndFlush(timetable);

        // Get all the timetableList
        restTimetableMockMvc.perform(get("/api/timetables?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timetable.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.intValue())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].everyWeek").value(hasItem(DEFAULT_EVERY_WEEK.intValue())))
            .andExpect(jsonPath("$.[*].startWithWeek").value(hasItem(DEFAULT_START_WITH_WEEK.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].colorBackground").value(hasItem(DEFAULT_COLOR_BACKGROUND.toString())))
            .andExpect(jsonPath("$.[*].colorText").value(hasItem(DEFAULT_COLOR_TEXT.toString())))
            .andExpect(jsonPath("$.[*].inMonday").value(hasItem(DEFAULT_IN_MONDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].inTuesday").value(hasItem(DEFAULT_IN_TUESDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].inWednesday").value(hasItem(DEFAULT_IN_WEDNESDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].inThursday").value(hasItem(DEFAULT_IN_THURSDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].inFriday").value(hasItem(DEFAULT_IN_FRIDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].inSaturday").value(hasItem(DEFAULT_IN_SATURDAY.booleanValue())))
            .andExpect(jsonPath("$.[*].inSunday").value(hasItem(DEFAULT_IN_SUNDAY.booleanValue())));
    }

    @Test
    @Transactional
    public void getTimetable() throws Exception {
        // Initialize the database
        timetableRepository.saveAndFlush(timetable);

        // Get the timetable
        restTimetableMockMvc.perform(get("/api/timetables/{id}", timetable.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(timetable.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.intValue()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.everyWeek").value(DEFAULT_EVERY_WEEK.intValue()))
            .andExpect(jsonPath("$.startWithWeek").value(DEFAULT_START_WITH_WEEK.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.colorBackground").value(DEFAULT_COLOR_BACKGROUND.toString()))
            .andExpect(jsonPath("$.colorText").value(DEFAULT_COLOR_TEXT.toString()))
            .andExpect(jsonPath("$.inMonday").value(DEFAULT_IN_MONDAY.booleanValue()))
            .andExpect(jsonPath("$.inTuesday").value(DEFAULT_IN_TUESDAY.booleanValue()))
            .andExpect(jsonPath("$.inWednesday").value(DEFAULT_IN_WEDNESDAY.booleanValue()))
            .andExpect(jsonPath("$.inThursday").value(DEFAULT_IN_THURSDAY.booleanValue()))
            .andExpect(jsonPath("$.inFriday").value(DEFAULT_IN_FRIDAY.booleanValue()))
            .andExpect(jsonPath("$.inSaturday").value(DEFAULT_IN_SATURDAY.booleanValue()))
            .andExpect(jsonPath("$.inSunday").value(DEFAULT_IN_SUNDAY.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTimetable() throws Exception {
        // Get the timetable
        restTimetableMockMvc.perform(get("/api/timetables/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimetable() throws Exception {
        // Initialize the database
        timetableRepository.saveAndFlush(timetable);
        int databaseSizeBeforeUpdate = timetableRepository.findAll().size();

        // Update the timetable
        Timetable updatedTimetable = timetableRepository.findOne(timetable.getId());
        updatedTimetable
            .title(UPDATED_TITLE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .date(UPDATED_DATE)
            .type(UPDATED_TYPE)
            .everyWeek(UPDATED_EVERY_WEEK)
            .startWithWeek(UPDATED_START_WITH_WEEK)
            .description(UPDATED_DESCRIPTION)
            .colorBackground(UPDATED_COLOR_BACKGROUND)
            .colorText(UPDATED_COLOR_TEXT)
            .inMonday(UPDATED_IN_MONDAY)
            .inTuesday(UPDATED_IN_TUESDAY)
            .inWednesday(UPDATED_IN_WEDNESDAY)
            .inThursday(UPDATED_IN_THURSDAY)
            .inFriday(UPDATED_IN_FRIDAY)
            .inSaturday(UPDATED_IN_SATURDAY)
            .inSunday(UPDATED_IN_SUNDAY);
        TimetableDTO timetableDTO = timetableMapper.toDto(updatedTimetable);

        restTimetableMockMvc.perform(put("/api/timetables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timetableDTO)))
            .andExpect(status().isOk());

        // Validate the Timetable in the database
        List<Timetable> timetableList = timetableRepository.findAll();
        assertThat(timetableList).hasSize(databaseSizeBeforeUpdate);
        Timetable testTimetable = timetableList.get(timetableList.size() - 1);
        assertThat(testTimetable.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTimetable.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testTimetable.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testTimetable.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTimetable.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testTimetable.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testTimetable.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTimetable.getEveryWeek()).isEqualTo(UPDATED_EVERY_WEEK);
        assertThat(testTimetable.getStartWithWeek()).isEqualTo(UPDATED_START_WITH_WEEK);
        assertThat(testTimetable.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTimetable.getColorBackground()).isEqualTo(UPDATED_COLOR_BACKGROUND);
        assertThat(testTimetable.getColorText()).isEqualTo(UPDATED_COLOR_TEXT);
        assertThat(testTimetable.isInMonday()).isEqualTo(UPDATED_IN_MONDAY);
        assertThat(testTimetable.isInTuesday()).isEqualTo(UPDATED_IN_TUESDAY);
        assertThat(testTimetable.isInWednesday()).isEqualTo(UPDATED_IN_WEDNESDAY);
        assertThat(testTimetable.isInThursday()).isEqualTo(UPDATED_IN_THURSDAY);
        assertThat(testTimetable.isInFriday()).isEqualTo(UPDATED_IN_FRIDAY);
        assertThat(testTimetable.isInSaturday()).isEqualTo(UPDATED_IN_SATURDAY);
        assertThat(testTimetable.isInSunday()).isEqualTo(UPDATED_IN_SUNDAY);
    }

    @Test
    @Transactional
    public void updateNonExistingTimetable() throws Exception {
        int databaseSizeBeforeUpdate = timetableRepository.findAll().size();

        // Create the Timetable
        TimetableDTO timetableDTO = timetableMapper.toDto(timetable);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTimetableMockMvc.perform(put("/api/timetables")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timetableDTO)))
            .andExpect(status().isCreated());

        // Validate the Timetable in the database
        List<Timetable> timetableList = timetableRepository.findAll();
        assertThat(timetableList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTimetable() throws Exception {
        // Initialize the database
        timetableRepository.saveAndFlush(timetable);
        int databaseSizeBeforeDelete = timetableRepository.findAll().size();

        // Get the timetable
        restTimetableMockMvc.perform(delete("/api/timetables/{id}", timetable.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Timetable> timetableList = timetableRepository.findAll();
        assertThat(timetableList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Timetable.class);
        Timetable timetable1 = new Timetable();
        timetable1.setId(1L);
        Timetable timetable2 = new Timetable();
        timetable2.setId(timetable1.getId());
        assertThat(timetable1).isEqualTo(timetable2);
        timetable2.setId(2L);
        assertThat(timetable1).isNotEqualTo(timetable2);
        timetable1.setId(null);
        assertThat(timetable1).isNotEqualTo(timetable2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimetableDTO.class);
        TimetableDTO timetableDTO1 = new TimetableDTO();
        timetableDTO1.setId(1L);
        TimetableDTO timetableDTO2 = new TimetableDTO();
        assertThat(timetableDTO1).isNotEqualTo(timetableDTO2);
        timetableDTO2.setId(timetableDTO1.getId());
        assertThat(timetableDTO1).isEqualTo(timetableDTO2);
        timetableDTO2.setId(2L);
        assertThat(timetableDTO1).isNotEqualTo(timetableDTO2);
        timetableDTO1.setId(null);
        assertThat(timetableDTO1).isNotEqualTo(timetableDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(timetableMapper.fromId(42L, Timetable::new).getId()).isEqualTo(42);
        assertThat(timetableMapper.fromId(null, Timetable::new)).isNull();
    }
}
