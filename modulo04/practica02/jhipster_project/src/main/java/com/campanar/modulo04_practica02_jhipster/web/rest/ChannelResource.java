package com.campanar.modulo04_practica02_jhipster.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.campanar.modulo04_practica02_jhipster.domain.Channel;

import com.campanar.modulo04_practica02_jhipster.repository.ChannelRepository;
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
 * REST controller for managing Channel.
 */
@RestController
@RequestMapping("/api")
public class ChannelResource {

    private final Logger log = LoggerFactory.getLogger(ChannelResource.class);
        
    @Inject
    private ChannelRepository channelRepository;

    /**
     * POST  /channels : Create a new channel.
     *
     * @param channel the channel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new channel, or with status 400 (Bad Request) if the channel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/channels",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Channel> createChannel(@RequestBody Channel channel) throws URISyntaxException {
        log.debug("REST request to save Channel : {}", channel);
        if (channel.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("channel", "idexists", "A new channel cannot already have an ID")).body(null);
        }
        Channel result = channelRepository.save(channel);
        return ResponseEntity.created(new URI("/api/channels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("channel", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /channels : Updates an existing channel.
     *
     * @param channel the channel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated channel,
     * or with status 400 (Bad Request) if the channel is not valid,
     * or with status 500 (Internal Server Error) if the channel couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/channels",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Channel> updateChannel(@RequestBody Channel channel) throws URISyntaxException {
        log.debug("REST request to update Channel : {}", channel);
        if (channel.getId() == null) {
            return createChannel(channel);
        }
        Channel result = channelRepository.save(channel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("channel", channel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /channels : get all the channels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of channels in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/channels",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Channel>> getAllChannels(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Channels");
        Page<Channel> page = channelRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/channels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /channels/:id : get the "id" channel.
     *
     * @param id the id of the channel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the channel, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/channels/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Channel> getChannel(@PathVariable Long id) {
        log.debug("REST request to get Channel : {}", id);
        Channel channel = channelRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(channel)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /channels/:id : delete the "id" channel.
     *
     * @param id the id of the channel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/channels/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteChannel(@PathVariable Long id) {
        log.debug("REST request to delete Channel : {}", id);
        channelRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("channel", id.toString())).build();
    }

}
