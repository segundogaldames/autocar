/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.autocar.dao;
import com.mycompany.autocar.model.Marca;
import java.util.List;
/**
 *
 * @author segundo
 */
public interface MarcaDAO 
{
    void crear(Marca marca) throws Exception;

    void actualizar(Marca marca) throws Exception;

    void eliminar(int id) throws Exception;

    Marca buscarPorId(int id) throws Exception;

    List<Marca> listarTodas() throws Exception;
}
