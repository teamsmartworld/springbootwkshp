// DetailsRepositoryTest.java
package com.teamsmartworld.springbootwkshp.repository;

import com.teamsmartworld.springbootwkshp.entity.AppUser;
import com.teamsmartworld.springbootwkshp.entity.Details;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        testUser = new AppUser();
        testUser.setUsername("testuser");
        testUser.setName("Test User");

        testDetails = new Details();
        testDetails.setEmail("test@example.com");
        testDetails.setAppUser(testUser);
        testUser.setDetails(testDetails);

        appUserRepository.save(testUser);
    }

    @Test
    @DisplayName("Should find details by email")
    void shouldFindByEmail() {
        Optional<Details> found = detailsRepository.findByEmail("test@example.com");

        assertThat(found)
                .isPresent()
                .hasValueSatisfying(details -> {
                    assertThat(details.getAppUser().getUsername()).isEqualTo("testuser");
                });
    }

    @Test
    @DisplayName("Should find details by user name contains")
    void shouldFindByNameContains() {
        List<Details> found = detailsRepository.findByAppUser_NameContaining("Test");

        assertThat(found).hasSize(1);
        assertThat(found.get(0).getEmail()).isEqualTo("test@example.com");
    }

    @Test
    @DisplayName("Should find details by user name ignore case")
    void shouldFindByNameIgnoreCase() {
        List<Details> found = detailsRepository.findByAppUser_NameIgnoreCase("TEST USER");

        assertThat(found).hasSize(1);
        assertThat(found.get(0).getEmail()).isEqualTo("test@example.com");
    }

    @Test
    @DisplayName("Should not allow duplicate email")
    void shouldNotAllowDuplicateEmail() {
        AppUser anotherUser = new AppUser();
        anotherUser.setUsername("another");
        anotherUser.setName("Another User");

        Details duplicateDetails = new Details();
        duplicateDetails.setEmail("test@example.com");
        duplicateDetails.setAppUser(anotherUser);
        anotherUser.setDetails(duplicateDetails);

        assertThrows(DataIntegrityViolationException.class, () -> {
            appUserRepository.save(anotherUser);
            appUserRepository.flush();
        });
    }

    @Test
    @DisplayName("Should set registration date automatically")
    void shouldSetRegistrationDateAutomatically() {
        assertThat(testDetails.getRegistrationDate())
                .isNotNull();
        // Using isEqualTo with LocalDate.now() can be flaky if the test runs near midnight
        // Better to check that it's today's date
        assertThat(testDetails.getRegistrationDate().isEqual(LocalDate.now())).isTrue();
    }
}