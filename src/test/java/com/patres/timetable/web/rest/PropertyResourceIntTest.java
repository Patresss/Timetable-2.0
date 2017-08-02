package com.patres.timetable.web.rest;

import com.patres.timetable.TimetableApp;

import com.patres.timetable.domain.Property;
import com.patres.timetable.repository.PropertyRepository;
import com.patres.timetable.service.PropertyService;
import com.patres.timetable.service.dto.PropertyDTO;
import com.patres.timetable.service.mapper.PropertyMapper;
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
 * @see PropertyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TimetableApp.class)
public class PropertyResourceIntTest {

    private static final String DEFAULT_PROPERTY_KEY = "AAAAAAAAAA";
    private static final String UPDATED_PROPERTY_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_PROPERTY_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_PROPERTY_VALUE = "BBBBBBBBBB";

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyMapper propertyMapper;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPropertiesMockMvc;

    private Property property;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PropertyResource propertyResource = new PropertyResource(propertyService);
        this.restPropertiesMockMvc = MockMvcBuilders.standaloneSetup(propertyResource)
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
    public static Property createEntity(EntityManager em) {
        Property property = new Property()
            .propertyKey(DEFAULT_PROPERTY_KEY)
            .propertyValue(DEFAULT_PROPERTY_VALUE);
        return property;
    }

    @Before
    public void initTest() {
        property = createEntity(em);
    }

    @Test
    @Transactional
    public void createProperties() throws Exception {
        int databaseSizeBeforeCreate = propertyRepository.findAll().size();

        // Create the Property
        PropertyDTO propertyDTO = propertyMapper.toDto(property);
        restPropertiesMockMvc.perform(post("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyDTO)))
            .andExpect(status().isCreated());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeCreate + 1);
        Property testProperty = propertyList.get(propertyList.size() - 1);
        assertThat(testProperty.getPropertyKey()).isEqualTo(DEFAULT_PROPERTY_KEY);
        assertThat(testProperty.getPropertyValue()).isEqualTo(DEFAULT_PROPERTY_VALUE);
    }

    @Test
    @Transactional
    public void createPropertiesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = propertyRepository.findAll().size();

        // Create the Property with an existing ID
        property.setId(1L);
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPropertiesMockMvc.perform(post("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPropertyKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = propertyRepository.findAll().size();
        // set the field null
        property.setPropertyKey(null);

        // Create the Property, which fails.
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        restPropertiesMockMvc.perform(post("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyDTO)))
            .andExpect(status().isBadRequest());

        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProperties() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get all the propertiesList
        restPropertiesMockMvc.perform(get("/api/properties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(property.getId().intValue())))
            .andExpect(jsonPath("$.[*].propertyKey").value(hasItem(DEFAULT_PROPERTY_KEY.toString())))
            .andExpect(jsonPath("$.[*].propertyValue").value(hasItem(DEFAULT_PROPERTY_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getProperties() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);

        // Get the property
        restPropertiesMockMvc.perform(get("/api/properties/{id}", property.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(property.getId().intValue()))
            .andExpect(jsonPath("$.propertyKey").value(DEFAULT_PROPERTY_KEY.toString()))
            .andExpect(jsonPath("$.propertyValue").value(DEFAULT_PROPERTY_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProperties() throws Exception {
        // Get the property
        restPropertiesMockMvc.perform(get("/api/properties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProperties() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);
        int databaseSizeBeforeUpdate = propertyRepository.findAll().size();

        // Update the property
        Property updatedProperty = propertyRepository.findOne(property.getId());
        updatedProperty
            .propertyKey(UPDATED_PROPERTY_KEY)
            .propertyValue(UPDATED_PROPERTY_VALUE);
        PropertyDTO propertyDTO = propertyMapper.toDto(updatedProperty);

        restPropertiesMockMvc.perform(put("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyDTO)))
            .andExpect(status().isOk());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeUpdate);
        Property testProperty = propertyList.get(propertyList.size() - 1);
        assertThat(testProperty.getPropertyKey()).isEqualTo(UPDATED_PROPERTY_KEY);
        assertThat(testProperty.getPropertyValue()).isEqualTo(UPDATED_PROPERTY_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingProperties() throws Exception {
        int databaseSizeBeforeUpdate = propertyRepository.findAll().size();

        // Create the Property
        PropertyDTO propertyDTO = propertyMapper.toDto(property);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPropertiesMockMvc.perform(put("/api/properties")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(propertyDTO)))
            .andExpect(status().isCreated());

        // Validate the Property in the database
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProperties() throws Exception {
        // Initialize the database
        propertyRepository.saveAndFlush(property);
        int databaseSizeBeforeDelete = propertyRepository.findAll().size();

        // Get the property
        restPropertiesMockMvc.perform(delete("/api/properties/{id}", property.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Property> propertyList = propertyRepository.findAll();
        assertThat(propertyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Property.class);
        Property property1 = new Property();
        property1.setId(1L);
        Property property2 = new Property();
        property2.setId(property1.getId());
        assertThat(property1).isEqualTo(property2);
        property2.setId(2L);
        assertThat(property1).isNotEqualTo(property2);
        property1.setId(null);
        assertThat(property1).isNotEqualTo(property2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PropertyDTO.class);
        PropertyDTO propertyDTO1 = new PropertyDTO();
        propertyDTO1.setId(1L);
        PropertyDTO propertyDTO2 = new PropertyDTO();
        assertThat(propertyDTO1).isNotEqualTo(propertyDTO2);
        propertyDTO2.setId(propertyDTO1.getId());
        assertThat(propertyDTO1).isEqualTo(propertyDTO2);
        propertyDTO2.setId(2L);
        assertThat(propertyDTO1).isNotEqualTo(propertyDTO2);
        propertyDTO1.setId(null);
        assertThat(propertyDTO1).isNotEqualTo(propertyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(propertyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(propertyMapper.fromId(null)).isNull();
    }
}
