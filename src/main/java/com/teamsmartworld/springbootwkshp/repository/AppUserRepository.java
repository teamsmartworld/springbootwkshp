// AppUserRepository.java
package com.teamsmartworld.springbootwkshp.repository;

import com.teamsmartworld.springbootwkshp.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

    Optional<AppUser> findByUsername(String username);

    List<AppUser> findByDetails_RegistrationDateBetween(LocalDate startDate, LocalDate endDate);

    Optional<AppUser> findByDetails_Id(Integer detailsId);

    Optional<AppUser> findByDetails_EmailIgnoreCase(String email);

    boolean existsByUsername(String username);

    // Additional useful methods
    List<AppUser> findByNameContainingIgnoreCase(String namePart);

    List<AppUser> findByBirthDateBefore(LocalDate date);
}
