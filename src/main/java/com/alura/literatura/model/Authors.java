package com.alura.literatura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "authors")
public class Authors {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private  Long id;
  @Column(unique = true)
  private String name;
  private String birthYear;
  private String deathYear;
  @ManyToMany(mappedBy = "authors", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Book> books;

  public Authors(String name, String birthYear, String deathYear) {
    this.name = name;
    this.birthYear = birthYear;
    this.deathYear = deathYear;
  }

  public Authors() {}

  public List<Book> getBooks() {
    return books;
  }

  public void setBooks(List<Book> books) {
    this.books = books;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBirthYear() {
    return birthYear;
  }

  public void setBirthYear(String birthYear) {
    this.birthYear = birthYear;
  }

  public String getDeathYear() {
    return deathYear;
  }

  public void setDeathYear(String deathYear) {
    this.deathYear = deathYear;
  }

  @Override
  public String toString() {
    return "Authors{" +
      "name='" + name + '\'' +
      ", birthYear='" + birthYear + '\'' +
      ", deathYear='" + deathYear + '\'' +
      '}';
  }
}
