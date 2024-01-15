package com.svichkar.eureka.personservice.repository;

import com.svichkar.eureka.personservice.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findPersonById(Long id);
}
