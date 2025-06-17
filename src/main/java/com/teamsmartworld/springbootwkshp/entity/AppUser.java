// AppUser.java
package com.teamsmartworld.springbootwkshp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Name cannot be empty")
    private String name;

    private LocalDate birthDate;

    @OneToOne(mappedBy = "appUser", cascade = CascadeType.ALL)
    private Details details;
}