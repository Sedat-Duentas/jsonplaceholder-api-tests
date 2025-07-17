package com.qa.jsonplaceholder.base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseTest {

    // Diese Methode wird einmalig vor allen Tests in allen erbenden Klassen ausgeführt.
    @BeforeAll
    static void setup() {
        // Die Konfiguration der Basis-URI und des Loggings direkt hier
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}