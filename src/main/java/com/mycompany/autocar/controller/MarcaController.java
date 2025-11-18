/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.autocar.controller;

import com.mycompany.autocar.dao.MarcaDAO;
import com.mycompany.autocar.model.Marca;
import java.util.List;

/**
 *
 * @author segundo
 */
public class MarcaController 
{
    private final MarcaDAO marcaDAO;

    public MarcaController(MarcaDAO marcaDAO) {
        this.marcaDAO = marcaDAO;
    }

    public void guardar(Marca marca) throws Exception {
        if (marca.getId() == 0) {
            marcaDAO.crear(marca);
        } else {
            marcaDAO.actualizar(marca);
        }
    }

    public void eliminar(int id) throws Exception {
        marcaDAO.eliminar(id);
    }

    public List<Marca> listar() throws Exception {
        return marcaDAO.listarTodas();
    }

    public Marca buscar(int id) throws Exception {
        return marcaDAO.buscarPorId(id);
    }
}
