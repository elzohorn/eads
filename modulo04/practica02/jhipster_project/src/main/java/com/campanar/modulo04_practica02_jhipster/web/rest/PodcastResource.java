package com.campanar.modulo04_practica02_jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.campanar.modulo04_practica02_jhipster.domain.Podcast;

import com.campanar.modulo04_practica02_jhipster.repository.PodcastRepository;
import com.campanar.modulo04_practica02_jhipster.web.rest.util.HeaderUtil;
import com.campanar.modulo04_practica02_jhipster.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Podcast.
 */
@RestController
@RequestMapping("/api")
public class PodcastResource {

    private final Logger log = LoggerFactory.getLogger(PodcastResource.class);
        
    @Inject
    private PodcastRepository podcastRepository;

    /**
     * POST  /podcasts : Create a new podcast.
     *
     * @param podcast the podcast to create
     * @return the ResponseEntity with status 201 (Created) and with body the new podcast, or with status 400 (Bad Request) if the podcast has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/podcasts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Podcast> createPodcast(@RequestBody Podcast podcast) throws URISyntaxException {
        log.debug("REST request to save Podcast : {}", podcast);
        if (podcast.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("podcast", "idexists", "A new podcast cannot already have an ID")).body(null);
        }
        Podcast result = podcastRepository.save(podcast);
        return ResponseEntity.created(new URI("/api/podcasts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("podcast", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /podcasts : Updates an existing podcast.
     *
     * @param podcast the podcast to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated podcast,
     * or with status 400 (Bad Request) if the podcast is not valid,
     * or with status 500 (Internal Server Error) if the podcast couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/podcasts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Podcast> updatePodcast(@RequestBody Podcast podcast) throws URISyntaxException {
        log.debug("REST request to update Podcast : {}", podcast);
        if (podcast.getId() == null) {
            return createPodcast(podcast);
        }
        Podcast result = podcastRepository.save(podcast);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("podcast", podcast.getId().toString()))
            .body(result);
    }

    /**
     * GET  /podcasts : get all the podcasts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of podcasts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/podcasts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Podcast>> getAllPodcasts(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Podcasts");
        Page<Podcast> page = podcastRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/podcasts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /podcasts/:id : get the "id" podcast.
     *
     * @param id the id of the podcast to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the podcast, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/podcasts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Podcast> getPodcast(@PathVariable Long id) {
        log.debug("REST request to get Podcast : {}", id);
        Podcast podcast = podcastRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(podcast)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /podcasts/:id : delete the "id" podcast.
     *
     * @param id the id of the podcast to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/podcasts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePodcast(@PathVariable Long id) {
        log.debug("REST request to delete Podcast : {}", id);
        podcastRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("podcast", id.toString())).build();
    }

}
