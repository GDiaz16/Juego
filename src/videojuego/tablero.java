package videojuego;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import static java.lang.System.err;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import org.netbeans.lib.awtextra.AbsoluteLayout;

/**
 *
 * @author user
 */
public class tablero extends JPanel implements Runnable {

    private int x1 = 120; // x1 puesto que es donde se posicionara por defecto la imagen 1
    private int y1 = 250; // una sola y ,porque ambas imagenes permaneceran en un mismo eje y
    private int y2 = 250;
    private int x2 = 480; //x2 porque es en donde se posicionará por defecto la imagen 2
    private int defaulty = 250;
    private Thread hilo;
    //posiciones por defecto de los personajes;
    int incremento1 = 0;
    int incremento2 = 0;
    int mx = 0;
    int my = 0;
    int mx2 = 0;
    int my2 = 0;
    protected JFrame frame;
    ImageIcon peleador1;
    ImageIcon peleador2;
    int pressed = 0;
    int aux = 0;
    boolean jump = false;
    JProgressBar life1;
    JProgressBar life2;

    public tablero(JFrame frame) {
        hilo = new Thread(this);
        hilo.start();
        this.frame = frame;
        this.setLayout(new GridLayout(10, 2));
        eventos();
        peleador1 = seleccionarJugador(1);
        peleador2 = seleccionarJugador(2);
        life1 = new JProgressBar();
        life2 = new JProgressBar();
        life1.setForeground(Color.red);
        //life1.setLocation(10, 30);
        life1.setSize(160, 30);

        //life2.setLocation(400, 90);
        life2.setSize(160, 30);
        add(life1);
        add(life2);
        life1.setValue(70);
        //life2.setValue(50);
        life1.setVisible(true);
        life2.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        Dimension tam = getSize();

        ImageIcon background = new ImageIcon(new ImageIcon(getClass().getResource("/media/escenary.png")).getImage());

        g.drawImage(background.getImage(), 0, 0, tam.width, tam.height, null);
        g.drawImage(peleador1.getImage(), x1, y1, (x1 + 100) + 50, y1 + 150, mx, my, mx + 50, my + 50, this);
        g.drawImage(peleador2.getImage(), x2, y2, (x2 + 90) + 110, 200 + 110, mx2, my2, mx2 + 110, my2 + 113, this);
        life1.update(g);
        /// life1.setLocation(x1, y1);
        life2.update(g);
        //g.drawImage

        repaint();

    }

    public ImageIcon seleccionarJugador(int jugador) {
        ImageIcon peleador;
        switch (jugador) {
            case 1: {
                peleador = new ImageIcon(new ImageIcon(getClass().getResource("/media/megaman.png")).getImage());
                break;
            }
            case 2: {
                peleador = new ImageIcon(new ImageIcon(getClass().getResource("/media/wendy.png")).getImage());
                break;
            }
            default: {
                peleador = new ImageIcon(new ImageIcon(getClass().getResource("/media/wendy.png")).getImage());
                break;
            }
        }

        return peleador;
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

                eventos(ke);

               // System.out.println("teclado");

            }

            /* Detectar cuando se suelta una letra */
            @Override
            public void keyReleased(KeyEvent ke) {
                eventos(ke);
                pressed = 0;
                // moveSTOP();
            }

        });

    }

    public void eventos(KeyEvent ke) {
        System.out.println("evento");
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_UP:
                pressed = 1;
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("left");
                pressed = 2;
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("right");
                pressed = 3;
                break;
            case KeyEvent.VK_S:
                pressed = 4;
                break;
            case KeyEvent.VK_X:
                pressed = 5;
                System.out.println("golpe1");
                break;
            default:
                pressed = 0;
                break;

        }

    }

    public void movimientos() {
        if (jump && y1 > 80) {
            moveUP();
        } else if (y1 < 80 || y1 < defaulty) {
            jump = false;
            moveDOWN();
        }
        switch (pressed) {
            case 1:
                jump = true;
                System.out.println("up");
                break;
            case 2:
                moveLEFT();
                System.out.println("left");
                break;

            case 3:
                moveRIGHT();
                System.out.println("right");
                break;
            case 4:
                hit();
                break;
            case 5:
                golpe();
                System.out.println("golpe");
                break;
            default:
                moveSTOP();
                System.out.println("stop error");
                break;
        }
    }

    public void run() {
        frame.setFocusable(true);
        System.out.println("press " + pressed);

        while (true) {
            movimientos();
            try {

                Thread.sleep(50);
            } catch (InterruptedException ex) {
                System.out.println(err);
            }

        }
    }

    private void moveUP() {
        y1 -= 20;
        mx = 300;
    }

    private void moveLEFT() {
        if (x1 > -30) {
            x1 -= 20;
        } else {
            x1 = -20;
        }
        mx = 100;
        my = 200;

    }

    private void moveRIGHT() {
        x1 += 20;

        my = 0;
        if (mx == 0) {
            mx = 100;
        }
        if (mx < 250) {
            mx += 50;

        } else if (aux < 1) {
            aux += 1;
            mx = 100;
        } else {
            mx = 350;

        }

    }

    private void moveSTOP() {
        mx = 0;
        if (jump) {
            mx = 300;
        } else if (y1 < defaulty) {
            mx = 400;
        } else {
            mx = 0;
        }

        aux = 0;
        my = 0;
    }

    private void moveDOWN() {
        y1 += 20;
        mx = 400;
    }

    private void hit() {

        if (mx == 0) {
            mx = 450;
            my = 250;
        }
        if (aux < 6) {
            x1 += 20;
            aux += 1;
        } else {
            mx = 0;
        }
    }

    private void golpe() {
        if (mx == 0) {
            mx = 200;
            my = 150;
        }
        if (aux < 3) {
            x1 += 20;
            aux += 1;
        } else {
            mx = 0;
        }
    }

}
