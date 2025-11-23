/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.autocar.controller;

import com.mycompany.autocar.dao.MarcaDAO;
import com.mycompany.autocar.dao.ModeloDAO;
import com.mycompany.autocar.model.Marca;
import java.util.List;

/**
 *
 * @author segundo
 */
public class MarcaController 
{
    private final MarcaDAO marcaDAO;
    private final ModeloDAO modeloDAO;

    public MarcaController(MarcaDAO marcaDAO, ModeloDAO modeloDAO) {
        this.marcaDAO = marcaDAO;
        this.modeloDAO = modeloDAO;
    }

    public void guardar(Marca marca) throws Exception {
        if (marca.getId() == 0) {
            marcaDAO.crear(marca);
        } else {
            marcaDAO.actualizar(marca);
        }
    }

    public void eliminar(int id) throws Exception {
        int cantidadModelos = modeloDAO.contarPorMarca(id);
        
        if (cantidadModelos > 0) {
            throw new Exception("No se puede eliminar la marca: tiene modelos asociados.");
        }
        
        marcaDAO.eliminar(id);
    }

    public List<Marca> listar() throws Exception {
        return marcaDAO.listarTodas();
    }

    public Marca buscar(int id) throws Exception {
        return marcaDAO.buscarPorId(id);
    }
}
