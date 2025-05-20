package com.example.service;

import com.example.entity.Book;
import com.example.repository.BookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class BookService {
    
    @Inject
    BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.listAll();
    }

    public Book getBook(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional
    public Book createBook(Book book) {
        bookRepository.persist(book);
        return book;
    }

    @Transactional
    public Book updateBook(Long id, Book book) {
        Book existingBook = bookRepository.findById(id);
        if (existingBook != null) {
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setPrice(book.getPrice());
            bookRepository.persist(existingBook);
        }
        return existingBook;
    }

    @Transactional
    public boolean deleteBook(Long id) {
        return bookRepository.deleteById(id);
    }
} 