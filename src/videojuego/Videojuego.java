/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videojuego;

import Logic.inicio;
import javax.swing.JFrame;

/**
 *
 * @author user
 */
public class Videojuego extends JFrame {

    public Videojuego() {

        setResizable(false);
        setSize(700, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        inicio inicio = new inicio(this);
        add(inicio);
        setVisible(true);

    }
    

   

    public static void main(String args[]) {
        new Videojuego();
    }

}
