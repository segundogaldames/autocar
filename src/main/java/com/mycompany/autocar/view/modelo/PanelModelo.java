/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.autocar.view.modelo;

import com.mycompany.autocar.controller.MarcaController;
import com.mycompany.autocar.controller.ModeloController;
import com.mycompany.autocar.model.Marca;
import com.mycompany.autocar.model.Modelo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
/**
 *
 * @author segundo
 */
public class PanelModelo extends JPanel
{
    private final ModeloController modeloController;
    private final MarcaController marcaController;

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private JTextField txtId;
    private JTextField txtNombre;
    private JComboBox<Marca> cmbMarca;

    private JButton btnNuevo;
    private JButton btnGuardar;
    private JButton btnEliminar;
    private JButton btnActualizar;
    
    public PanelModelo(ModeloController modeloController, MarcaController marcaController) {
        this.modeloController = modeloController;
        this.marcaController = marcaController;
        initComponents();
        cargarMarcas();
        cargarTabla();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());

        // ====== FORMULARIO SUPERIOR ======
        JPanel formulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblId = new JLabel("ID:");
        JLabel lblNombre = new JLabel("Nombre modelo:");
        JLabel lblMarca = new JLabel("Marca:");

        txtId = new JTextField(5);
        txtId.setEditable(false);

        txtNombre = new JTextField(20);
        cmbMarca = new JComboBox<>();

        gbc.gridx = 0; gbc.gridy = 0;
        formulario.add(lblId, gbc);
        gbc.gridx = 1;
        formulario.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formulario.add(lblNombre, gbc);
        gbc.gridx = 1;
        formulario.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formulario.add(lblMarca, gbc);
        gbc.gridx = 1;
        formulario.add(cmbMarca, gbc);

        // ====== BOTONES ======
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

        // ====== TABLA ======
        modeloTabla = new DefaultTableModel(new Object[]{"ID", "Nombre", "Marca"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // ====== EVENTOS ======
        btnNuevo.addActionListener(e -> limpiarFormulario());
        btnGuardar.addActionListener(e -> guardarModelo());
        btnEliminar.addActionListener(e -> eliminarModelo());
        btnActualizar.addActionListener(e -> cargarTabla());

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarSeleccion();
            }
        });
    }
    
    // ==========================
    // LÓGICA
    // ==========================

    private void cargarMarcas() {
        try {
            cmbMarca.removeAllItems();
            List<Marca> marcas = marcaController.listar();
            for (Marca m : marcas) {
                cmbMarca.addItem(m);
            }
            cmbMarca.setSelectedIndex(-1); // nada seleccionado al inicio
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar marcas: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarTabla() {
        try {
            modeloTabla.setRowCount(0);
            List<Modelo> modelos = modeloController.listar();

            for (Modelo mo : modelos) {
                modeloTabla.addRow(new Object[]{
                        mo.getId(),
                        mo.getNombre(),
                        mo.getMarca() != null ? mo.getMarca().getNombre() : ""
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar modelos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        cmbMarca.setSelectedIndex(-1);
        tabla.clearSelection();
    }
    
    private void guardarModelo() {
        try {
            String nombre = txtNombre.getText().trim();
            Marca marcaSeleccionada = (Marca) cmbMarca.getSelectedItem();

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre del modelo es obligatorio");
                return;
            }
            if (marcaSeleccionada == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar una marca");
                return;
            }

            Modelo modelo = new Modelo();
            if (!txtId.getText().isEmpty()) {
                modelo.setId(Integer.parseInt(txtId.getText()));
            }
            modelo.setNombre(nombre);
            modelo.setMarca(marcaSeleccionada);

            modeloController.guardar(modelo);
            JOptionPane.showMessageDialog(this, "Modelo guardado correctamente");

            cargarTabla();
            limpiarFormulario();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar modelo: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarModelo() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un modelo");
            return;
        }

        int resp = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el modelo seleccionado?",
                "Confirmar", JOptionPane.YES_NO_OPTION);

        if (resp == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText());
                modeloController.eliminar(id);
                cargarTabla();
                limpiarFormulario();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al eliminar modelo: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void cargarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
            String nombreMarca = modeloTabla.getValueAt(fila, 2).toString();

            // Seleccionar la marca correspondiente en el combo
            for (int i = 0; i < cmbMarca.getItemCount(); i++) {
                Marca m = cmbMarca.getItemAt(i);
                if (m.getNombre().equals(nombreMarca)) {
                    cmbMarca.setSelectedIndex(i);
                    break;
                }
            }
        }
    }
}
