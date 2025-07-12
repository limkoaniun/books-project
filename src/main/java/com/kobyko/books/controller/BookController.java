package com.kobyko.books.controller;

import com.kobyko.books.entity.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {

    private final List<Book> books = new ArrayList<>();

    public BookController() {
        initialiseBooks();
    }

    private void initialiseBooks() {
        books.addAll(List.of(new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy"), new Book("To Kill a Mockingbird", "Harper Lee", "Fiction"), new Book("1984", "George Orwell", "Dystopian"), new Book("The Great Gatsby", "F. Scott Fitzgerald", "Classic"), new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "Fantasy")));
    }

    @GetMapping("/api/v1/books")
    public List<Book> getBooks() {
        return books;
    }

    @GetMapping("/api/v1/book/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }
}
