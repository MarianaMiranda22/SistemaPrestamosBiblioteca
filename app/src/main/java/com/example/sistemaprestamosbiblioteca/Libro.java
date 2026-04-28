package com.example.sistemaprestamosbiblioteca;

/**
 * Modelo para representar un elemento del catálogo/inventario.
 * Puede ser un libro o una herramienta.
 */
public class Libro {

    private String id;
    private String tipo;          // Libro o Herramienta
    private String nombre;        // Nombre del libro/herramienta
    private String autor;         // Autor, marca o descripción
    private boolean disponible;   // true = disponible, false = prestado

    public Libro() {
        // Constructor vacío requerido por Firebase
    }

    public Libro(String id, String nombre, String autor) {
        this.id = id;
        this.tipo = "Libro";
        this.nombre = nombre;
        this.autor = autor;
        this.disponible = true;
    }

    public Libro(String id, String tipo, String nombre, String autor, boolean disponible) {
        this.id = id;
        this.tipo = tipo;
        this.nombre = nombre;
        this.autor = autor;
        this.disponible = disponible;
    }

    public String getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAutor() {
        return autor;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getTextoEstado() {
        return disponible ? "Disponible" : "Prestado";
    }

    @Override
    public String toString() {
        String tipoTexto = tipo == null || tipo.trim().isEmpty() ? "Elemento" : tipo;
        String nombreTexto = nombre == null ? "Sin nombre" : nombre;
        String autorTexto = autor == null ? "" : autor;
        return tipoTexto + " - " + nombreTexto + " | " + autorTexto + " | " + getTextoEstado();
    }
}
