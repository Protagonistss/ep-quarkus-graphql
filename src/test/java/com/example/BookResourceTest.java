package com.example;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class BookResourceTest {

    @Test
    public void testCreateBook() {
        String createBookMutation = """
            mutation {
                createBook(book: {
                    title: "Test Book",
                    author: "Test Author",
                    price: 29.99
                }) {
                    id
                    title
                    author
                    price
                }
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body("{\"query\": \"" + createBookMutation + "\"}")
            .when()
            .post("/graphql")
            .then()
            .statusCode(200)
            .body("data.createBook.title", is("Test Book"));
    }

    @Test
    public void testGetAllBooks() {
        String getAllBooksQuery = """
            query {
                allBooks {
                    id
                    title
                    author
                    price
                }
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body("{\"query\": \"" + getAllBooksQuery + "\"}")
            .when()
            .post("/graphql")
            .then()
            .statusCode(200);
    }
} 