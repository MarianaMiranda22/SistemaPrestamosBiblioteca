package com.example.sistemaprestamos.model;

public class InventoryItem {

    public enum Type { LIBRO, HERRAMIENTA }
    public enum Status { DISPONIBLE, PRESTADO }

    private final String name;
    private final Type type;
    private final Status status;
    private final int loansCount;

    public InventoryItem(String name, Type type, Status status, int loansCount) {
        this.name = name;
        this.type = type;
        this.status = status;
        this.loansCount = loansCount;
    }

    public String getName() { return name; }
    public Type getType() { return type; }
    public Status getStatus() { return status; }
    public int getLoansCount() { return loansCount; }
}
