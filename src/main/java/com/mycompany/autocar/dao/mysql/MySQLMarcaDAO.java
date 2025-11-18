/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.autocar.dao.mysql;

import com.mycompany.autocar.dao.MarcaDAO;
import com.mycompany.autocar.db.Conexion;
import com.mycompany.autocar.model.Marca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author segundo
 */
public class MySQLMarcaDAO implements MarcaDAO
{
    @Override
    public void crear(Marca marca) throws Exception {
        String sql = "INSERT INTO marcas (nombre) VALUES (?)";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, marca.getNombre());
            ps.executeUpdate();

            // opcional: recuperar id generado
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    marca.setId(rs.getInt(1));
                }
            }
        }
    }
    
    @Override
    public void actualizar(Marca marca) throws Exception {
        String sql = "UPDATE marcas SET nombre = ? WHERE id = ?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, marca.getNombre());
            ps.setInt(2, marca.getId());

            ps.executeUpdate();
        }
    }
    
    @Override
    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM marcas WHERE id = ?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
    
    @Override
    public Marca buscarPorId(int id) throws Exception {
        String sql = "SELECT id, nombre FROM marcas WHERE id = ?";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Marca(
                            rs.getInt("id"),
                            rs.getString("nombre")
                    );
                }
            }
        }
        return null; // no encontrada
    }
    
    @Override
    public List<Marca> listarTodas() throws Exception {
        List<Marca> lista = new ArrayList<>();
        String sql = "SELECT id, nombre FROM marcas ORDER BY nombre";

        try (Connection cn = Conexion.getConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Marca m = new Marca(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );
                lista.add(m);
            }
        }
        return lista;
    }
}
