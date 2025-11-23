/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.autocar.dao.mysql;

import com.mycompany.autocar.dao.ModeloDAO;
import com.mycompany.autocar.db.Conexion;
import com.mycompany.autocar.model.Marca;
import com.mycompany.autocar.model.Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author segundo
 */
public class MySQLModeloDAO implements ModeloDAO
{
    @Override
    public void crear(Modelo modelo) throws Exception {
        String sql = "INSERT INTO modelos (nombre, marca_id) VALUES (?, ?)";

        try (Connection cn = Conexion.getConexion();
            PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, modelo.getNombre());
            ps.setInt(2, modelo.getMarca().getId());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    modelo.setId(rs.getInt(1));
                }
            }
        }
    }
    
    @Override
    public void actualizar(Modelo modelo) throws Exception {
        String sql = "UPDATE modelos SET nombre = ?, marca_id = ? WHERE id = ?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, modelo.getNombre());
            ps.setInt(2, modelo.getMarca().getId());
            ps.setInt(3, modelo.getId());
            ps.executeUpdate();
        }
    }
    
    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM modelos WHERE id = ?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    @Override
    public Modelo buscarPorId(int id) throws Exception {
        String sql = """
            SELECT mo.id, mo.nombre,
                   ma.id AS marca_id, ma.nombre AS marca_nombre
            FROM modelos mo
            JOIN marcas ma ON mo.marca_id = ma.id
            WHERE mo.id = ?
        """;

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Marca marca = new Marca(
                            rs.getInt("marca_id"),
                            rs.getString("marca_nombre")
                    );
                    return new Modelo(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            marca
                    );
                }
            }
        }
        return null;
    }
    
    @Override
    public List<Modelo> listarTodos() throws Exception {
        List<Modelo> lista = new ArrayList<>();

        String sql = """
            SELECT mo.id, mo.nombre,
                   ma.id AS marca_id, ma.nombre AS marca_nombre
            FROM modelos mo
            JOIN marcas ma ON mo.marca_id = ma.id
            ORDER BY ma.nombre, mo.nombre
        """;

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Marca marca = new Marca(
                        rs.getInt("marca_id"),
                        rs.getString("marca_nombre")
                );

                Modelo modelo = new Modelo(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        marca
                );

                lista.add(modelo);
            }
        }
        return lista;
    }
    
    @Override
    public List<Modelo> listarPorMarca(int idMarca) throws Exception {
        List<Modelo> lista = new ArrayList<>();

        String sql = """
            SELECT mo.id, mo.nombre,
                   ma.id AS marca_id, ma.nombre AS marca_nombre
            FROM modelos mo
            JOIN marcas ma ON mo.marca_id = ma.id
            WHERE ma.id = ?
            ORDER BY mo.nombre
        """;

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idMarca);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Marca marca = new Marca(
                            rs.getInt("marca_id"),
                            rs.getString("marca_nombre")
                    );

                    Modelo modelo = new Modelo(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            marca
                    );

                    lista.add(modelo);
                }
            }
        }
        return lista;
    }
    
    @Override
    public int contarPorMarca(int idMarca) throws Exception {
        String sql = "SELECT COUNT(*) FROM modelos WHERE marca_id = ?";
        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, idMarca);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
}
