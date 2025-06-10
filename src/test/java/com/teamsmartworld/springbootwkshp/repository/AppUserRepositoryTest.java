// AppUserRepositoryTest.java
package com.teamsmartworld.springbootwkshp.repository;

import com.teamsmartworld.springbootwkshp.model.AppUser;
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
class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository appUserRepository;

    private AppUser testUser;

    @BeforeEach
    void setUp() {
        testUser = new AppUser();
        testUser.setName("Test User");
        testUser.setEmail("test@example.com");
        testUser.setBirthDate(LocalDate.of(1990, 1, 1));
    }

    @Test
    void shouldSaveUser() {
        AppUser savedUser = appUserRepository.save(testUser);
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getEmail()).isEqualTo(testUser.getEmail());
    }

    @Test
    void shouldFindByEmail() {
        appUserRepository.save(testUser);
        Optional<AppUser> found = appUserRepository.findByEmail("test@example.com");
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Test User");
    }

    @Test
    void shouldFindByBirthDateBefore() {
        appUserRepository.save(testUser);
        List<AppUser> users = appUserRepository.findByBirthDateBefore(LocalDate.now());
        assertThat(users).hasSize(1);
        assertThat(users.get(0).getEmail()).isEqualTo(testUser.getEmail());
    }

    @Test
    void shouldCheckIfEmailExists() {
        appUserRepository.save(testUser);
        boolean exists = appUserRepository.existsByEmail("test@example.com");
        assertThat(exists).isTrue();
    }
}
