package com.teamsmartworld.springbootwkshp;

import com.teamsmartworld.springbootwkshp.entity.AppUser;
import com.teamsmartworld.springbootwkshp.entity.Details;
import com.teamsmartworld.springbootwkshp.repository.AppUserRepository;
import com.teamsmartworld.springbootwkshp.repository.DetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SpringbootwkshpApplication {

    private static final Logger log = LoggerFactory.getLogger(SpringbootwkshpApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringbootwkshpApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(AppUserRepository appUserRepository, DetailsRepository detailsRepository) {
        return (args) -> {
            try {
                // Create some sample users with details
                createSampleUsers(appUserRepository);

                // Demonstrate repository operations
                demonstrateRepositoryOperations(appUserRepository, detailsRepository);

            } catch (Exception e) {
                log.error("Error during initialization: ", e);
            }
        };
    }

    private void createSampleUsers(AppUserRepository appUserRepository) {
        // Create first user
        AppUser user1 = new AppUser();
        user1.setUsername("john.doe");
        user1.setName("John Doe");
        user1.setBirthDate(LocalDate.of(1990, 1, 15));

        Details details1 = new Details();
        details1.setEmail("john.doe@example.com");
        details1.setAppUser(user1);
        user1.setDetails(details1);

        // Create second user
        AppUser user2 = new AppUser();
        user2.setUsername("jane.smith");
        user2.setName("Jane Smith");
        user2.setBirthDate(LocalDate.of(1992, 6, 23));

        Details details2 = new Details();
        details2.setEmail("jane.smith@example.com");
        details2.setAppUser(user2);
        user2.setDetails(details2);

        // Create third user
        AppUser user3 = new AppUser();
        user3.setUsername("bob.wilson");
        user3.setName("Bob Wilson");
        user3.setBirthDate(LocalDate.of(1988, 12, 8));

        Details details3 = new Details();
        details3.setEmail("bob.wilson@example.com");
        details3.setAppUser(user3);
        user3.setDetails(details3);

        // Save all users
        List<AppUser> users = Arrays.asList(user1, user2, user3);
        appUserRepository.saveAll(users);

        log.info("Sample users created successfully");
    }

    private void demonstrateRepositoryOperations(AppUserRepository appUserRepository, DetailsRepository detailsRepository) {
        log.info("-------------------------------");
        log.info("Demonstrating repository operations");
        log.info("-------------------------------");

        // Find user by username
        log.info("Finding user by username 'john.doe':");
        appUserRepository.findByUsername("john.doe")
                .ifPresent(user -> log.info("Found user: {}", user.getName()));

        // Find users by registration date range
        log.info("Finding users registered today:");
        List<AppUser> todayUsers = appUserRepository.findByDetails_RegistrationDateBetween(
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(1));
        log.info("Found {} users registered today", todayUsers.size());

        // Find by email (case insensitive)
        log.info("Finding user by email (case insensitive):");
        appUserRepository.findByDetails_EmailIgnoreCase("JOHN.DOE@EXAMPLE.COM")
                .ifPresent(user -> log.info("Found user by email: {}", user.getName()));

        // Find details by name contains
        log.info("Finding details where name contains 'Smith':");
        List<Details> smithDetails = detailsRepository.findByAppUser_NameContaining("Smith");
        smithDetails.forEach(details ->
                log.info("Found details for: {}", details.getAppUser().getName()));

        // Find by name ignore case
        log.info("Finding details by exact name (ignore case):");
        List<Details> bobDetails = detailsRepository.findByAppUser_NameIgnoreCase("BOB WILSON");
        bobDetails.forEach(details ->
                log.info("Found details for: {}", details.getAppUser().getName()));

        log.info("-------------------------------");
    }
}

