// DetailsRepository.java
package com.teamsmartworld.springbootwkshp.repository;

import com.teamsmartworld.springbootwkshp.entity.Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DetailsRepository extends JpaRepository<Details, Integer> {

    Optional<Details> findByEmail(String email);

    List<Details> findByAppUser_NameContaining(String namePart);

    List<Details> findByAppUser_NameIgnoreCase(String name);

    boolean existsByEmail(String email);

    // Additional useful methods
    List<Details> findByRegistrationDateBetween(LocalDate startDate, LocalDate endDate);
}