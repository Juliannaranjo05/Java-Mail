package com.example.demo.model;

import jakarta.persistence.*;

@Entity
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private int frecuenciaHoras; // Cada cuántas horas se toma
    private int cantidad; // Cantidad disponible

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFrecuenciaHoras() {
        return frecuenciaHoras;
    }

    public void setFrecuenciaHoras(int frecuenciaHoras) {
        this.frecuenciaHoras = frecuenciaHoras;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
