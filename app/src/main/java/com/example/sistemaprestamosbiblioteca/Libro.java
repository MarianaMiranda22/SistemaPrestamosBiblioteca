package com.example.sistemaprestamosbiblioteca;

/**
 * Clase modelo que representa un libro dentro del sistema
 */
public class Libro {

    // ===============================
    // ATRIBUTOS
    // ===============================

    private String id;          // ID único del libro (Firebase)
    private String nombre;      // Nombre del libro
    private String autor;       // Autor del libro
    private boolean disponible; // Estado: true = disponible, false = prestado

    // ===============================
    // CONSTRUCTOR VACÍO (OBLIGATORIO)
    // ===============================

    public Libro() {
        // Firebase necesita este constructor vacío
    }

    // ===============================
    // CONSTRUCTORES CON DATOS
    // ===============================

    public Libro(String id, String nombre, String autor) {
        this.id = id;
        this.nombre = nombre;
        this.autor = autor;
        this.disponible = true; // Por defecto, un libro nuevo está disponible
    }

    public Libro(String id, String nombre, String autor, boolean disponible) {
        this.id = id;
        this.nombre = nombre;
        this.autor = autor;
        this.disponible = disponible;
    }

    // ===============================
    // GETTERS (OBTENER DATOS)
    // ===============================

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getAutor() { return autor; }
    public boolean isDisponible() { return disponible; }

    // ===============================
    // SETTERS (MODIFICAR DATOS)
    // ===============================

    public void setId(String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setAutor(String autor) { this.autor = autor; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}