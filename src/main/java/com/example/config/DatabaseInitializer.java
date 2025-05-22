package com.example.config;

import com.example.entity.Author;
import com.example.entity.Book;
import com.example.repository.AuthorRepository;
import com.example.repository.BookRepository;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class DatabaseInitializer {
    @Inject
    AuthorRepository authorRepository;
    
    @Inject
    BookRepository bookRepository;

    @Transactional
    public void onStart(@Observes StartupEvent ev) {
        List<Author> authors = authorRepository.findAll().list();
        if (authors.isEmpty()) {
            // Create authors
            Author author1 = new Author();
            author1.setName("J.K. Rowling");
            author1.setBiography("British author best known for the Harry Potter series");
            authorRepository.persist(author1);

            Author author2 = new Author();
            author2.setName("George R.R. Martin");
            author2.setBiography("American novelist and short story writer");
            authorRepository.persist(author2);

            // Create books with authors
            Book book1 = new Book();
            book1.setTitle("Harry Potter and the Philosopher's Stone");
            book1.setAuthor(author1);
            book1.setPrice(new BigDecimal("19.99"));
            bookRepository.persist(book1);

            Book book2 = new Book();
            book2.setTitle("A Game of Thrones");
            book2.setAuthor(author2);
            book2.setPrice(new BigDecimal("24.99"));
            bookRepository.persist(book2);

            Book book3 = new Book();
            book3.setTitle("Harry Potter and the Chamber of Secrets");
            book3.setAuthor(author1);
            book3.setPrice(new BigDecimal("21.99"));
            bookRepository.persist(book3);
        }
    }
} 