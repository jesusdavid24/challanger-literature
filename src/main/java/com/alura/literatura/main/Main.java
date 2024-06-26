package com.alura.literatura.main;

import com.alura.literatura.model.*;
import com.alura.literatura.repository.AuthorRepository;
import com.alura.literatura.repository.BookRepository;
import com.alura.literatura.service.ApiConnector;
import com.alura.literatura.service.DataConverter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Main {
  private ApiConnector apiConnector = new ApiConnector();
  private DataConverter dataConverter = new DataConverter();
  private Scanner scanner = new Scanner(System.in);
  public static final String BASE_URL = "https://gutendex.com/books";
  private BookRepository repository;
  private AuthorRepository authorRepository;
  private List<Book> book;
  private List<Authors> authors;
  @PersistenceContext
  private EntityManager entityManager;

  public Main(BookRepository repository, AuthorRepository authorRepository) {
    this.repository = repository;
    this.authorRepository = authorRepository;
  }

  public void showMenu() {
//    var json = apiConnector.fetchData(BASE_URL);
//    var data = dataConverter.getData(json, Data.class);
//    System.out.println(data);

    var option = -1;
    while (option != 0) {
      System.out.println("Choose one of the following options");
      var menu = """
        1 - Search book by title
        2 - Search for registered books
        3 - List registered authors
        4 - List living authors in a given year
        5 - List book by language
        6 - Search authors by name
        7 - Top 10 most downloaded books
        0 - Exit
        """;
      System.out.println(menu);
      option = scanner.nextInt();
      scanner.nextLine();

      switch (option) {
        case 1:
          saveBook();
          break;
        case 2:
          searchAllBook();
          break;
        case 3:
          listAuthors();
          break;
        case 4:
          listLivingAuthors();
          break;
        case 5:
          listBooksByLanguage();
          break;
        case 6:
          searchAuthorsByName();
          break;
        case 7:
          top10MostDownloadedBooks();
          break;
        case 0:
          System.out.println("Closing application");
          break;
        default:
          System.out.println("Option invalid");
      }
    }
  }
  private BookData searchBook() {
    System.out.println("Type in the name of the book you wish to search for.");
    String bookName = scanner.nextLine();
    String json = apiConnector.fetchData(BASE_URL + "?search=" + bookName.replace(" ", "+"));
    Data dataSearch = dataConverter.getData(json, Data.class);
    return dataSearch.results().stream()
      .filter(l -> l.title().toUpperCase().contains(bookName.toUpperCase()))
      .findFirst()
      .orElse(null);
  }

  @Transactional
  public void saveBook() {
    BookData data = searchBook();

    if (data == null) {
      System.out.println("Book not found");
      return;
    }

    Optional<Book> existingBook = repository.findBookByTitle(data.title());

    if (existingBook.isPresent()) {
      System.out.println("Book already exists in the database: " + existingBook.get().getTitle());
    } else {
      Book book = new Book(data);
      List<Authors> authors = authorRepository.findAuthorsByName(data.authors().get(0).name());

      if (!authors.isEmpty()) {
        authors.forEach(author -> {
          Authors managedAuthor = entityManager.merge(author);
          System.out.println(managedAuthor);
          book.getAuthors().add(managedAuthor);
        });
      } else {
        data.authors().forEach(authorData -> {
          Authors author = new Authors(authorData.name(), authorData.birthYear(), authorData.deathYear());
          book.getAuthors().add(author);
        });
      }

      repository.save(book);
      System.out.println("========================================");
      System.out.println("Book entered in the database");
      System.out.printf("Title: %s\n", data.title());

      System.out.print("Authors: ");
      data.authors().forEach(author ->
        System.out.printf("%s (%s - %s); ", author.name(), author.birthYear(), author.deathYear()));
      System.out.println();

      System.out.print("Languages: ");
      data.languages().forEach(language -> System.out.printf("%s ", language));
      System.out.println();

      System.out.printf("Download: %s\n", data.downloadCount());
      System.out.println("========================================");
    }
  }

  private void searchAuthorsByName() {
    System.out.println("Enter name of author");
    var nameAuthor = scanner.nextLine().trim();

    List<Authors> authors = authorRepository.findAuthorsByName(nameAuthor);
    if (!authors.isEmpty()) {
      Authors author = authors.get(0);
      System.out.println("========================================");
      System.out.println("Name author: " + author.getName());
      System.out.println("Year of birth: " + author.getBirthYear());
      System.out.println("Year of death: " + author.getDeathYear());
      System.out.println("========================================");
    } else {
      System.out.println("Authors not found");
    }
  }
  private void searchAllBook() {
    book = repository.findAllBook();
    book.forEach(b -> {
      System.out.printf("Title: %s\n", b.getTitle());

      System.out.print("Authors: ");
      b.getAuthors().forEach(author ->
        System.out.printf("%s (%s - %s); ", author.getName(), author.getBirthYear(), author.getDeathYear()));
      System.out.println();

      System.out.print("Languages: ");
      b.getLanguages().forEach(language -> System.out.printf("%s ", language));
      System.out.println();

      System.out.printf("DownloadCount: %s\n", b.getDownloadCount());

      System.out.println("========================================");
    });
  }
  public void listAuthors() {
    authors = repository.findAllAuthors();
    for (Authors author : authors) {
      System.out.println("========================================");
      System.out.println("Name author: " + author.getName());
      System.out.println("Year of birth: " + author.getBirthYear());
      System.out.println("Year of death: " + author.getDeathYear());
      List<Book> books = author.getBooks();
      if (books != null && !books.isEmpty()) {
        for (Book book : books) {
          System.out.printf("Book Title: %s\n", book.getTitle());
        }
      } else {
        System.out.println("  - No books found.");
      }
    }
  }

  private void listLivingAuthors() {
    System.out.println("Enter the live year of authors you wish to search for");
    var livingYear = scanner.nextInt();
    scanner.nextLine();

    authors = repository.findAuthorsAliveInYear(livingYear);
    if (!authors.isEmpty()) {
      authors.forEach(a -> {
        System.out.println("========================================");
        System.out.println("Name author: " + a.getName());
        System.out.println("Year of birth: " + a.getBirthYear());
        System.out.println("Year of death: " + a.getDeathYear());

        List<Book> books = a.getBooks();
        if (books != null && !books.isEmpty()) {
          books.forEach(b -> System.out.printf("4Book Title: %s\n", b.getTitle()));
        } else {
          System.out.println("  - No books found.");
        }
      });
    } else {
      System.out.println("No authors were found alive in the year of admission.");
    }
  }

  public void listBooksByLanguage() {
    System.out.println("Enter a language by which you want to search for books");
    System.out.println("en - English\nes - Spanish\nfr - French\npt - Portuguese\nde - German");
    var languageCode = scanner.nextLine().trim();

    try {
      Language language = Language.fromCode(languageCode);
      List<Book> books = repository.findBookByLanguage(language);
      if (books != null && !books.isEmpty()) {
        books.forEach(b -> {
          System.out.printf("Title: %s\n", b.getTitle());

          System.out.print("Authors: ");
          b.getAuthors().forEach(author ->
            System.out.printf("%s (%s - %s); ", author.getName(), author.getBirthYear(), author.getDeathYear()));
          System.out.println();

          System.out.print("Languages: ");
          b.getLanguages().forEach(l -> System.out.printf("%s ", l));
          System.out.println();

          System.out.printf("DownloadCount: %s\n", b.getDownloadCount());

          System.out.println("========================================");
        });
      } else {
        System.out.println("No books found.");
      }
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    }
  }

  private void top10MostDownloadedBooks() {
    Pageable pageable = PageRequest.of(0, 10);
    List<Book> books = repository.findBooksOrderedByDownloadCount(pageable);
    final AtomicInteger counter = new AtomicInteger(1);

    books.forEach(b -> {
      int index = counter.getAndIncrement();
      System.out.printf("%d. Title: %s\n", index, b.getTitle());
    });
  }

}