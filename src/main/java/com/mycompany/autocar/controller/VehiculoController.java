/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.autocar.controller;

import com.mycompany.autocar.model.Vehiculo;
import com.mycompany.autocar.dao.VehiculoDAO;

import java.util.List;
/**
 *
 * @author segundo
 */
public class VehiculoController 
{
    private final VehiculoDAO vehiculoDAO;
    
    public VehiculoController(VehiculoDAO vehiculoDAO) 
    {
        this.vehiculoDAO = vehiculoDAO;
    }
    
    public void guardar(Vehiculo v) throws Exception {
        if (v.getId() == 0) {
            vehiculoDAO.crear(v);
        } else {
            vehiculoDAO.actualizar(v);
        }
    }
    
    public void eliminar(int id) throws Exception {
        vehiculoDAO.eliminar(id);
    }
    
    public List<Vehiculo> listar() throws Exception {
        return vehiculoDAO.listarTodos();
    }
    
    public Vehiculo buscar(int id) throws Exception {
        return vehiculoDAO.buscarPorId(id);
    }
    
    public List<Vehiculo> listarPorModelo(int modeloId) throws Exception {
        return vehiculoDAO.listarPorModelo(modeloId);
    }

    public List<Vehiculo> listarPorMarca(int marcaId) throws Exception {
        return vehiculoDAO.listarPorMarca(marcaId);
    }
}
