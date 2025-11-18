/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.autocar.view;

import com.mycompany.autocar.controller.MarcaController;
import com.mycompany.autocar.dao.mysql.MySQLMarcaDAO;
import com.mycompany.autocar.view.marca.PanelMarca;

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
        MarcaController marcaController = new MarcaController(new MySQLMarcaDAO());
        PanelMarca panelMarca = new PanelMarca(marcaController);
        tabs.addTab("Marcas", panelMarca);

        // En el futuro agregamos:
        // tabs.addTab("Modelos", new PanelModelo(...));
        // tabs.addTab("Vehículos", new PanelVehiculo(...));

        add(tabs, BorderLayout.CENTER);
    }
}
