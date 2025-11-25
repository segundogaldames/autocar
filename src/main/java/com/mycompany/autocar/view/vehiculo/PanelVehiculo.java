/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.autocar.view.vehiculo;

import com.mycompany.autocar.controller.MarcaController;
import com.mycompany.autocar.controller.ModeloController;
import com.mycompany.autocar.controller.VehiculoController;
import com.mycompany.autocar.model.Marca;
import com.mycompany.autocar.model.Modelo;
import com.mycompany.autocar.model.Transmision;
import com.mycompany.autocar.model.Vehiculo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
/**
 *
 * @author segundo
 */
public class PanelVehiculo extends JPanel
{
    private final VehiculoController vehiculoController;
    private final ModeloController modeloController;
    private final MarcaController marcaController;

    private JTable tabla;
    private DefaultTableModel modeloTabla;

    // Formulario
    private JTextField txtId;
    private JTextField txtChasis;
    private JTextField txtColor;
    private JTextField txtPrecio;
    private JComboBox<Transmision> cmbTransmision;
    private JCheckBox chkStock;
    private JComboBox<Modelo> cmbModelo;

    private JButton btnNuevo;
    private JButton btnGuardar;
    private JButton btnEliminar;
    private JButton btnActualizar;

    // Filtros
    private JComboBox<Marca> cmbMarcaFiltro;
    private JComboBox<Modelo> cmbModeloFiltro;
    private JButton btnBuscarMarca;
    private JButton btnBuscarModelo;
    private JButton btnQuitarFiltro;
    
    public PanelVehiculo(VehiculoController vehiculoController,
                         ModeloController modeloController,
                         MarcaController marcaController) 
    {
        this.vehiculoController = vehiculoController;
        this.modeloController = modeloController;
        this.marcaController = marcaController;

        initComponents();
        cargarModelosFormulario();
        cargarFiltros();
        cargarTabla();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());

        // ========== FORMULARIO ==========
        JPanel formulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,4,4,4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblId = new JLabel("ID:");
        JLabel lblChasis = new JLabel("N° Chasis:");
        JLabel lblColor = new JLabel("Color:");
        JLabel lblTransmision = new JLabel("Transmisión:");
        JLabel lblPrecio = new JLabel("Precio:");
        JLabel lblStock = new JLabel("En stock:");
        JLabel lblModelo = new JLabel("Modelo:");

        txtId = new JTextField(5);
        txtId.setEditable(false);
        txtChasis = new JTextField(15);
        txtColor = new JTextField(10);
        txtPrecio = new JTextField(10);
        cmbTransmision = new JComboBox<>(Transmision.values());
        chkStock = new JCheckBox();
        cmbModelo = new JComboBox<>();

        int y = 0;
        gbc.gridx = 0; gbc.gridy = y;
        formulario.add(lblId, gbc);
        gbc.gridx = 1;
        formulario.add(txtId, gbc);
        y++;

        gbc.gridx = 0; gbc.gridy = y;
        formulario.add(lblChasis, gbc);
        gbc.gridx = 1;
        formulario.add(txtChasis, gbc);
        y++;

        gbc.gridx = 0; gbc.gridy = y;
        formulario.add(lblColor, gbc);
        gbc.gridx = 1;
        formulario.add(txtColor, gbc);
        y++;

        gbc.gridx = 0; gbc.gridy = y;
        formulario.add(lblTransmision, gbc);
        gbc.gridx = 1;
        formulario.add(cmbTransmision, gbc);
        y++;

        gbc.gridx = 0; gbc.gridy = y;
        formulario.add(lblPrecio, gbc);
        gbc.gridx = 1;
        formulario.add(txtPrecio, gbc);
        y++;

        gbc.gridx = 0; gbc.gridy = y;
        formulario.add(lblStock, gbc);
        gbc.gridx = 1;
        formulario.add(chkStock, gbc);
        y++;

        gbc.gridx = 0; gbc.gridy = y;
        formulario.add(lblModelo, gbc);
        gbc.gridx = 1;
        formulario.add(cmbModelo, gbc);

        // ========== BOTONES CRUD ==========
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnNuevo = new JButton("Nuevo");
        btnGuardar = new JButton("Guardar");
        btnEliminar = new JButton("Eliminar");
        btnActualizar = new JButton("Actualizar lista");

        botones.add(btnNuevo);
        botones.add(btnGuardar);
        botones.add(btnEliminar);
        botones.add(btnActualizar);

        // ========== FILTROS ==========
        JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT));

        cmbMarcaFiltro = new JComboBox<>();
        cmbModeloFiltro = new JComboBox<>();
        btnBuscarMarca = new JButton("Buscar por marca");
        btnBuscarModelo = new JButton("Buscar por modelo");
        btnQuitarFiltro = new JButton("Quitar filtro");

        filtros.add(new JLabel("Marca:"));
        filtros.add(cmbMarcaFiltro);
        filtros.add(btnBuscarMarca);

        filtros.add(new JLabel("Modelo:"));
        filtros.add(cmbModeloFiltro);
        filtros.add(btnBuscarModelo);

        filtros.add(btnQuitarFiltro);

        // Top general (form + botones + filtros)
        JPanel top = new JPanel(new BorderLayout());
        top.add(formulario, BorderLayout.CENTER);
        top.add(botones, BorderLayout.SOUTH);

        JPanel north = new JPanel(new BorderLayout());
        north.add(top, BorderLayout.CENTER);
        north.add(filtros, BorderLayout.SOUTH);

        add(north, BorderLayout.NORTH);

        // ========== TABLA ==========
        modeloTabla = new DefaultTableModel(
                new Object[]{"ID", "Chasis", "Color", "Transmisión", "Precio", "Stock", "Modelo", "Marca"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // ========== EVENTOS ==========
        btnNuevo.addActionListener(e -> limpiarFormulario());
        btnGuardar.addActionListener(e -> guardarVehiculo());
        btnEliminar.addActionListener(e -> eliminarVehiculo());
        btnActualizar.addActionListener(e -> cargarTabla());

        btnBuscarMarca.addActionListener(e -> buscarPorMarca());
        btnBuscarModelo.addActionListener(e -> buscarPorModelo());
        btnQuitarFiltro.addActionListener(e -> {
            cargarTabla();
            tabla.clearSelection();
        });

        cmbMarcaFiltro.addActionListener(e -> actualizarModelosFiltroPorMarca());

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarSeleccion();
            }
        });
    }
    
    private void cargarModelosFormulario() {
        try {
            cmbModelo.removeAllItems();
            List<Modelo> modelos = modeloController.listar();
            for (Modelo m : modelos) {
                cmbModelo.addItem(m);
            }
            cmbModelo.setSelectedIndex(-1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar modelos del formulario: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarFiltros() {
        try {
            // Marcas
            cmbMarcaFiltro.removeAllItems();
            List<Marca> marcas = marcaController.listar();
            for (Marca m : marcas) {
                cmbMarcaFiltro.addItem(m);
            }
            cmbMarcaFiltro.setSelectedIndex(-1);

            // Modelos (todos al inicio)
            cmbModeloFiltro.removeAllItems();
            List<Modelo> modelos = modeloController.listar();
            for (Modelo m : modelos) {
                cmbModeloFiltro.addItem(m);
            }
            cmbModeloFiltro.setSelectedIndex(-1);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar filtros: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizarModelosFiltroPorMarca() {
        Marca marca = (Marca) cmbMarcaFiltro.getSelectedItem();
        if (marca == null) {
            return;
        }
        try {
            cmbModeloFiltro.removeAllItems();
            List<Modelo> modelos = modeloController.listarPorMarca(marca.getId());
            for (Modelo m : modelos) {
                cmbModeloFiltro.addItem(m);
            }
            cmbModeloFiltro.setSelectedIndex(-1);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar modelos por marca: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarTabla() {
        try {
            List<Vehiculo> vehiculos = vehiculoController.listar();
            llenarTabla(vehiculos);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar vehículos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void llenarTabla(List<Vehiculo> vehiculos) {
        modeloTabla.setRowCount(0);
        for (Vehiculo v : vehiculos) {
            modeloTabla.addRow(new Object[]{
                    v.getId(),
                    v.getNumChasis(),
                    v.getColor(),
                    v.getTransmision(),
                    v.getPrecio(),
                    v.isStock() ? "Sí" : "No",
                    v.getModelo() != null ? v.getModelo().getNombre() : "",
                    (v.getModelo() != null && v.getModelo().getMarca() != null)
                            ? v.getModelo().getMarca().getNombre()
                            : ""
            });
        }
    }
    
    private void limpiarFormulario() {
        txtId.setText("");
        txtChasis.setText("");
        txtColor.setText("");
        txtPrecio.setText("");
        chkStock.setSelected(false);
        cmbTransmision.setSelectedIndex(0);
        cmbModelo.setSelectedIndex(-1);
        tabla.clearSelection();
    }
    
    private void guardarVehiculo() {
        try {
            String chasis = txtChasis.getText().trim();
            String color = txtColor.getText().trim();
            String precioStr = txtPrecio.getText().trim();
            Transmision transmision = (Transmision) cmbTransmision.getSelectedItem();
            Modelo modelo = (Modelo) cmbModelo.getSelectedItem();
            boolean stock = chkStock.isSelected();

            if (chasis.isEmpty() || color.isEmpty() || precioStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Chasis, color y precio son obligatorios");
                return;
            }
            if (modelo == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un modelo");
                return;
            }

            int precio;
            try {
                precio = Integer.parseInt(precioStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El precio debe ser numérico");
                return;
            }

            Vehiculo v = new Vehiculo();
            if (!txtId.getText().isEmpty()) {
                v.setId(Integer.parseInt(txtId.getText()));
            }
            v.setNumChasis(chasis);
            v.setColor(color);
            v.setPrecio(precio);
            v.setTransmision(transmision);
            v.setStock(stock);
            v.setModelo(modelo);

            vehiculoController.guardar(v);
            JOptionPane.showMessageDialog(this, "Vehículo guardado correctamente");
            cargarTabla();
            limpiarFormulario();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar vehículo: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarVehiculo() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un vehículo");
            return;
        }

        int resp = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el vehículo seleccionado?",
                "Confirmar", JOptionPane.YES_NO_OPTION);

        if (resp == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(txtId.getText());
                vehiculoController.eliminar(id);
                cargarTabla();
                limpiarFormulario();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error al eliminar vehículo: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void cargarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
            txtChasis.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtColor.setText(modeloTabla.getValueAt(fila, 2).toString());
            cmbTransmision.setSelectedItem(modeloTabla.getValueAt(fila, 3));
            txtPrecio.setText(modeloTabla.getValueAt(fila, 4).toString());
            chkStock.setSelected("Sí".equals(modeloTabla.getValueAt(fila, 5)));

            String nombreModelo = modeloTabla.getValueAt(fila, 6).toString();
            for (int i = 0; i < cmbModelo.getItemCount(); i++) {
                Modelo m = cmbModelo.getItemAt(i);
                if (m.getNombre().equals(nombreModelo)) {
                    cmbModelo.setSelectedIndex(i);
                    break;
                }
            }
        }
    }
    
    private void buscarPorMarca() {
        Marca marca = (Marca) cmbMarcaFiltro.getSelectedItem();
        if (marca == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una marca para filtrar");
            return;
        }
        try {
            List<Vehiculo> lista = vehiculoController.listarPorMarca(marca.getId());
            llenarTabla(lista);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al buscar por marca: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void buscarPorModelo() {
        Modelo modelo = (Modelo) cmbModeloFiltro.getSelectedItem();
        if (modelo == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un modelo para filtrar");
            return;
        }
        try {
            List<Vehiculo> lista = vehiculoController.listarPorModelo(modelo.getId());
            llenarTabla(lista);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al buscar por modelo: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
