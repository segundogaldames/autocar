/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.autocar.view.marca;

import com.mycompany.autocar.controller.MarcaController;
import com.mycompany.autocar.model.Marca;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
/**
 *
 * @author segundo
 */
public class PanelMarca extends JPanel
{
    private MarcaController controller;

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private JTextField txtId;
    private JTextField txtNombre;

    private JButton btnNuevo;
    private JButton btnGuardar;
    private JButton btnEliminar;
    private JButton btnActualizar;
    
    public PanelMarca(MarcaController controller) {
        this.controller = controller;
        initComponents();
        cargarTabla();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());

        // =========================
        // Formulario superior
        // =========================
        JPanel formulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblId = new JLabel("ID:");
        JLabel lblNombre = new JLabel("Nombre:");

        txtId = new JTextField(5);
        txtId.setEditable(false);

        txtNombre = new JTextField(20);

        gbc.gridx = 0; gbc.gridy = 0;
        formulario.add(lblId, gbc);
        gbc.gridx = 1;
        formulario.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formulario.add(lblNombre, gbc);
        gbc.gridx = 1;
        formulario.add(txtNombre, gbc);

        // =========================
        // Botones
        // =========================
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));

        btnNuevo = new JButton("Nuevo");
        btnGuardar = new JButton("Guardar");
        btnEliminar = new JButton("Eliminar");
        btnActualizar = new JButton("Actualizar lista");

        botones.add(btnNuevo);
        botones.add(btnGuardar);
        botones.add(btnEliminar);
        botones.add(btnActualizar);

        JPanel top = new JPanel(new BorderLayout());
        top.add(formulario, BorderLayout.CENTER);
        top.add(botones, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);

        // =========================
        // Tabla
        // =========================
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // EVENTOS ==================

        btnNuevo.addActionListener(e -> limpiarFormulario());
        btnGuardar.addActionListener(e -> guardarMarca());
        btnEliminar.addActionListener(e -> eliminarMarca());
        btnActualizar.addActionListener(e -> cargarTabla());

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarSeleccion();
            }
        });
    }
    
    private void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        tabla.clearSelection();
    }
    
    private void guardarMarca() {
        try {
            String nombre = txtNombre.getText().trim();
            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío");
                return;
            }

            Marca m = new Marca();
            if (!txtId.getText().isEmpty()) {
                m.setId(Integer.parseInt(txtId.getText()));
            }
            m.setNombre(nombre);

            controller.guardar(m);
            JOptionPane.showMessageDialog(this, "Guardado correctamente");

            cargarTabla();
            limpiarFormulario();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar: " + ex.getMessage());
        }
    }
    
    private void eliminarMarca() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una marca");
            return;
        }

        int resp = JOptionPane.showConfirmDialog(this,
                "¿Eliminar la marca?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (resp == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText());
                controller.eliminar(id);
                cargarTabla();
                limpiarFormulario();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al eliminar: " + ex.getMessage());
            }
        }
    }
    
    private void cargarTabla() {
        try {
            modeloTabla.setRowCount(0);
            List<Marca> marcas = controller.listar();
            for (Marca m : marcas) {
                modeloTabla.addRow(new Object[]{m.getId(), m.getNombre()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar tabla: " + ex.getMessage());
        }
    }
    
    private void cargarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
        }
    }
}
