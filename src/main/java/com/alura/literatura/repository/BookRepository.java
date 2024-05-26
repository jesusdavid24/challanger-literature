package com.alura.literatura.repository;

import com.alura.literatura.model.Authors;
import com.alura.literatura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
  @Query("SELECT b FROM Book b JOIN FETCH b.authors JOIN FETCH b.languages")
  List<Book> findAllBook();

  @Query("SELECT a FROM Authors a JOIN FETCH a.books")
  List<Authors> findAllAuthors();
}
