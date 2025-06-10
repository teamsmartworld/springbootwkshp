// DetailsRepositoryTest.java
package com.teamsmartworld.springbootwkshp.repository;

import com.teamsmartworld.springbootwkshp.model.AppUser;
import com.teamsmartworld.springbootwkshp.model.Details;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class DetailsRepositoryTest {

    @Autowired
    private DetailsRepository detailsRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    private Details testDetails;
    private AppUser testUser;

    @BeforeEach
    void setUp() {
        // Create and save test user
        testUser = new AppUser();
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setBirthDate(LocalDate.of(1990, 1, 1));
        testUser = appUserRepository.save(testUser);

        // Create test details
        testDetails = new Details();
        testDetails.setUsername("testuser");
        testDetails.setPassword("TestPass123!");
        testDetails.setRegDate(LocalDate.now());
        testDetails.setUserDetails(testUser);
    }

    @Test
    void shouldSaveDetails() {
        Details savedDetails = detailsRepository.save(testDetails);
        assertThat(savedDetails.getId()).isNotNull();
        assertThat(savedDetails.getUsername()).isEqualTo(testDetails.getUsername());
    }

    @Test
    void shouldFindByUsername() {
        detailsRepository.save(testDetails);
        Optional<Details> found = detailsRepository.findByUsername("testuser");
        assertThat(found).isPresent();
        assertThat(found.get().getUserDetails().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void shouldFindByRegDateBetween() {
        detailsRepository.save(testDetails);
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(1);

        List<Details> details = detailsRepository.findByRegDateBetween(startDate, endDate);
        assertThat(details).hasSize(1);
        assertThat(details.get(0).getUsername()).isEqualTo(testDetails.getUsername());
    }

    @Test
    void shouldCheckIfUsernameExists() {
        detailsRepository.save(testDetails);
        boolean exists = detailsRepository.existsByUsername("testuser");
        assertThat(exists).isTrue();
    }
}
