package Logic;

import java.awt.Rectangle;

public class player2 {

    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int defaulty = 160;
    boolean state;
    int mx2 = 0;
    int my2 = 0;
    int cx = 110;
    int cy = 113;
    int pressed = 0;
    int aux = 0;
    public boolean jump = false;
    int lifev1 = 100;
    int lifev2 = 100;
    Rectangle player1;
    Rectangle player2;

    public player2(int x1, int y1, int x2, int y2, Rectangle p1, Rectangle p2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.player1 = p1;
        this.player2 = p2;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public void setJump(boolean pressed) {
        this.jump = pressed;
    }

    public void update() {
        player1 = new Rectangle(x1 + 25, y1 + 35, 70, 100);
        player2 = new Rectangle(x2 + 20, y2 + 40, 70, 140);
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    public int getMx2() {
        return mx2;
    }

    public int getMy2() {
        return my2;
    }

    public boolean isState() {
        return state;
    }

    public int getlifev1() {
        return lifev1;
    }

    public void moveUP() {
        state = false;
        y2 -= 20;
        mx2 = cx * 0;
        my2 = cy * 5;
    }

    public void moveDOWN() {
        state = false;
        y2 += 20;
        mx2 = cx * 0;
        my2 = cy * 5;
    }

    public void moveLEFT() {
        x2 -= 20;
        state = true;
        my2 = 0;
        if (mx2 == 0) {
            mx2 = cx * 3;
        }
        if (mx2 < cx * 3) {
            mx2 -= cx;

        } else if (aux < 5) {
            aux += 1;
            mx2 = cx;
        } else {
            state = false;
            mx2 = cx * 4;
            my2 = cy * 2;
            moveSTOP();

        }
        if (player2.intersects(player1) || x2 < -20) {
            moveRIGHT(true);
        }
    }

    public void moveRIGHT(boolean x) {
        if (x2 < 600) {
            x2 += 20;
        } else {
            moveLEFT();
        }
        if (x) {
            state = false;
            mx2 = cx * 3;
            my2 = cy * 0;
        }

        state = false;

    }

    public void moveSTOP() {
        state = false;
        mx2 = 0;
        if (jump) {
            mx2 = cx * 0;
            my2 = cy * 5;
        } else if (y2 < defaulty) {
            mx2 = cx * 0;
            my2 = cy * 5;
        } else {
            mx2 = cx * 1;
            my2 = cy * 2;
        }

        aux = 0;
        mx2 = cx * 1;
        my2 = cy * 2;
        state = false;
    }

    public void hit() {
        state = false;
        mx2 = cx * 4;
        my2 = cy * 0;

        if (aux < 4) {
            x2 -= 20;
            aux += 1;
        } else {
            mx2 = 0;
        }

        if (player2.intersects(player1)) {
            lifev1 -= 3;
            moveRIGHT(false);
        }

    }

    public void golpe() {
        state = false;
        mx2 = cx * 0;
        my2 = cy * 3;

        if (aux < 2) {
            x2 -= 20;
            aux -= 1;
        } else {
            mx2 = 0;
            my2 = 0;
        }
        if (player2.intersects(player1)) {
            lifev1 -= 1;
            moveRIGHT(false);
        }
    }

    public void patada() {
        state = false;
        mx2 = cx * 5 + 20;
        my2 = cy * 0;
        if (aux < 4) {
            x2 -= 20;
            aux += 1;
        } else {
            mx2 = 0;
            my2 = 0;
        }
        if (player2.intersects(player1)) {
            lifev1 -= 1;
            moveRIGHT(false);
        }

    }

}
