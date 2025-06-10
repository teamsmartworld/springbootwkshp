// DetailsRepository.java
package com.teamsmartworld.springbootwkshp.repository;

import com.teamsmartworld.springbootwkshp.model.Details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DetailsRepository extends JpaRepository<Details, Integer> {
    Optional<Details> findByUsername(String username);
    List<Details> findByRegDateBetween(LocalDate startDate, LocalDate endDate);
    boolean existsByUsername(String username);
}
