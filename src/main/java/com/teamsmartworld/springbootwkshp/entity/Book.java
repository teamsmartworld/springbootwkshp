// Book.java
package com.teamsmartworld.springbootwkshp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column(name = "max_loan_days", nullable = false)
    private Integer maxLoanDays;
    
    @ManyToMany(mappedBy = "writtenBooks")
    private Set<Author> authors = new HashSet<>();
    
    // Add an author to this book (bidirectional)
    public void addAuthor(Author author) {
        authors.add(author);
        author.getWrittenBooks().add(this);
    }
    
    // Remove an author from this book (bidirectional)
    public void removeAuthor(Author author) {
        authors.remove(author);
        author.getWrittenBooks().remove(this);
    }
}