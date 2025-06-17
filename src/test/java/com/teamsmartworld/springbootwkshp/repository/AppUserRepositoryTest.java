// AppUserRepositoryTest.java
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
class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private DetailsRepository detailsRepository;

    private AppUser testUser;
    private Details testDetails;

    @BeforeEach
    void setUp() {
        testUser = new AppUser();
        testUser.setUsername("testuser");
        testUser.setName("Test User");
        testUser.setBirthDate(LocalDate.of(1990, 1, 1));

        testDetails = new Details();
        testDetails.setEmail("test@example.com");
        testDetails.setAppUser(testUser);
        testUser.setDetails(testDetails);

        appUserRepository.save(testUser);
    }

    @Test
    @DisplayName("Should find user by username")
    void shouldFindByUsername() {
        Optional<AppUser> found = appUserRepository.findByUsername("testuser");

        assertThat(found)
                .isPresent()
                .hasValueSatisfying(user -> {
                    assertThat(user.getName()).isEqualTo("Test User");
                    assertThat(user.getDetails().getEmail()).isEqualTo("test@example.com");
                });
    }

    @Test
    @DisplayName("Should find users by registration date range")
    void shouldFindByRegistrationDateBetween() {
        LocalDate startDate = LocalDate.now().minusDays(1);
        LocalDate endDate = LocalDate.now().plusDays(1);

        List<AppUser> users = appUserRepository
                .findByDetails_RegistrationDateBetween(startDate, endDate);

        assertThat(users).hasSize(1);
        assertThat(users.get(0).getUsername()).isEqualTo("testuser");
    }

    @Test
    @DisplayName("Should find user by details id")
    void shouldFindByDetailsId() {
        Optional<AppUser> found = appUserRepository
                .findByDetails_Id(testDetails.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("testuser");
    }

    @Test
    @DisplayName("Should find user by email ignore case")
    void shouldFindByEmailIgnoreCase() {
        Optional<AppUser> found = appUserRepository
                .findByDetails_EmailIgnoreCase("TEST@EXAMPLE.COM");

        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("testuser");
    }

    @Test
    @DisplayName("Should not allow duplicate username")
    void shouldNotAllowDuplicateUsername() {
        AppUser duplicateUser = new AppUser();
        duplicateUser.setUsername("testuser");
        duplicateUser.setName("Another User");

        assertThrows(DataIntegrityViolationException.class, () -> {
            appUserRepository.save(duplicateUser);
            appUserRepository.flush();
        });
    }
}