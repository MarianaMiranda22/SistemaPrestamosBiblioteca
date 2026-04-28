package com.example.sistemaprestamosbiblioteca;

/**
 * Modelo para registrar préstamos de libros o herramientas.
 */
public class Prestamo {

    private String id;
    private String usuario;
    private String itemId;
    private String itemNombre;
    private String itemTipo;
    private String fechaPrestamo;
    private String fechaDevolucion;
    private String estado;

    public Prestamo() {
        // Constructor vacío requerido por Firebase
    }

    public Prestamo(String id, String usuario, String itemId, String itemNombre,
                    String itemTipo, String fechaPrestamo, String fechaDevolucion, String estado) {
        this.id = id;
        this.usuario = usuario;
        this.itemId = itemId;
        this.itemNombre = itemNombre;
        this.itemTipo = itemTipo;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getItemId() {
        return itemId;
    }

    public String getItemNombre() {
        return itemNombre;
    }

    public String getItemTipo() {
        return itemTipo;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public String getEstado() {
        return estado;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setItemNombre(String itemNombre) {
        this.itemNombre = itemNombre;
    }

    public void setItemTipo(String itemTipo) {
        this.itemTipo = itemTipo;
    }

    public void setFechaPrestamo(String fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Usuario: " + usuario + "\n" +
                "Artículo: " + itemTipo + " - " + itemNombre + "\n" +
                "Fecha préstamo: " + fechaPrestamo + "\n" +
                "Fecha devolución: " + fechaDevolucion + "\n" +
                "Estado: " + estado;
    }
}
