package com.campanar.modulo04_practica02_jhipster.web.rest;

import com.campanar.modulo04_practica02_jhipster.Modulo04Practica02JhipsterApp;

import com.campanar.modulo04_practica02_jhipster.domain.Podcast;
import com.campanar.modulo04_practica02_jhipster.repository.PodcastRepository;

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
 * Test class for the PodcastResource REST controller.
 *
 * @see PodcastResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Modulo04Practica02JhipsterApp.class)
public class PodcastResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAA";
    private static final String UPDATED_TITLE = "BBBBB";
    private static final String DEFAULT_DESCRIPTION = "AAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBB";

    @Inject
    private PodcastRepository podcastRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restPodcastMockMvc;

    private Podcast podcast;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PodcastResource podcastResource = new PodcastResource();
        ReflectionTestUtils.setField(podcastResource, "podcastRepository", podcastRepository);
        this.restPodcastMockMvc = MockMvcBuilders.standaloneSetup(podcastResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Podcast createEntity(EntityManager em) {
        Podcast podcast = new Podcast()
                .title(DEFAULT_TITLE)
                .description(DEFAULT_DESCRIPTION);
        return podcast;
    }

    @Before
    public void initTest() {
        podcast = createEntity(em);
    }

    @Test
    @Transactional
    public void createPodcast() throws Exception {
        int databaseSizeBeforeCreate = podcastRepository.findAll().size();

        // Create the Podcast

        restPodcastMockMvc.perform(post("/api/podcasts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(podcast)))
                .andExpect(status().isCreated());

        // Validate the Podcast in the database
        List<Podcast> podcasts = podcastRepository.findAll();
        assertThat(podcasts).hasSize(databaseSizeBeforeCreate + 1);
        Podcast testPodcast = podcasts.get(podcasts.size() - 1);
        assertThat(testPodcast.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPodcast.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPodcasts() throws Exception {
        // Initialize the database
        podcastRepository.saveAndFlush(podcast);

        // Get all the podcasts
        restPodcastMockMvc.perform(get("/api/podcasts?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(podcast.getId().intValue())))
                .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getPodcast() throws Exception {
        // Initialize the database
        podcastRepository.saveAndFlush(podcast);

        // Get the podcast
        restPodcastMockMvc.perform(get("/api/podcasts/{id}", podcast.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(podcast.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPodcast() throws Exception {
        // Get the podcast
        restPodcastMockMvc.perform(get("/api/podcasts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePodcast() throws Exception {
        // Initialize the database
        podcastRepository.saveAndFlush(podcast);
        int databaseSizeBeforeUpdate = podcastRepository.findAll().size();

        // Update the podcast
        Podcast updatedPodcast = podcastRepository.findOne(podcast.getId());
        updatedPodcast
                .title(UPDATED_TITLE)
                .description(UPDATED_DESCRIPTION);

        restPodcastMockMvc.perform(put("/api/podcasts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPodcast)))
                .andExpect(status().isOk());

        // Validate the Podcast in the database
        List<Podcast> podcasts = podcastRepository.findAll();
        assertThat(podcasts).hasSize(databaseSizeBeforeUpdate);
        Podcast testPodcast = podcasts.get(podcasts.size() - 1);
        assertThat(testPodcast.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPodcast.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deletePodcast() throws Exception {
        // Initialize the database
        podcastRepository.saveAndFlush(podcast);
        int databaseSizeBeforeDelete = podcastRepository.findAll().size();

        // Get the podcast
        restPodcastMockMvc.perform(delete("/api/podcasts/{id}", podcast.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Podcast> podcasts = podcastRepository.findAll();
        assertThat(podcasts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
