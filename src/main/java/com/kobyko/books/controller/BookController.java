package com.kobyko.books.controller;

import com.kobyko.books.entity.Book;
import com.kobyko.books.request.BookRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * REST controller for book operations.
 * Provides API endpoints for retrieving and searching books.
 */
@RestController
@RequestMapping("/api/books")
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
                        new Book(1, "The Hobbit", "J.R.R. Tolkien", "Fantasy", 5),
                        new Book(2, "To Kill a Mockingbird", "Harper Lee", "Fiction", 2),
                        new Book(3, "1984", "George Orwell", "Dystopian", 3),
                        new Book(4, "The Great Gatsby", "F. Scott Fitzgerald", "Classic", 1),
                        new Book(5, "Harry Potter and the Sorcerer's Stone", "J.K. Rowling", "Fantasy", 4),
                        new Book(6, "Dune", "Frank Herbert", "Science Fiction", 3),
                        new Book(7, "The Catcher in the Rye", "J.D. Salinger", "Fiction", 1),
                        new Book(8, "Brave New World", "Aldous Huxley", "Dystopian", 4),
                        new Book(9, "Pride and Prejudice", "Jane Austen", "Romance", 5),
                        new Book(10, "The Lord of the Rings", "J.R.R. Tolkien", "Fantasy", 2)
                )
        );
    }

    /**
     * Gets books with optional category filtering.
     *
     * @param category the category to filter by (optional)
     * @return all books if no category specified, filtered books otherwise
     */
    @GetMapping
    public List<Book> getBooks(@RequestParam(required = false) String category) {
        if (category == null || category.isEmpty()) {
            return books;
        }

        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    /**
     * Finds a book by title (case-insensitive).
     *
     * @param id the book title to search for
     * @return the book if found, null otherwise
     */
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable long id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public void createBook(@RequestBody BookRequest bookRequest) {
        long id;
        if (books.isEmpty()) {
            id = 1;
        } else {
            id = books.get(books.size() - 1).getId() + 1;
        }

        Book book = new Book(
                id,
                bookRequest.getTitle(),
                bookRequest.getAuthor(),
                bookRequest.getCategory(),
                bookRequest.getRating()
        );

        books.add(book);
    }

    @PutMapping("/{id}")
    public void updateBook(@PathVariable long id, @RequestBody Book updateBook) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId() == id) {
                books.set(i, updateBook);
                return;
            }
        }
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable long id) {
        books.removeIf(book -> book.getId() == id);
    }
}