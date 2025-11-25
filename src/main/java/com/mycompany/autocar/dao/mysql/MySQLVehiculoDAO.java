/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.autocar.dao.mysql;

import com.mycompany.autocar.dao.VehiculoDAO;
import com.mycompany.autocar.db.Conexion;
import com.mycompany.autocar.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author segundo
 */
public class MySQLVehiculoDAO implements VehiculoDAO
{
    @Override
    public void crear(Vehiculo v) throws Exception {
        String sql = """
            INSERT INTO vehiculos
            (num_chasis, color, transmision, precio, stock, modelo_id)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, v.getNumChasis());
            ps.setString(2, v.getColor());
            ps.setString(3, v.getTransmision().name());
            ps.setInt(4, v.getPrecio());
            ps.setBoolean(5, v.isStock());
            ps.setInt(6, v.getModelo().getId());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    v.setId(rs.getInt(1));
                }
            }
        }
    }
    
    @Override
    public void actualizar(Vehiculo v) throws Exception {
        String sql = """
            UPDATE vehiculos
            SET num_chasis = ?, color = ?, transmision = ?,
                precio = ?, stock = ?, modelo_id = ?
            WHERE id = ?
        """;

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, v.getNumChasis());
            ps.setString(2, v.getColor());
            ps.setString(3, v.getTransmision().name());
            ps.setInt(4, v.getPrecio());
            ps.setBoolean(5, v.isStock());
            ps.setInt(6, v.getModelo().getId());
            ps.setInt(7, v.getId());

            ps.executeUpdate();
        }
    }
    
    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM vehiculos WHERE id = ?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    @Override
    public Vehiculo buscarPorId(int id) throws Exception {
        String sql = """
            SELECT v.id, v.num_chasis, v.color, v.transmision,
                   v.precio, v.stock,
                   mo.id AS modelo_id, mo.nombre AS modelo_nombre,
                   ma.id AS marca_id, ma.nombre AS marca_nombre
            FROM vehiculos v
            JOIN modelos mo ON v.modelo_id = mo.id
            JOIN marcas ma ON mo.marca_id = ma.id
            WHERE v.id = ?
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

                    Modelo modelo = new Modelo(
                            rs.getInt("modelo_id"),
                            rs.getString("modelo_nombre"),
                            marca
                    );

                    Vehiculo v = new Vehiculo();
                    v.setId(rs.getInt("id"));
                    v.setNumChasis(rs.getString("num_chasis"));
                    v.setColor(rs.getString("color"));
                    v.setTransmision(Transmision.valueOf(rs.getString("transmision")));
                    v.setPrecio(rs.getInt("precio"));
                    v.setStock(rs.getBoolean("stock"));
                    v.setModelo(modelo);

                    return v;
                }
            }
        }
        return null;
    }
    
    @Override
    public List<Vehiculo> listarTodos() throws Exception {
        List<Vehiculo> lista = new ArrayList<>();

        String sql = """
            SELECT v.id, v.num_chasis, v.color, v.transmision,
                   v.precio, v.stock,
                   mo.id AS modelo_id, mo.nombre AS modelo_nombre,
                   ma.id AS marca_id, ma.nombre AS marca_nombre
            FROM vehiculos v
            JOIN modelos mo ON v.modelo_id = mo.id
            JOIN marcas ma ON mo.marca_id = ma.id
            ORDER BY ma.nombre, mo.nombre, v.num_chasis
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
                        rs.getInt("modelo_id"),
                        rs.getString("modelo_nombre"),
                        marca
                );

                Vehiculo v = new Vehiculo();
                v.setId(rs.getInt("id"));
                v.setNumChasis(rs.getString("num_chasis"));
                v.setColor(rs.getString("color"));
                v.setTransmision(Transmision.valueOf(rs.getString("transmision")));
                v.setPrecio(rs.getInt("precio"));
                v.setStock(rs.getBoolean("stock"));
                v.setModelo(modelo);

                lista.add(v);
            }
        }
        return lista;
    }
    
    @Override
    public List<Vehiculo> listarPorModelo(int modeloId) throws Exception {
        List<Vehiculo> lista = new ArrayList<>();

        String sql = """
            SELECT v.id, v.num_chasis, v.color, v.transmision,
                   v.precio, v.stock,
                   mo.id AS modelo_id, mo.nombre AS modelo_nombre,
                   ma.id AS marca_id, ma.nombre AS marca_nombre
            FROM vehiculos v
            JOIN modelos mo ON v.modelo_id = mo.id
            JOIN marcas ma ON mo.marca_id = ma.id
            WHERE mo.id = ?
            ORDER BY v.num_chasis
        """;

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, modeloId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Marca marca = new Marca(
                            rs.getInt("marca_id"),
                            rs.getString("marca_nombre")
                    );

                    Modelo modelo = new Modelo(
                            rs.getInt("modelo_id"),
                            rs.getString("modelo_nombre"),
                            marca
                    );

                    Vehiculo v = new Vehiculo();
                    v.setId(rs.getInt("id"));
                    v.setNumChasis(rs.getString("num_chasis"));
                    v.setColor(rs.getString("color"));
                    v.setTransmision(Transmision.valueOf(rs.getString("transmision")));
                    v.setPrecio(rs.getInt("precio"));
                    v.setStock(rs.getBoolean("stock"));
                    v.setModelo(modelo);

                    lista.add(v);
                }
            }
        }
        return lista;
    }
    
    @Override
    public List<Vehiculo> listarPorMarca(int marcaId) throws Exception {
        List<Vehiculo> lista = new ArrayList<>();

        String sql = """
            SELECT v.id, v.num_chasis, v.color, v.transmision,
                   v.precio, v.stock,
                   mo.id AS modelo_id, mo.nombre AS modelo_nombre,
                   ma.id AS marca_id, ma.nombre AS marca_nombre
            FROM vehiculos v
            JOIN modelos mo ON v.modelo_id = mo.id
            JOIN marcas ma ON mo.marca_id = ma.id
            WHERE ma.id = ?
            ORDER BY mo.nombre, v.num_chasis
        """;

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, marcaId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Marca marca = new Marca(
                            rs.getInt("marca_id"),
                            rs.getString("marca_nombre")
                    );

                    Modelo modelo = new Modelo(
                            rs.getInt("modelo_id"),
                            rs.getString("modelo_nombre"),
                            marca
                    );

                    Vehiculo v = new Vehiculo();
                    v.setId(rs.getInt("id"));
                    v.setNumChasis(rs.getString("num_chasis"));
                    v.setColor(rs.getString("color"));
                    v.setTransmision(Transmision.valueOf(rs.getString("transmision")));
                    v.setPrecio(rs.getInt("precio"));
                    v.setStock(rs.getBoolean("stock"));
                    v.setModelo(modelo);

                    lista.add(v);
                }
            }
        }
        return lista;
    }
}
