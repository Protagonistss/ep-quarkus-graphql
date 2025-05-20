package com.example.resource;

import com.example.entity.Book;
import com.example.service.BookService;
import jakarta.inject.Inject;
import org.eclipse.microprofile.graphql.*;

import java.util.List;

@GraphQLApi
public class BookResource {

    @Inject
    BookService bookService;

    @Query("allBooks")
    @Description("Get all books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @Query("book")
    @Description("Get a book by id")
    public Book getBook(@Name("id") Long id) {
        return bookService.getBook(id);
    }

    @Mutation("createBook")
    @Description("Create a new book")
    public Book createBook(Book book) {
        return bookService.createBook(book);
    }

    @Mutation("updateBook")
    @Description("Update an existing book")
    public Book updateBook(@Name("id") Long id, Book book) {
        return bookService.updateBook(id, book);
    }

    @Mutation("deleteBook")
    @Description("Delete a book")
    public boolean deleteBook(@Name("id") Long id) {
        return bookService.deleteBook(id);
    }
} 