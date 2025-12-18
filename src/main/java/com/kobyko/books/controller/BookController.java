package com.kobyko.books.controller;

import com.kobyko.books.entity.Book;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for book operations.
 * Provides API endpoints for retrieving and searching books.
 */
@RestController
public class BookController {

    private final List<Book> books = new ArrayList<>();

    public BookController() {
        initialiseBooks();
    }

    /**
     * Loads sample books into the collection.
     */
    private void initialiseBooks() {
        books.addAll(
                List.of(
                        new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy"),
                        new Book("To Kill a Mockingbird", "Harper Lee", "Fiction"),
                        new Book("1984", "George Orwell", "Dystopian"),
                        new Book("The Great Gatsby", "F. Scott Fitzgerald", "Classic"),
                        new Book("Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "Fantasy")
                )
        );
    }

    /**
     * Gets all books in the collection.
     *
     * @return list of all books
     */
    @GetMapping("/api/v1/books")
    public List<Book> getAllBooks() {
        return books;
    }

    /**
     * Gets books with optional category filtering.
     *
     * @param category the category to filter by (optional)
     * @return all books if no category specified, filtered books otherwise
     */
    @GetMapping("/api/v2/books")
    public List<Book> getBooksByCategory(@RequestParam(required = false) String category) {
        if(category == null || category.isEmpty()) {
            return books;
        }

        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    /**
     * Finds a book by title (case-insensitive).
     *
     * @param title the book title to search for
     * @return the book if found, null otherwise
     */
    @GetMapping("/api/v1/book/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    @PostMapping("/api/v1/books")
    public void createBook(@RequestBody Book newBook) {
        boolean isNewBook = books.stream()
                .noneMatch(book -> book.getTitle().equalsIgnoreCase(newBook.getTitle()));
        if (isNewBook) {
            books.add(newBook);
        }
    }

    @PutMapping("/api/books/{title}")
    public void updateBook(@PathVariable String title, @RequestBody Book updateBook) {
        for (int i = 0; i < books.size(); i++) {
           if (books.get(i).getTitle().equalsIgnoreCase(title)){
               books.set(i, updateBook);
               return;
           }
        }
    }
}