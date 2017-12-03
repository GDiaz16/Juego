/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videojuego;

import Logic.inicio;
import java.awt.event.KeyListener;
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
        add(new inicio(this));
        setVisible(true);

    }
    

    @Override
    public synchronized void addKeyListener(KeyListener me) {
        super.addKeyListener(me); //To change body of generated methods, choose Tools | Templates.
        
    }

    public static void main(String args[]) {
        new Videojuego();
    }

}
