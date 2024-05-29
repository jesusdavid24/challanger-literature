package com.alura.literatura.repository;

import com.alura.literatura.model.Authors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Authors, Long> {
  @Query("SELECT a FROM Authors a WHERE a.name = :name")
  Optional<Authors> findAuthorsByName(@Param("name") String name);
}
