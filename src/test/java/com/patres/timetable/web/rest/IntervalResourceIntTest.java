package com.patres.timetable.web.rest;

import com.patres.timetable.TimetableApp;

import com.patres.timetable.domain.Interval;
import com.patres.timetable.repository.IntervalRepository;
import com.patres.timetable.service.IntervalService;
import com.patres.timetable.repository.search.IntervalSearchRepository;
import com.patres.timetable.service.dto.IntervalDTO;
import com.patres.timetable.service.mapper.IntervalMapper;
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

/**
 * Test class for the IntervalResource REST controller.
 *
 * @see IntervalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TimetableApp.class)
public class IntervalResourceIntTest {

    private static final Boolean DEFAULT_INCLUDED = false;
    private static final Boolean UPDATED_INCLUDED = true;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private IntervalRepository intervalRepository;

    @Autowired
    private IntervalMapper intervalMapper;

    @Autowired
    private IntervalService intervalService;

    @Autowired
    private IntervalSearchRepository intervalSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIntervalMockMvc;

    private Interval interval;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        IntervalResource intervalResource = new IntervalResource(intervalService);
        this.restIntervalMockMvc = MockMvcBuilders.standaloneSetup(intervalResource)
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
    public static Interval createEntity(EntityManager em) {
        Interval interval = new Interval()
            .included(DEFAULT_INCLUDED)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return interval;
    }

    @Before
    public void initTest() {
        intervalSearchRepository.deleteAll();
        interval = createEntity(em);
    }

    @Test
    @Transactional
    public void createInterval() throws Exception {
        int databaseSizeBeforeCreate = intervalRepository.findAll().size();

        // Create the Interval
        IntervalDTO intervalDTO = intervalMapper.toDto(interval);
        restIntervalMockMvc.perform(post("/api/intervals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intervalDTO)))
            .andExpect(status().isCreated());

        // Validate the Interval in the database
        List<Interval> intervalList = intervalRepository.findAll();
        assertThat(intervalList).hasSize(databaseSizeBeforeCreate + 1);
        Interval testInterval = intervalList.get(intervalList.size() - 1);
        assertThat(testInterval.isIncluded()).isEqualTo(DEFAULT_INCLUDED);
        assertThat(testInterval.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testInterval.getEndDate()).isEqualTo(DEFAULT_END_DATE);

        // Validate the Interval in Elasticsearch
        Interval intervalEs = intervalSearchRepository.findOne(testInterval.getId());
        assertThat(intervalEs).isEqualToComparingFieldByField(testInterval);
    }

    @Test
    @Transactional
    public void createIntervalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = intervalRepository.findAll().size();

        // Create the Interval with an existing ID
        interval.setId(1L);
        IntervalDTO intervalDTO = intervalMapper.toDto(interval);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntervalMockMvc.perform(post("/api/intervals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intervalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Interval> intervalList = intervalRepository.findAll();
        assertThat(intervalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIntervals() throws Exception {
        // Initialize the database
        intervalRepository.saveAndFlush(interval);

        // Get all the intervalList
        restIntervalMockMvc.perform(get("/api/intervals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interval.getId().intValue())))
            .andExpect(jsonPath("$.[*].included").value(hasItem(DEFAULT_INCLUDED.booleanValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void getInterval() throws Exception {
        // Initialize the database
        intervalRepository.saveAndFlush(interval);

        // Get the interval
        restIntervalMockMvc.perform(get("/api/intervals/{id}", interval.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(interval.getId().intValue()))
            .andExpect(jsonPath("$.included").value(DEFAULT_INCLUDED.booleanValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInterval() throws Exception {
        // Get the interval
        restIntervalMockMvc.perform(get("/api/intervals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInterval() throws Exception {
        // Initialize the database
        intervalRepository.saveAndFlush(interval);
        intervalSearchRepository.save(interval);
        int databaseSizeBeforeUpdate = intervalRepository.findAll().size();

        // Update the interval
        Interval updatedInterval = intervalRepository.findOne(interval.getId());
        updatedInterval
            .included(UPDATED_INCLUDED)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        IntervalDTO intervalDTO = intervalMapper.toDto(updatedInterval);

        restIntervalMockMvc.perform(put("/api/intervals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intervalDTO)))
            .andExpect(status().isOk());

        // Validate the Interval in the database
        List<Interval> intervalList = intervalRepository.findAll();
        assertThat(intervalList).hasSize(databaseSizeBeforeUpdate);
        Interval testInterval = intervalList.get(intervalList.size() - 1);
        assertThat(testInterval.isIncluded()).isEqualTo(UPDATED_INCLUDED);
        assertThat(testInterval.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testInterval.getEndDate()).isEqualTo(UPDATED_END_DATE);

        // Validate the Interval in Elasticsearch
        Interval intervalEs = intervalSearchRepository.findOne(testInterval.getId());
        assertThat(intervalEs).isEqualToComparingFieldByField(testInterval);
    }

    @Test
    @Transactional
    public void updateNonExistingInterval() throws Exception {
        int databaseSizeBeforeUpdate = intervalRepository.findAll().size();

        // Create the Interval
        IntervalDTO intervalDTO = intervalMapper.toDto(interval);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIntervalMockMvc.perform(put("/api/intervals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intervalDTO)))
            .andExpect(status().isCreated());

        // Validate the Interval in the database
        List<Interval> intervalList = intervalRepository.findAll();
        assertThat(intervalList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInterval() throws Exception {
        // Initialize the database
        intervalRepository.saveAndFlush(interval);
        intervalSearchRepository.save(interval);
        int databaseSizeBeforeDelete = intervalRepository.findAll().size();

        // Get the interval
        restIntervalMockMvc.perform(delete("/api/intervals/{id}", interval.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean intervalExistsInEs = intervalSearchRepository.exists(interval.getId());
        assertThat(intervalExistsInEs).isFalse();

        // Validate the database is empty
        List<Interval> intervalList = intervalRepository.findAll();
        assertThat(intervalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchInterval() throws Exception {
        // Initialize the database
        intervalRepository.saveAndFlush(interval);
        intervalSearchRepository.save(interval);

        // Search the interval
        restIntervalMockMvc.perform(get("/api/_search/intervals?query=id:" + interval.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interval.getId().intValue())))
            .andExpect(jsonPath("$.[*].included").value(hasItem(DEFAULT_INCLUDED.booleanValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Interval.class);
        Interval interval1 = new Interval();
        interval1.setId(1L);
        Interval interval2 = new Interval();
        interval2.setId(interval1.getId());
        assertThat(interval1).isEqualTo(interval2);
        interval2.setId(2L);
        assertThat(interval1).isNotEqualTo(interval2);
        interval1.setId(null);
        assertThat(interval1).isNotEqualTo(interval2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntervalDTO.class);
        IntervalDTO intervalDTO1 = new IntervalDTO();
        intervalDTO1.setId(1L);
        IntervalDTO intervalDTO2 = new IntervalDTO();
        assertThat(intervalDTO1).isNotEqualTo(intervalDTO2);
        intervalDTO2.setId(intervalDTO1.getId());
        assertThat(intervalDTO1).isEqualTo(intervalDTO2);
        intervalDTO2.setId(2L);
        assertThat(intervalDTO1).isNotEqualTo(intervalDTO2);
        intervalDTO1.setId(null);
        assertThat(intervalDTO1).isNotEqualTo(intervalDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(intervalMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(intervalMapper.fromId(null)).isNull();
    }
}
