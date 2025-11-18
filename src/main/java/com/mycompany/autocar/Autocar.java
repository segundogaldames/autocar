/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.autocar;

import com.mycompany.autocar.view.MainFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author segundo
 */
public class Autocar 
{

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
