/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.autocar.dao;

import com.mycompany.autocar.model.Vehiculo;
import java.util.List;
/**
 *
 * @author segundo
 */
public interface VehiculoDAO 
{
    void crear(Vehiculo v) throws Exception;
    void actualizar(Vehiculo v) throws Exception;
    void eliminar(int id) throws Exception;
    Vehiculo buscarPorId(int id) throws Exception;
    List<Vehiculo> listarTodos() throws Exception;
    List<Vehiculo> listarPorModelo(int modeloId) throws Exception;
    List<Vehiculo> listarPorMarca(int marcaId) throws Exception;
}
