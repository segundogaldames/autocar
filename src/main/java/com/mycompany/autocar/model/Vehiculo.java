/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.autocar.model;

/**
 *
 * @author segundo
 */
public class Vehiculo 
{
    private int id;
    private String numChasis;
    private String color;
    private Transmision transmision;
    private int precio;
    private boolean stock;
    private Modelo modelo;

    public Vehiculo() {}

    public Vehiculo(int id, String numChasis, String color, Transmision transmision, 
            int precio, boolean stock, Modelo modelo) {
        this.id = id;
        this.numChasis = numChasis;
        this.color = color;
        this.transmision = transmision;
        this.precio = precio;
        this.stock = stock;
        this.modelo = modelo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumChasis() {
        return numChasis;
    }

    public void setNumChasis(String numChasis) {
        this.numChasis = numChasis;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Transmision getTransmision() {
        return transmision;
    }

    public void setTransmision(Transmision transmision) {
        this.transmision = transmision;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public boolean isStock() {
        return stock;
    }

    public void setStock(boolean stock) {
        this.stock = stock;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }
}
