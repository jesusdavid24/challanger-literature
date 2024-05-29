package com.alura.literatura.repository;

import com.alura.literatura.model.Authors;
import com.alura.literatura.model.Book;
import com.alura.literatura.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
  @Query("SELECT b FROM Book b WHERE b.title = :titleBook")
  Optional<Book> findBookByTitle(@Param("titleBook") String titleBook);
  @Query("SELECT b FROM Book b JOIN FETCH b.authors JOIN FETCH b.languages")
  List<Book> findAllBook();
  @Query("SELECT a FROM Authors a LEFT JOIN FETCH a.books")
  List<Authors> findAllAuthors();
  @Query("SELECT a FROM Authors a LEFT JOIN FETCH a.books WHERE a.birthYear <= :yearLive " +
    "AND (a.deathYear IS NULL OR a.deathYear >= :yearLive)")
  List<Authors> findAuthorsAliveInYear(@Param("yearLive") int yearLive);
  @Query("SELECT b FROM Book b JOIN FETCH b.authors JOIN FETCH b.languages lang WHERE lang = :language")
  List<Book> findBookByLanguage(@Param("language") Language language);

}
