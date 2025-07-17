package com.qa.jsonplaceholder.constants;

/**
 * Enthält Konstanten für die API-Endpunkte der JSONPlaceholder-Dienste.
 * Dies zentralisiert die Pfade zu Ressourcen wie Kommentaren, Todos,
 * und anderen verfügbaren Endpunkten wie Posts ("/posts"), Users ("/users"), Alben ("/albums"), Fotos ("/photos").
 */
public class ApiEndpoints {

    // Comments Endpunkte
    public static final String COMMENTS = "/comments";
    public static final String SINGLE_COMMENT = "/comments/{commentId}";

    // Todos Endpunkt
    public static final String SINGLE_TODO = "/todos/{todoId}";
}