package com.example.sistemaprestamosbiblioteca;

/**
 * Clase modelo que representa un Libro dentro del sistema.
 *
 * Esta clase se utiliza para:
 * - Guardar libros en Firebase Realtime Database.
 * - Recuperar libros desde Firebase.
 * - Mantener organizada la estructura de datos del inventario.
 */
public class Libro {

    // ===============================
    // ATRIBUTOS (Propiedades del libro)
    // ===============================

    /**
     * Identificador único del libro.
     * Este ID se genera automáticamente al guardarlo en Firebase.
     */
    private String id;

    /**
     * Nombre o título del libro.
     */
    private String nombre;

    /**
     * Autor del libro.
     */
    private String autor;


    // ===============================
    // CONSTRUCTOR VACÍO
    // ===============================

    /**
     * Constructor vacío obligatorio.
     *
     * Firebase necesita este constructor para poder
     * convertir los datos de la base de datos en objetos Libro.
     */
    public Libro() {
        // No se inicializa nada aquí
    }


    // ===============================
    // CONSTRUCTOR CON PARÁMETROS
    // ===============================

    /**
     * Constructor que permite crear un libro con todos sus datos.
     *
     * @param id Identificador único del libro.
     * @param nombre Nombre o título del libro.
     * @param autor Autor del libro.
     */
    public Libro(String id, String nombre, String autor) {
        this.id = id;
        this.nombre = nombre;
        this.autor = autor;
    }


    // ===============================
    // MÉTODOS GET (Obtener datos)
    // ===============================

    /**
     * Obtiene el ID del libro.
     * @return id del libro.
     */
    public String getId() {
        return id;
    }

    /**
     * Obtiene el nombre del libro.
     * @return nombre del libro.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el autor del libro.
     * @return autor del libro.
     */
    public String getAutor() {
        return autor;
    }


    // ===============================
    // MÉTODOS SET (Modificar datos)
    // ===============================

    /**
     * Establece el ID del libro.
     * @param id identificador único.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Establece el nombre del libro.
     * @param nombre título del libro.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece el autor del libro.
     * @param autor nombre del autor.
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }
}