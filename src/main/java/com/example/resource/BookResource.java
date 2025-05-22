package com.example.resource;

import com.example.entity.Book;
import com.example.entity.Author;
import com.example.repository.BookRepository;
import com.example.repository.AuthorRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.graphql.*;

import java.util.List;

@GraphQLApi
public class BookResource {
    @Inject
    BookRepository bookRepository;
    
    @Inject
    AuthorRepository authorRepository;

    @Query("allBooks")
    @Description("Get all books")
    public List<Book> getAllBooks() {
        return bookRepository.listAll();
    }

    @Query("book")
    @Description("Get a book by ID")
    public Book getBook(@Name("id") Long id) {
        return bookRepository.findById(id);
    }

    @Query("booksByAuthor")
    @Description("Get books by author ID")
    public List<Book> getBooksByAuthor(@Name("authorId") Long authorId) {
        Author author = authorRepository.findById(authorId);
        return author != null ? author.getBooks() : List.of();
    }

    @Mutation
    @Transactional
    public Book createBook(Book book) {
        if (book.getAuthor() != null && book.getAuthor().getId() != null) {
            Author author = authorRepository.findById(book.getAuthor().getId());
            if (author != null) {
                book.setAuthor(author);
            }
        }
        bookRepository.persist(book);
        return book;
    }

    @Mutation
    @Transactional
    public Book updateBook(Long id, Book bookInput) {
        Book book = bookRepository.findById(id);
        if (book != null) {
            book.setTitle(bookInput.getTitle());
            book.setPrice(bookInput.getPrice());
            if (bookInput.getAuthor() != null && bookInput.getAuthor().getId() != null) {
                Author author = authorRepository.findById(bookInput.getAuthor().getId());
                if (author != null) {
                    book.setAuthor(author);
                }
            }
            bookRepository.persist(book);
        }
        return book;
    }

    @Mutation
    @Transactional
    public boolean deleteBook(Long id) {
        return bookRepository.deleteById(id);
    }
} 