package com.crudjsp.crudjsp.model;
import lombok.Data;

@Data
public class Producto {
    private Long id;
    private String nombre;
    private Long codigo;
    private Long cantidad;
    private Double precio;
    private Categoria categoria;
    private String active;
}
