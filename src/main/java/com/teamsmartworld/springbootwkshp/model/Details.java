package com.teamsmartworld.springbootwkshp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Table(name = "user_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Details {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username cannot be empty")
    @Pattern(regexp = "^[a-zA-Z0-9]{3,20}$",
            message = "Username must be between 3 and 20 characters and contain only letters and numbers")
    private String username;

    @Column(nullable = false)
    @NotBlank(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must be at least 8 characters long and contain at least one digit, " +
                    "one uppercase letter, one lowercase letter, and one special character")
    private String password;

    @Column(name = "registration_date")
    @CreatedDate
    private LocalDate regDate;

    @OneToOne
    @JoinColumn(name = "app_user_id")
    private AppUser userDetails;

    // Custom constructor without id (useful for creating new users)
    public Details(String username, String password, AppUser userDetails) {
        this.username = username;
        this.password = password;
        this.regDate = LocalDate.now();
        this.userDetails = userDetails;
    }

    // Override toString to exclude password for security
    @Override
    public String toString() {
        return "Details{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", regDate=" + regDate +
                ", userDetails=" + userDetails +
                '}';
    }
}
