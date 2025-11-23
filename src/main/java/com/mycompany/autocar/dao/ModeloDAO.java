/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.autocar.dao;

import com.mycompany.autocar.model.Modelo;
import java.util.List;
/**
 *
 * @author segundo
 */
public interface ModeloDAO 
{
    void crear(Modelo modelo) throws Exception;
    void actualizar(Modelo modelo) throws Exception;
    void eliminar(int id) throws Exception;
    Modelo buscarPorId(int id) throws Exception;
    List<Modelo> listarTodos() throws Exception;
    List<Modelo> listarPorMarca(int idMarca) throws Exception;
    int contarPorMarca(int idMarca) throws Exception;
}
