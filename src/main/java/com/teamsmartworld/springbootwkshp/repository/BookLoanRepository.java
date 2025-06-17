// BookLoanRepository.java
package com.teamsmartworld.springbootwkshp.repository;

import com.teamsmartworld.springbootwkshp.model.BookLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookLoanRepository extends JpaRepository<BookLoan, Integer> {
    
    List<BookLoan> findByBorrower_Id(Integer borrowerId);
    
    List<BookLoan> findByBook_Id(Integer bookId);
    
    List<BookLoan> findByReturnedFalse();
    
    @Query("SELECT bl FROM BookLoan bl WHERE bl.returned = false AND bl.dueDate < CURRENT_DATE")
    List<BookLoan> findOverdueLoans();
    
    List<BookLoan> findByLoanDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Modifying
    @Transactional
    @Query("UPDATE BookLoan bl SET bl.returned = true WHERE bl.id = ?1")
    void markAsReturned(Integer loanId);
}