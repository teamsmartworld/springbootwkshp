// AuthorRepository.java
package com.teamsmartworld.springbootwkshp.repository;

import com.teamsmartworld.springbootwkshp.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    
    List<Author> findByFirstName(String firstName);
    
    List<Author> findByLastName(String lastName);
    
    List<Author> findByFirstNameContainingOrLastNameContaining(String keyword, String sameKeyword);
    
    @Query("SELECT a FROM Author a JOIN a.writtenBooks b WHERE b.id = ?1")
    List<Author> findByWrittenBooks_Id(Integer bookId);
    
    @Modifying
    @Transactional
    @Query("UPDATE Author a SET a.firstName = ?2, a.lastName = ?3 WHERE a.id = ?1")
    void updateNameById(Integer id, String firstName, String lastName);
    
    void deleteById(Integer id);
}