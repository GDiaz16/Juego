package videojuego;

import Logic.inicio;
import Logic.player2;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import static java.lang.System.err;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class tablero extends javax.swing.JPanel implements Runnable {

    tablero tab;
    private int x1 = 120; // x1 puesto que es donde se posicionara por defecto la imagen 1
    private int y1 = 250; // una sola y ,porque ambas imagenes permaneceran en un mismo eje y
    private int y2 = 180;
    private int x2 = 480; //x2 porque es en donde se posicionará por defecto la imagen 2
    private int defaulty = 250;
    private Thread hilo;
    //posiciones por defecto de los personajes;

    int mx = 0;
    int my = 0;
    int mx2 = 0;
    int my2 = 0;
    protected JFrame frame;
    ImageIcon peleador1;
    ImageIcon peleador2;
    int pressed = 0;
    int pressed2 = 0;
    int aux = 0;
    boolean jump = false;
    int lifev1 = 100;
    int lifev2 = 100;
    Rectangle player1;
    Rectangle player2;
    private player2 jugador2;

    public tablero(JFrame frame) {
        initComponents();
        jLabel1.setVisible(false);
        jButton1.setVisible(false);
        jButton2.setVisible(false);
        player1 = new Rectangle(x1 + 25, y1 + 35, 90, 70);
        player2 = new Rectangle(x2 + 25, y2 + 40, 70, 250);
        jugador2 = new player2(x1, y1, x2, y2, player1, player2);
        //update();
        hilo = new Thread(this);
        this.frame = frame;
        eventos();
        peleador1 = seleccionarJugador(1);
        peleador2 = seleccionarJugador(2);
        hilo.start();
    }

    @Override
    public void paint(Graphics g) {
        Dimension tam = getSize();
        life1.update(g);
        life2.update(g);
        ImageIcon background = new ImageIcon(new ImageIcon(getClass().getResource("/media/escenary.png")).getImage());
        if (jugador2.isState()) {
            peleador2 = seleccionarJugador(3);
        } else {
            peleador2 = seleccionarJugador(2);
        }
        g.drawImage(background.getImage(), 0, 0, tam.width, tam.height, null);
        g.drawImage(peleador1.getImage(), x1, y1, x1 + 150, y1 + 150, mx, my, mx + 50, my + 50, this);
        g.drawImage(peleador2.getImage(), jugador2.getX2(), jugador2.getY2() + 55, jugador2.getX2() + 100, jugador2.getY2() + 180,
                jugador2.getMx2(), jugador2.getMy2(), jugador2.getMx2() + 110, jugador2.getMy2() + 113, this);
        // g.drawRect((int) player1.getX(), (int) player1.getY(), (int) player1.width, (int) player1.height);
        // g.drawRect((int) player2.getX(), (int) player2.getY(), (int) player2.width, (int) player2.height);
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
                peleador = new ImageIcon(new ImageIcon(getClass().getResource("/media/eric.png")).getImage());
                break;
            }
            case 3: {
                peleador = new ImageIcon(new ImageIcon(getClass().getResource("/media/erict.png")).getImage());
                break;

            }
            default:
                peleador = new ImageIcon(new ImageIcon(getClass().getResource("/media/erict.png")).getImage());
                break;
        }

        return peleador;
    }

    public void eventos() {

        frame.addKeyListener(new java.awt.event.KeyAdapter() {

            /* Detectar cuando se tipea una letra */
            @Override
            public void keyTyped(KeyEvent ke) {
            }

            /* Detectar cuando se presiona una letra */
            @Override
            public void keyPressed(KeyEvent ke) {

                eventos(ke);

            }

            /* Detectar cuando se suelta una letra */
            @Override
            public void keyReleased(KeyEvent ke) {
                eventos(ke);
                pressed = 0;
                pressed2 = 0;

            }

        });

    }

    public void eventos(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_UP:
                pressed = 1;
                break;
            case KeyEvent.VK_LEFT:
                pressed = 2;
                break;
            case KeyEvent.VK_RIGHT:
                pressed = 3;
                break;
            case KeyEvent.VK_NUMPAD1:
                pressed = 4;
                break;
            case KeyEvent.VK_NUMPAD2:
                pressed = 5;
                break;
            case KeyEvent.VK_NUMPAD3:
                pressed = 6;
                break;
            default:
                pressed = 0;
                break;
        }
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_W:
                pressed2 = 7;
                break;
            case KeyEvent.VK_A:
                pressed2 = 8;
                break;
            case KeyEvent.VK_D:
                pressed2 = 9;
                break;
            case KeyEvent.VK_E:
                pressed2 = 10;
                break;
            case KeyEvent.VK_R:
                pressed2 = 11;
                break;
            case KeyEvent.VK_F:
                pressed2 = 12;
                break;
            default:
                pressed2 = 0;
                break;

        }

    }

    public void saltar(int x) {
        switch (x) {
            case 1:
                if (jump && y1 > 80) {
                    moveUP();
                } else if (y1 < 80 || y1 < defaulty) {
                    jump = false;
                    moveDOWN();
                }
                break;
            case 2:
                if (jugador2.jump && jugador2.getY2() > 70) {
                    jugador2.moveUP();
                } else if (jugador2.getY2() < 70 || jugador2.getY2() < defaulty - 80) {
                    jugador2.setJump(false);
                    jugador2.moveDOWN();
                }
                break;
        }
    }

    public void movimientos() {
        saltar(1);
        switch (pressed) {
            case 1:
                jump = true;
                break;
            case 2:
                moveLEFT(true);
                break;

            case 3:
                moveRIGHT();
                break;
            case 4:
                hit();
                break;
            case 5:
                golpe();
                break;
            case 6:
                patada();
                break;
            default:
                moveSTOP();
                break;
        }
        saltar(2);
        switch (pressed2) {
            case 7:
                jugador2.setJump(true);

                break;
            case 8:
                jugador2.moveLEFT();
                break;
            case 9:
                jugador2.moveRIGHT(false);
                break;
            case 10:
                jugador2.hit();
                break;
            case 11:
                jugador2.golpe();
                break;
            case 12:
                jugador2.patada();
                break;
            default:
                jugador2.moveSTOP();
                break;
        }
    }

    public void update() {
        player1 = new Rectangle(x1 + 25, y1 + 35, 70, 90);
        player2 = new Rectangle(jugador2.getX2() + 20, jugador2.getY2() + 40, 70, 140);
        jugador2.setX1(x1);
        jugador2.setY1(y1);
        jugador2.update();
        life1.setValue(jugador2.getlifev1());
        lifev1 = jugador2.getlifev1();

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
        if (x) {
            mx = 100;
            my = 200;
        }

    }

    private void moveRIGHT() {
        if (x1 < 640) {
            x1 += 20;
        }

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
        if (aux < 4) {
            x1 += 20;
            aux += 1;
        } else {
            mx = 0;
        }

        if (player1.intersects(player2)) {
            life2.setValue(lifev2 -= 3);
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
            life2.setValue(lifev2 -= 1);
            moveLEFT(false);
        }

    }

    public void winner() {
        if (lifev1 <= 0) {
            jLabel1.setVisible(true);
            jLabel1.setText("JUGADOR 2 GANA");
            jButton1.setVisible(true);
            jButton2.setVisible(true);
            hilo.suspend();
        } else if (lifev2 <= 0) {
            jLabel1.setVisible(true);
            jLabel1.setText("JUGADOR 1 GANA");
            jButton1.setVisible(true);
            jButton2.setVisible(true);
            hilo.suspend();
        } else if (lifev1 <= 0 && lifev2 <= 0) {
            jLabel1.setVisible(true);
            jLabel1.setText("EMPATE");
            jButton1.setVisible(true);
            jButton2.setVisible(true);
            hilo.suspend();
        }
    }

    public void run() {
        frame.setFocusable(true);

        while (true) {
            movimientos();
            update();
            winner();

            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                System.out.println(err);
            }

        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        life1 = new javax.swing.JProgressBar();
        life2 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setOpaque(false);

        life1.setBackground(new java.awt.Color(204, 204, 204));
        life1.setFont(new java.awt.Font("Showcard Gothic", 0, 14)); // NOI18N
        life1.setForeground(new java.awt.Color(0, 204, 0));
        life1.setToolTipText("");
        life1.setValue(100);
        life1.setOpaque(true);
        life1.setString("jugador 1");
        life1.setStringPainted(true);

        life2.setBackground(new java.awt.Color(204, 204, 204));
        life2.setFont(new java.awt.Font("Showcard Gothic", 0, 14)); // NOI18N
        life2.setForeground(new java.awt.Color(0, 204, 0));
        life2.setValue(100);
        life2.setOpaque(true);
        life2.setString("jugador 2");
        life2.setStringPainted(true);

        jLabel1.setFont(new java.awt.Font("Showcard Gothic", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 0));
        jLabel1.setText("JUGADOR 1 GANA");

        jButton1.setFont(new java.awt.Font("Showcard Gothic", 0, 12)); // NOI18N
        jButton1.setText("REINICIAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Showcard Gothic", 0, 12)); // NOI18N
        jButton2.setText("inicio");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(life1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(135, 135, 135)
                .addComponent(life2, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(257, 257, 257)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jButton2))))
            .addGroup(layout.createSequentialGroup()
                .addGap(239, 239, 239)
                .addComponent(jLabel1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(life2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(life1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(126, 126, 126)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        tab = new tablero(frame);
        try {
            this.setVisible(false);
            this.getTopLevelAncestor().add(tab);
            this.getTopLevelAncestor().remove(2);
            tab.revalidate();
            hilo.resume();
        } catch (ArrayIndexOutOfBoundsException ex) {

        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setVisible(false);
        this.getTopLevelAncestor().add(new inicio(frame));
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar life1;
    private javax.swing.JProgressBar life2;
    // End of variables declaration//GEN-END:variables
}
