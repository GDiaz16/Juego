package videojuego;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import static java.lang.System.err;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class tablero2 extends javax.swing.JPanel implements Runnable {

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
    int lifev1 = 100;
    int lifev2 = 100;
    Rectangle player1;
    Rectangle player2;

    public tablero2(JFrame frame) {
        initComponents();
        hilo = new Thread(this);
        hilo.start();
        this.frame = frame;
        eventos();
        peleador1 = seleccionarJugador(1);
        peleador2 = seleccionarJugador(2);

    }

    @Override
    public void paint(Graphics g) {
        Dimension tam = getSize();
        life1.update(g);
        life2.update(g);
        ImageIcon background = new ImageIcon(new ImageIcon(getClass().getResource("/media/escenary.png")).getImage());

        g.drawImage(background.getImage(), 0, 0, tam.width, tam.height, null);
        g.drawImage(peleador1.getImage(), x1, y1, x1 + 150, y1 + 150, mx, my, mx + 50, my + 50, this);
        g.drawImage(peleador2.getImage(), x2, y2, x2 + 150, y2 + 130, mx2, my2, mx2 + 110, my2 + 113, this);
        g.drawRect((int) player1.getX(), (int) player1.getY(), (int) player1.width, (int) player1.height);
        g.drawRect((int) player2.getX(), (int) player2.getY(), (int) player2.width, (int) player2.height);
        super.paint(g);
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

                //  System.out.println("teclado");
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
        //   System.out.println("evento");
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_UP:
                pressed = 1;
                break;
            case KeyEvent.VK_LEFT:
                //   System.out.println("left");
                pressed = 2;
                break;
            case KeyEvent.VK_RIGHT:
                //   System.out.println("right");
                pressed = 3;
                break;
            case KeyEvent.VK_S:
                pressed = 4;
                break;
            case KeyEvent.VK_X:
                pressed = 5;
                // System.out.println("golpe1");
                break;
            case KeyEvent.VK_Z:
                pressed = 6;
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
                // System.out.println("up");
                break;
            case 2:
                moveLEFT(true);
                // System.out.println("left");
                break;

            case 3:
                moveRIGHT();
                // System.out.println("right");
                break;
            case 4:
                hit();
                break;
            case 5:
                golpe();
                //  System.out.println("golpe");
                break;
            case 6:
                patada();
                // System.out.println("golpe");
                break;
            default:
                moveSTOP();
                //System.out.println("stop");
                break;
        }
    }

    public void run() {
        frame.setFocusable(true);
        System.out.println("press " + pressed);

        while (true) {
            movimientos();
            update();
            try {

                Thread.sleep(50);
            } catch (InterruptedException ex) {
                System.out.println(err);
            }

        }
    }

    public void update() {
        player1 = new Rectangle(x1 + 25, y1 + 35, 70, 100);
        player2 = new Rectangle(x2 + 50, y2 + 30, 70, 100);
    }

    private void moveUP() {
        y1 -= 20;
        mx = 300;
    }

    private void moveLEFT(boolean x) {
        if (x1 > -30) {
            x1 -= 20;
        } else {
            x1 = -20;
        }
        if(x){
        mx = 100;
        my = 200;
        }
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
        if (player1.intersects(player2)) {
            moveLEFT(false);
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

        if (player1.intersects(player2)) {
            life2.setValue(lifev2 -= 3);
            System.out.println("toque");
            moveLEFT(false);
        }

    }

    private void golpe() {
        if (mx == 0) {
            mx = 200;
            my = 150;
        }
        if (aux < 2) {
            x1 += 20;
            aux += 1;
        } else {
            mx = 0;
            my = 0;
        }
        if (player1.intersects(player2)) {
            System.out.println("golpe");
            life2.setValue(lifev2 -= 1);
            moveLEFT(false);
        }
    }

    private void patada() {
        if (mx == 0) {
            mx = 250;
            my = 200;
        }
        if (aux < 4) {
            x1 += 20;
            aux += 1;
        } else {
            mx = 0;
            my = 0;
        }
        if (player1.intersects(player2)) {
            System.out.println("patada");
            life2.setValue(lifev2 -= 1);
            moveLEFT(false);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        life1 = new javax.swing.JProgressBar();
        life2 = new javax.swing.JProgressBar();

        setOpaque(false);

        life1.setBackground(new java.awt.Color(204, 204, 204));
        life1.setFont(new java.awt.Font("Showcard Gothic", 0, 14)); // NOI18N
        life1.setForeground(new java.awt.Color(0, 204, 0));
        life1.setValue(80);
        life1.setOpaque(true);
        life1.setString("Player");
        life1.setStringPainted(true);

        life2.setBackground(new java.awt.Color(204, 204, 204));
        life2.setFont(new java.awt.Font("Showcard Gothic", 0, 14)); // NOI18N
        life2.setForeground(new java.awt.Color(0, 204, 0));
        life2.setValue(60);
        life2.setOpaque(true);
        life2.setString("CPU");
        life2.setStringPainted(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(life1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(135, 135, 135)
                .addComponent(life2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(life2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(life1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(405, 405, 405))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar life1;
    private javax.swing.JProgressBar life2;
    // End of variables declaration//GEN-END:variables
}
