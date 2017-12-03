/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videojuego;

import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.Dimension;
import static java.lang.System.err;
import java.util.logging.Level;
import java.util.logging.Logger;
import static jdk.nashorn.internal.objects.NativeDebug.getClass;

/**
 *
 * @author user
 */
public class tablero extends JPanel implements Runnable {

    private int x1 = 120; // x1 puesto que es donde se posicionara por defecto la imagen 1
    private int defaulty = 200; // una sola y ,porque ambas imagenes permaneceran en un mismo eje y
    private int x2 = 480; //x2 porque es en donde se posicionará por defecto la imagen 2
    private Thread hilo;
    //posiciones por defecto de los personajes;
    int incremento1 = 0;
    int incremento2 = 0;
    
    
    
    
    

    @Override
    public void paint(Graphics g) {
        Dimension tam = getSize();
        
        int mx = (incremento1 % 10) * 50;
        int my = (incremento1 / 10) * 50;
        
        int mx2 = (incremento2 % 9) * 109;
        int my2 = (incremento2 / 6 ) * 113;
        
        ImageIcon background = new ImageIcon(new ImageIcon(getClass().getResource("/media/Battle background 2.png")).getImage());
        ImageIcon peleador = new ImageIcon(new ImageIcon(getClass().getResource("/media/megaman.png")).getImage());
        ImageIcon peleador2 = new ImageIcon(new ImageIcon(getClass().getResource("/media/wendy.png")).getImage());
        g.drawImage(background.getImage(), 0, 0, tam.width, tam.height, null);
        g.drawImage(peleador.getImage(), x1, defaulty, (x1 + 100) + 50, 300 + 50, mx, my, mx + 50, my + 50, this);
        g.drawImage(peleador2.getImage(), x2, defaulty, (x2 + 90) + 110, 200 + 110, mx2, my2, mx2 + 110, my2 + 113, this);
        hilo = new Thread(this);

      

        repaint();

        hilo.start();

    }

    @Override
    public void run() {
        while (true) {
            try {

                Thread.sleep(40);
            } catch (InterruptedException ex) {
                System.out.println(err);
            }
            x1++;
            if (x1 >= 700) {
                x1 = 120;
            }
            incremento1++;
            if (incremento1 > 70) {
                incremento1 = 0;
            }
            incremento2++;
            if (incremento2 >54){
            incremento2 = 0;
            }
        }
    }
}
