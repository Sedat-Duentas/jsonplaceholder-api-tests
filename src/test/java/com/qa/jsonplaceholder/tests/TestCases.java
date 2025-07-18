package com.qa.jsonplaceholder.tests;

import com.qa.jsonplaceholder.constants.ApiEndpoints;
import com.qa.jsonplaceholder.base.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestCases extends BaseTest {

    // Die ID des Kommentars, den wir für DELETE und POST (simulierte Neuerstellung) verwenden
    private final int COMMENT_ID_FOR_OPERATIONS = 1;

    // Die ID des Todos, das wir für PUT und PATCH verwenden.
    private final int TODO_ID_FOR_OPERATIONS = 1;


    // --- 1. Testfall: GET Request - Alle Kommentare abrufen ---
    @Test
    void getComments() {
        System.out.println("\n--- Starting Test Case 1: GET All Comments ---");

        Response response = given()
                .when()
                .get(ApiEndpoints.COMMENTS)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", greaterThan(0))
                .body("[0].id", notNullValue())
                .extract().response();

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body:\n" + response.getBody().asString());
        System.out.println("--- Finished Test Case 1 ---");
    }

    // --- 2. Testfall: DELETE Request - Einen Kommentar löschen ---
    @Test
    void deleteComment() {
        System.out.println("\n--- Starting Test Case 2: DELETE Comment ---");
        System.out.println("Deleting Comment with ID: " + COMMENT_ID_FOR_OPERATIONS);

        Response response = given()
                .pathParam("commentId", COMMENT_ID_FOR_OPERATIONS)
                .when()
                .delete(ApiEndpoints.SINGLE_COMMENT)
                .then()
                .statusCode(200)
                .extract().response();

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body:\n" + response.getBody().asString());
        System.out.println("--- Finished Test Case 2 ---");
    }

    // --- 3. Testfall: POST Request - Einen neuen Kommentar erstellen ---
    @Test
    void createComment() {
        System.out.println("\n--- Starting Test Case 3: POST New Comment ---");
        String requestBody = String.format("{ \"postId\": %d, \"name\": \"Neuer Kommentar\", \"email\": \"new.test@example.com\", \"body\": \"Dies ist der Inhalt des neu erstellten Kommentars.\" }", COMMENT_ID_FOR_OPERATIONS);
        System.out.println("Request Body:\n" + requestBody);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(ApiEndpoints.COMMENTS)
                .then()
                .statusCode(201) // 201 Created für erfolgreiche Erstellung
                .contentType(ContentType.JSON)
                .body("id", notNullValue())
                .body("name", equalTo("Neuer Kommentar"))
                .body("email", equalTo("new.test@example.com"))
                .extract().response();

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body:\n" + response.getBody().asString());
        System.out.println("--- Finished Test Case 3 ---");
    }

    // --- 4. Testfall: PUT Request - Bestehendes Todos vollständig aktualisieren ---
    @Test
    void updateTodoPut() {
        System.out.println("\n--- Starting Test Case 4: PUT Update Todo ---");
        System.out.println("Updating Todo with ID: " + TODO_ID_FOR_OPERATIONS);
        String requestBody = String.format("{ \"id\": %d, \"userId\": 1, \"title\": \"Vollstaendig aktualisierter Todo-Titel\", \"completed\": true }", TODO_ID_FOR_OPERATIONS);
        System.out.println("Request Body:\n" + requestBody);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .pathParam("todoId", TODO_ID_FOR_OPERATIONS)
                .when()
                .put(ApiEndpoints.SINGLE_TODO)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(TODO_ID_FOR_OPERATIONS))
                .body("title", equalTo("Vollstaendig aktualisierter Todo-Titel"))
                .body("completed", equalTo(true))
                .extract().response();

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body:\n" + response.getBody().asString());
        System.out.println("--- Finished Test Case 4 ---");
    }

    // --- 5. Testfall: PATCH Request - Bestehendes Todos teilweise aktualisieren ---
    @Test
    void updateTodoPatch() {
        System.out.println("\n--- Starting Test Case 5: PATCH Update Todo ---");
        System.out.println("Partially Updating Todo with ID: " + TODO_ID_FOR_OPERATIONS);
        String requestBody = "{ \"title\": \"Neuer Titel per PATCH\" }";
        System.out.println("Request Body:\n" + requestBody);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .pathParam("todoId", TODO_ID_FOR_OPERATIONS)
                .when()
                .patch(ApiEndpoints.SINGLE_TODO)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(TODO_ID_FOR_OPERATIONS))
                .body("title", equalTo("Neuer Titel per PATCH")) // Prüft, ob 'title' aktualisiert wurde
                .body("completed", notNullValue()) // Prüft, ob 'completed' noch vorhanden ist (nicht überschrieben)
                .extract().response();

        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Body:\n" + response.getBody().asString());
        System.out.println("--- Finished Test Case 5 ---");
    }
}