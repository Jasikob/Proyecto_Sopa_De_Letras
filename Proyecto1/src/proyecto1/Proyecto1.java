/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto1;

import javax.swing.SwingUtilities;

/**
 * Clase principal para iniciar la aplicación Sopa de Letras.
 * @author Jesús Schneider
 */
public class Proyecto1 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SopaDeLetrasFrame frame = new SopaDeLetrasFrame();
            frame.setVisible(true);
        });
    }
}