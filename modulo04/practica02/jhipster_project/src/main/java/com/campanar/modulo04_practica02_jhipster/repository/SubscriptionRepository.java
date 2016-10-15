package com.campanar.modulo04_practica02_jhipster.repository;

import com.campanar.modulo04_practica02_jhipster.domain.Subscription;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Subscription entity.
 */
@SuppressWarnings("unused")
public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {

    @Query("select distinct subscription from Subscription subscription left join fetch subscription.channels")
    List<Subscription> findAllWithEagerRelationships();

    @Query("select subscription from Subscription subscription left join fetch subscription.channels where subscription.id =:id")
    Subscription findOneWithEagerRelationships(@Param("id") Long id);

}
