# Alura literature

## Overview

## Overview
Alura Literatura is a Java-based application designed to manage and query a database of books and authors. 
It interfaces with the Gutenberg Project via `https://gutendex.com/books` to fetch book data and supports 
various operations such as searching, listing, and tracking the most downloaded books.

## Features
- Search for books by title.
- List all registered books and authors.
- Display books based on the language.
- Find top downloaded books.
- Manage author information, including listing authors alive in a specified year.
- Interactive command line interface for easy operation.


## Technologies Utilized
- **Java**: Core programming language.
- **Spring Framework**: Provides the infrastructure support for developing Java apps.
- **Jakarta Persistence (JPA)**: For database integration and management.
- **Hibernate**: JPA implementation for data handling.
- **H2 Database**: In-memory database for development and testing.
- **Maven**: Dependency management and build tool.

## Key Features
- **Book and Author Management**: Allows for adding, searching, and listing books and authors in the system.
- **Data Fetching**: Connects to external APIs to fetch real-time data about books.
- **Dynamic Querying**: Users can query books by various attributes like language or download count.
- **Transaction Management**: Ensures data integrity and handles transactional operations.

## Prerequisites
Before you begin, ensure you have the following installed on your system:
- Java JDK 17 or later
- Maven 3.6 or later (for building the project)
- An IDE of your choice (e.g., IntelliJ IDEA, Eclipse)

## Starting 🚀

1. Clone the repository:

```shell
git clone https://github.com/jesusdavid24/challanger-literature.git
```

2. Navigate to the project directory:

```shell
cd challenger-literature
```

3. Create a new database and configure the application.properties with the correct database URL, username, and password.

4. Update Maven Configuration:
```shell
 <dependency>
       <groupId>com.fasterxml.jackson.core</groupId>
       <artifactId>jackson-databind</artifactId>
       <version>2.16.0</version>
</dependency>

<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

5. Start the application:

```shell
 click on run 
```

## Authors 👊

This project was created by [jesusdavid24](https://github.com/jesusdavid24)

## License

[MIT](LICENSE)
