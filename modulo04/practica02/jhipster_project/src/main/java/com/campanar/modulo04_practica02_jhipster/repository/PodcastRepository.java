package com.campanar.modulo04_practica02_jhipster.repository;

import com.campanar.modulo04_practica02_jhipster.domain.Podcast;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Podcast entity.
 */
@SuppressWarnings("unused")
public interface PodcastRepository extends JpaRepository<Podcast,Long> {

    @Query("select distinct podcast from Podcast podcast left join fetch podcast.authors")
    List<Podcast> findAllWithEagerRelationships();

    @Query("select podcast from Podcast podcast left join fetch podcast.authors where podcast.id =:id")
    Podcast findOneWithEagerRelationships(@Param("id") Long id);

}
