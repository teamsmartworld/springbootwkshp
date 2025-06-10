
package com.teamsmartworld.springbootwkshp.model;

import jakarta.persistence.*;
        import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity // This annotation marks the class as a JPA entity
@Table(name = "app_users") // This annotation specifies the table name in the database
@Data // Lombok annotation to generate getters, setters, toString, equals, and hashCode methods
@NoArgsConstructor // Lombok annotation to generate a no-args constructor
@AllArgsConstructor // Lombok annotation to generate a constructor with all fields
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    private LocalDate birthDate;

    // In AppUser.java, add this field:
    @OneToOne(mappedBy = "userDetails", cascade = CascadeType.ALL)
    private Details details;
}
