// BookRepository.java
package com.teamsmartworld.springbootwkshp.repository;

import com.teamsmartworld.springbootwkshp.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    
    Optional<Book> findByIsbnIgnoreCase(String isbn);
    
    List<Book> findByTitleContaining(String titleFragment);
    
    List<Book> findByMaxLoanDaysLessThan(Integer days);
}