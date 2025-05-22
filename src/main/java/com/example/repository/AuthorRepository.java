package com.example.repository;

import com.example.entity.Author;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthorRepository implements PanacheRepository<Author> {
    public Author findByName(String name) {
        return find("name", name).firstResult();
    }
} 