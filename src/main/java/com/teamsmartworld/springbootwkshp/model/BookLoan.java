// BookLoan.java
package com.teamsmartworld.springbootwkshp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "book_loans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private LocalDate loanDate;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private boolean returned;

    @ManyToOne
    @JoinColumn(name = "borrower_id", nullable = false)
    private AppUser borrower;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @PrePersist
    protected void onCreate() {
        if (loanDate == null) {
            loanDate = LocalDate.now();
        }
        if (dueDate == null && book != null) {
            dueDate = loanDate.plusDays(book.getMaxLoanDays());
        }
    }
}