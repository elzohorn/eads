package com.campanar.modulo04_practica02_jhipster.web.rest;

import com.campanar.modulo04_practica02_jhipster.Modulo04Practica02JhipsterApp;

import com.campanar.modulo04_practica02_jhipster.domain.Person;
import com.campanar.modulo04_practica02_jhipster.repository.PersonRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PersonResource REST controller.
 *
 * @see PersonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Modulo04Practica02JhipsterApp.class)
public class PersonResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private PersonRepository personRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPersonMockMvc;

    private Person person;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PersonResource personResource = new PersonResource();
        ReflectionTestUtils.setField(personResource, "personRepository", personRepository);
        this.restPersonMockMvc = MockMvcBuilders.standaloneSetup(personResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Person createEntity(EntityManager em) {
        Person person = new Person()
                .name(DEFAULT_NAME);
        return person;
    }

    @Before
    public void initTest() {
        person = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerson() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // Create the Person

        restPersonMockMvc.perform(post("/api/people")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(person)))
                .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> people = personRepository.findAll();
        assertThat(people).hasSize(databaseSizeBeforeCreate + 1);
        Person testPerson = people.get(people.size() - 1);
        assertThat(testPerson.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllPeople() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the people
        restPersonMockMvc.perform(get("/api/people?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get the person
        restPersonMockMvc.perform(get("/api/people/{id}", person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(person.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPerson() throws Exception {
        // Get the person
        restPersonMockMvc.perform(get("/api/people/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);
        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person
        Person updatedPerson = personRepository.findOne(person.getId());
        updatedPerson
                .name(UPDATED_NAME);

        restPersonMockMvc.perform(put("/api/people")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPerson)))
                .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> people = personRepository.findAll();
        assertThat(people).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = people.get(people.size() - 1);
        assertThat(testPerson.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deletePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);
        int databaseSizeBeforeDelete = personRepository.findAll().size();

        // Get the person
        restPersonMockMvc.perform(delete("/api/people/{id}", person.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Person> people = personRepository.findAll();
        assertThat(people).hasSize(databaseSizeBeforeDelete - 1);
    }
}
