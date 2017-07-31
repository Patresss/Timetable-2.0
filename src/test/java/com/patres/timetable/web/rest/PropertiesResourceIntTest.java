package com.patres.timetable.web.rest;

import com.patres.timetable.TimetableApp;

import com.patres.timetable.domain.Properties;
import com.patres.timetable.repository.PropertiesRepository;
import com.patres.timetable.service.PropertiesService;
import com.patres.timetable.service.dto.PropertiesDTO;
import com.patres.timetable.service.mapper.PropertiesMapper;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PropertiesResource REST controller.
 *
 * @see PropertiesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TimetableApp.class)
public class PropertiesResourceIntTest {

    private static final String DEFAULT_PROPERTY_KEY = "AAAAAAAAAA";
    private static final String UPDATED_PROPERTY_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_PROPERTY_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_PROPERTY_VALUE = "BBBBBBBBBB";

    @Autowired
    private PropertiesRepository propertiesRepository;

    @Autowired
    private PropertiesMapper propertiesMapper;

    @Autowired
    private PropertiesService propertiesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPropertiesMockMvc;

    private Properties properties;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PropertiesResource propertiesResource = new PropertiesResource(propertiesService);
        this.restPropertiesMockMvc = MockMvcBuilders.standaloneSetup(propertiesResource)
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
    public static Properties createEntity(EntityManager em) {
        Properties properties = new Properties()
            .propertyKey(DEFAULT_PROPERTY_KEY)
            .propertyValue(DEFAULT_PROPERTY_VALUE);
        return properties;
    }

    @Before
    public void initTest() {
        properties = createEntity(em);
    }

    @Test
    @Transactional
    public void createProperties() throws Exception {
        int databaseSizeBeforeCreate = propertiesRepository.findAll().size();

        // Create the Properties
        PropertiesDTO propertiesDTO = propertiesMapper.toDto(properties);
        restPropertiesMockMvc.perform(post("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertiesDTO)))
            .andExpect(status().isCreated());

        // Validate the Properties in the database
        List<Properties> propertiesList = propertiesRepository.findAll();
        assertThat(propertiesList).hasSize(databaseSizeBeforeCreate + 1);
        Properties testProperties = propertiesList.get(propertiesList.size() - 1);
        assertThat(testProperties.getPropertyKey()).isEqualTo(DEFAULT_PROPERTY_KEY);
        assertThat(testProperties.getPropertyValue()).isEqualTo(DEFAULT_PROPERTY_VALUE);
    }

    @Test
    @Transactional
    public void createPropertiesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = propertiesRepository.findAll().size();

        // Create the Properties with an existing ID
        properties.setId(1L);
        PropertiesDTO propertiesDTO = propertiesMapper.toDto(properties);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropertiesMockMvc.perform(post("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertiesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Properties> propertiesList = propertiesRepository.findAll();
        assertThat(propertiesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPropertyKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = propertiesRepository.findAll().size();
        // set the field null
        properties.setPropertyKey(null);

        // Create the Properties, which fails.
        PropertiesDTO propertiesDTO = propertiesMapper.toDto(properties);

        restPropertiesMockMvc.perform(post("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertiesDTO)))
            .andExpect(status().isBadRequest());

        List<Properties> propertiesList = propertiesRepository.findAll();
        assertThat(propertiesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProperties() throws Exception {
        // Initialize the database
        propertiesRepository.saveAndFlush(properties);

        // Get all the propertiesList
        restPropertiesMockMvc.perform(get("/api/properties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(properties.getId().intValue())))
            .andExpect(jsonPath("$.[*].propertyKey").value(hasItem(DEFAULT_PROPERTY_KEY.toString())))
            .andExpect(jsonPath("$.[*].propertyValue").value(hasItem(DEFAULT_PROPERTY_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getProperties() throws Exception {
        // Initialize the database
        propertiesRepository.saveAndFlush(properties);

        // Get the properties
        restPropertiesMockMvc.perform(get("/api/properties/{id}", properties.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(properties.getId().intValue()))
            .andExpect(jsonPath("$.propertyKey").value(DEFAULT_PROPERTY_KEY.toString()))
            .andExpect(jsonPath("$.propertyValue").value(DEFAULT_PROPERTY_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProperties() throws Exception {
        // Get the properties
        restPropertiesMockMvc.perform(get("/api/properties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProperties() throws Exception {
        // Initialize the database
        propertiesRepository.saveAndFlush(properties);
        int databaseSizeBeforeUpdate = propertiesRepository.findAll().size();

        // Update the properties
        Properties updatedProperties = propertiesRepository.findOne(properties.getId());
        updatedProperties
            .propertyKey(UPDATED_PROPERTY_KEY)
            .propertyValue(UPDATED_PROPERTY_VALUE);
        PropertiesDTO propertiesDTO = propertiesMapper.toDto(updatedProperties);

        restPropertiesMockMvc.perform(put("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertiesDTO)))
            .andExpect(status().isOk());

        // Validate the Properties in the database
        List<Properties> propertiesList = propertiesRepository.findAll();
        assertThat(propertiesList).hasSize(databaseSizeBeforeUpdate);
        Properties testProperties = propertiesList.get(propertiesList.size() - 1);
        assertThat(testProperties.getPropertyKey()).isEqualTo(UPDATED_PROPERTY_KEY);
        assertThat(testProperties.getPropertyValue()).isEqualTo(UPDATED_PROPERTY_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingProperties() throws Exception {
        int databaseSizeBeforeUpdate = propertiesRepository.findAll().size();

        // Create the Properties
        PropertiesDTO propertiesDTO = propertiesMapper.toDto(properties);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPropertiesMockMvc.perform(put("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertiesDTO)))
            .andExpect(status().isCreated());

        // Validate the Properties in the database
        List<Properties> propertiesList = propertiesRepository.findAll();
        assertThat(propertiesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProperties() throws Exception {
        // Initialize the database
        propertiesRepository.saveAndFlush(properties);
        int databaseSizeBeforeDelete = propertiesRepository.findAll().size();

        // Get the properties
        restPropertiesMockMvc.perform(delete("/api/properties/{id}", properties.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Properties> propertiesList = propertiesRepository.findAll();
        assertThat(propertiesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Properties.class);
        Properties properties1 = new Properties();
        properties1.setId(1L);
        Properties properties2 = new Properties();
        properties2.setId(properties1.getId());
        assertThat(properties1).isEqualTo(properties2);
        properties2.setId(2L);
        assertThat(properties1).isNotEqualTo(properties2);
        properties1.setId(null);
        assertThat(properties1).isNotEqualTo(properties2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PropertiesDTO.class);
        PropertiesDTO propertiesDTO1 = new PropertiesDTO();
        propertiesDTO1.setId(1L);
        PropertiesDTO propertiesDTO2 = new PropertiesDTO();
        assertThat(propertiesDTO1).isNotEqualTo(propertiesDTO2);
        propertiesDTO2.setId(propertiesDTO1.getId());
        assertThat(propertiesDTO1).isEqualTo(propertiesDTO2);
        propertiesDTO2.setId(2L);
        assertThat(propertiesDTO1).isNotEqualTo(propertiesDTO2);
        propertiesDTO1.setId(null);
        assertThat(propertiesDTO1).isNotEqualTo(propertiesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(propertiesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(propertiesMapper.fromId(null)).isNull();
    }
}
