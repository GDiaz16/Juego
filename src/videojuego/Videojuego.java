
package videojuego;

import Logic.inicio;
import javax.swing.JFrame;

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
