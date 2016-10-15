package com.campanar.modulo04_practica02_jhipster.repository;

import com.campanar.modulo04_practica02_jhipster.domain.Channel;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Channel entity.
 */
@SuppressWarnings("unused")
public interface ChannelRepository extends JpaRepository<Channel,Long> {

    @Query("select distinct channel from Channel channel left join fetch channel.hosts")
    List<Channel> findAllWithEagerRelationships();

    @Query("select channel from Channel channel left join fetch channel.hosts where channel.id =:id")
    Channel findOneWithEagerRelationships(@Param("id") Long id);

}
