/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.autocar.view;

import com.mycompany.autocar.controller.MarcaController;
import com.mycompany.autocar.controller.ModeloController;
import com.mycompany.autocar.dao.mysql.MySQLMarcaDAO;
import com.mycompany.autocar.dao.mysql.MySQLModeloDAO;
import com.mycompany.autocar.view.marca.PanelMarca;
import com.mycompany.autocar.view.modelo.PanelModelo;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author segundo
 */
public class MainFrame extends JFrame
{
    private JTabbedPane tabs;

    public MainFrame() {
        initUI();
    }
    
    private void initUI() {
        setTitle("Automotora - Gestión General");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tabs = new JTabbedPane();

        // ===== PESTAÑA MARCAS ======
        MySQLMarcaDAO marcaDAO = new MySQLMarcaDAO();
        MySQLModeloDAO modeloDAO = new MySQLModeloDAO();
        
        ModeloController modeloController = new ModeloController(modeloDAO);
        MarcaController marcaController = new MarcaController(marcaDAO,modeloDAO);
        
        PanelMarca panelMarca = new PanelMarca(marcaController);
        tabs.addTab("Marcas", panelMarca);

        PanelModelo panelModelo = new PanelModelo(modeloController, marcaController);
        tabs.addTab("Modelos", panelModelo);

        // En el futuro agregamos:
        // tabs.addTab("Modelos", new PanelModelo(...));
        // tabs.addTab("Vehículos", new PanelVehiculo(...));

        add(tabs, BorderLayout.CENTER);
    }
}
