/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.autocar.controller;

import com.mycompany.autocar.dao.ModeloDAO;
import com.mycompany.autocar.model.Modelo;

import java.util.List;
/**
 *
 * @author segundo
 */
public class ModeloController 
{
    private final ModeloDAO modeloDAO;
    
    public ModeloController(ModeloDAO modeloDAO) {
        this.modeloDAO = modeloDAO;
    }
    
    public void guardar(Modelo modelo) throws Exception {
        if (modelo.getId() == 0) {
            modeloDAO.crear(modelo);
        } else {
            modeloDAO.actualizar(modelo);
        }
    }
    
    public void eliminar(int id) throws Exception {
        modeloDAO.eliminar(id);
    }

    public List<Modelo> listar() throws Exception {
        return modeloDAO.listarTodos();
    }
    
    public List<Modelo> listarPorMarca(int idMarca) throws Exception {
        return modeloDAO.listarPorMarca(idMarca);
    }
    
    public Modelo buscar(int id) throws Exception {
        return modeloDAO.buscarPorId(id);
    }
}
