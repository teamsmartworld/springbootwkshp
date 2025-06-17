// Author.java
package com.teamsmartworld.springbootwkshp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "author_books",
        joinColumns = @JoinColumn(name = "author_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private Set<Book> writtenBooks = new HashSet<>();

    // Constructor with name fields
    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Add a book to author's written books
    public void addBook(Book book) {
        writtenBooks.add(book);
    }

    // Remove a book from author's written books
    public void removeBook(Book book) {
        writtenBooks.remove(book);
    }

    // Get full name
    public String getFullName() {
        return firstName + " " + lastName;
    }
}