package videojuego;

import java.awt.Graphics;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static java.lang.System.err;

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
    protected JFrame frame;

    public tablero(JFrame frame) {
          hilo = new Thread(this);
        hilo.start();
        this.frame = frame;
        eventos();
        
     
    }

    @Override
    public void paint(Graphics g) {
        Dimension tam = getSize();

        int mx = (incremento1 % 10) * 50;
        int my = (incremento1 / 10) * 50;

        int mx2 = (incremento2 % 9) * 109;
        int my2 = (incremento2 / 6) * 113;

        ImageIcon background = new ImageIcon(new ImageIcon(getClass().getResource("/media/Battle_background_2.png")).getImage());
        ImageIcon peleador = new ImageIcon(new ImageIcon(getClass().getResource("/media/megaman.png")).getImage());
        ImageIcon peleador2 = new ImageIcon(new ImageIcon(getClass().getResource("/media/wendy.png")).getImage());
        g.drawImage(background.getImage(), 0, 0, tam.width, tam.height, null);
        g.drawImage(peleador.getImage(), x1, defaulty, (x1 + 100) + 50, 300 + 50, mx, my, mx + 50, my + 50, this);
        g.drawImage(peleador2.getImage(), x2, defaulty, (x2 + 90) + 110, 200 + 110, mx2, my2, mx2 + 110, my2 + 113, this);

        repaint();

    }

    public void eventos() {
        System.out.println("eventos");
        
        frame.addKeyListener(new java.awt.event.KeyAdapter() {

            /* Detectar cuando se tipea una letra */
            @Override
            public void keyTyped(KeyEvent ke) {
            }

            /* Detectar cuando se presiona una letra */
            @Override
            public void keyPressed(KeyEvent ke) {

                System.out.println("teclado");

            }

            /* Detectar cuando se suelta una letra */
            @Override
            public void keyReleased(KeyEvent ke) {

            }

        });
        frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("algo");
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    public void eventos2(KeyEvent ke) {
        System.out.println("evento");
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_UP:
                moveUP();
                break;

            case KeyEvent.VK_LEFT:
                System.out.println("left");
                moveLEFT();
                break;

            case KeyEvent.VK_RIGHT:
                System.out.println("right");
                moveRIGHT();
                break;
        }

    }

    public void run() {
    frame.setFocusable(true);
        while (true) {
            try {

                Thread.sleep(40);
            } catch (InterruptedException ex) {
                System.out.println(err);
            }
            /*//x1++;
            if (x1 >= 700) {
                x1 = 120;
            }
            incremento1 = 3;
            if (incremento1 > 70) {
                incremento1 = 0;
            }
            incremento2 = 3;
            if (incremento2 > 54) {
                incremento2 = 0;
            }*/
        }
    }

    private void moveUP() {
        //1++;
    }

    private void moveLEFT() {
        x1--;
    }

    private void moveRIGHT() {
        x2++;
    }
}
