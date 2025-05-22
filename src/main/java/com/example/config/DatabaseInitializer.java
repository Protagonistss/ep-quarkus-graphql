package com.example.config;

import com.example.entity.Book;
import com.example.repository.BookRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import io.quarkus.runtime.StartupEvent;
import java.util.List;

@ApplicationScoped
public class DatabaseInitializer {

    @Inject
    BookRepository bookRepository;

    @Transactional
    public void onStart(@Observes StartupEvent ev) {
        // 检查是否已经有数据
        List<Book> books = bookRepository.findAll().list();
        if (books.isEmpty()) {
            // 创建初始数据
            Book book1 = new Book();
            book1.setTitle("Spring Boot in Action");
            book1.setAuthor("Craig Walls");
            book1.setPrice(49.99);
            bookRepository.persist(book1);

            Book book2 = new Book();
            book2.setTitle("Clean Code");
            book2.setAuthor("Robert C. Martin");
            book2.setPrice(39.99);
            bookRepository.persist(book2);

            Book book3 = new Book();
            book3.setTitle("Effective Java");
            book3.setAuthor("Joshua Bloch");
            book3.setPrice(45.99);
            bookRepository.persist(book3);
        }
    }
} 