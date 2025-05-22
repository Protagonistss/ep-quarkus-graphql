package com.example.resource;

import com.example.entity.Author;
import com.example.repository.AuthorRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.graphql.*;

import java.util.List;

@GraphQLApi
public class AuthorResource {
    @Inject
    AuthorRepository authorRepository;

    @Query("allAuthors")
    @Description("Get all authors")
    public List<Author> getAllAuthors() {
        return authorRepository.listAll();
    }

    @Query("author")
    @Description("Get an author by ID")
    public Author getAuthor(@Name("id") Long id) {
        return authorRepository.findById(id);
    }

    @Query("authorByName")
    @Description("Get an author by name")
    public Author getAuthorByName(@Name("name") String name) {
        return authorRepository.findByName(name);
    }

    @Mutation
    @Transactional
    public Author createAuthor(Author author) {
        authorRepository.persist(author);
        return author;
    }

    @Mutation
    @Transactional
    public Author updateAuthor(Long id, Author authorInput) {
        Author author = authorRepository.findById(id);
        if (author != null) {
            author.setName(authorInput.getName());
            author.setBiography(authorInput.getBiography());
            authorRepository.persist(author);
        }
        return author;
    }

    @Mutation
    @Transactional
    public boolean deleteAuthor(Long id) {
        return authorRepository.deleteById(id);
    }
} 