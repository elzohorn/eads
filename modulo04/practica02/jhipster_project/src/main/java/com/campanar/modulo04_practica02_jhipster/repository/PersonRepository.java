package com.campanar.modulo04_practica02_jhipster.repository;

import com.campanar.modulo04_practica02_jhipster.domain.Person;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Person entity.
 */
@SuppressWarnings("unused")
public interface PersonRepository extends JpaRepository<Person,Long> {

}
