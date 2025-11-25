/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.autocar.model;

/**
 *
 * @author segundo
 */
public enum Transmision 
{
    MANUAL,
    AUTOMATICA;

    @Override
    public String toString() {
        // Para mostrar bonito en el combo
        String name = name().toLowerCase();
        return name.substring(0,1).toUpperCase() + name.substring(1);
    }
}
