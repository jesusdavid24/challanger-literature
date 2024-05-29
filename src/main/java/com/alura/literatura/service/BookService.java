//package com.alura.literatura.service;
//
//import com.alura.literatura.dto.BookDTO;
//import com.alura.literatura.model.Authors;
//import com.alura.literatura.model.Book;
//import com.alura.literatura.repository.BookRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Service
//public class BookService {
//  @Autowired
//  private BookRepository repository;
//
//  public List<BookDTO> listAllBooks() {
//    return dataConverter(repository.findAllBook());
//  }
//
//  public List<BookDTO> dataConverter(List<Book> books) {
//    return books.stream()
//      .map(b -> new BookDTO(b.getTitle(), (Set<Authors>) b.getAuthors(), b.getLanguages(), b.getDownloadCount()))
//      .collect(Collectors.toList());
//  }
//
//}
