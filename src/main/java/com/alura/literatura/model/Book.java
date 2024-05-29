package com.alura.literatura.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "books")
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private  Long id;
  @Column(unique = true)
  private String title;
  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(
    name = "book_authors",
    joinColumns = @JoinColumn(name = "book_id"),
    inverseJoinColumns = @JoinColumn(name = "author_id")
  )
  private Set<Authors> authors;
  @ElementCollection(targetClass = Language.class, fetch = FetchType.LAZY)
  @CollectionTable(name = "book_languages", joinColumns = @JoinColumn(name = "book_id"))
  @Enumerated(EnumType.STRING)
  @Column(name = "language")
  private List<Language> languages;
  private int downloadCount;

  public Book(BookData bookData) {
    this.title = bookData.title();
    this.languages = bookData.languages().stream().map(Language::fromCode).collect(Collectors.toList());
    this.downloadCount = bookData.downloadCount();
    this.authors = new HashSet<>();
  }

  public Book() {}

  public Set<Authors> getAuthors() {
    return authors;
  }

  public void setAuthors(Set<Authors> authors) {
    this.authors = authors;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<Language> getLanguages() {
    return languages;
  }

  public void setLanguages(List<Language> languages) {
    this.languages = languages;
  }

  public int getDownloadCount() {
    return downloadCount;
  }

  public void setDownloadCount(int downloadCount) {
    this.downloadCount = downloadCount;
  }

  @Override
  public String toString() {
    return
      "title='" + title + '\'' +
      ", authors=" + authors +
      ", languages=" + languages +
      ", downloadCount=" + downloadCount;
  }
}
