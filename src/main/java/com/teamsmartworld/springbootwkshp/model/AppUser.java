// AppUser.java
package com.teamsmartworld.springbootwkshp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "app_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email cannot be empty")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Name cannot be empty")
    @Column(nullable = false)
    private String name;

    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @OneToOne(mappedBy = "userDetails", cascade = CascadeType.ALL)
    private Details details;
}
