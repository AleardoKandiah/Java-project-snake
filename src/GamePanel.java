import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

import javafx.scene.paint.Color;

public class GamePanel extends JPanel implements ActionListener {

 // create matrix for unit size of objects

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE= 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;

    // create matrix to hold coordinates and units of snake
    final int x[] = new int[GAME_UNITS]; 
    final int y[] = new int[GAME_UNITS]; 
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    // create constructor for game methods
    GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGane();
    }
    public void startGane() {
        newApple();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);

        }
        g.setColor(Color.red);
        g.fillOval(appleX, appleX, UNIT_SIZE, UNIT_SIZE);

        // draw head and body of snake
        //  a for loop to iterate through each snake body part
        for(int i = 0;i< bodyParts; i++) {
            if(i == 0) {
                g.setColor(Color.green);
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
            else{
                g.setColor(new Color(45,180,0));
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);

            }
        }
    }

    public void newApple() {
        // generate coordinates of new apple wheneverr method is called
        appleX = random.nextInt((int)(SCREEN_WIDTH*UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT*UNIT_SIZE))*UNIT_SIZE;

    }

    public void move() {
        for(int i = bodyParts; i>0; i--) {
            // shift array coordinates by one
             x[i] = x[i-1];
             y[i] = y[i-1];
        }
        switch(direction) {
        case 'U':
        y[0] = y[0] -UNIT_SIZE;
        case 'D':
        y[0] = y[0] +UNIT_SIZE;
        case 'L':
        x[0] = x[0] -UNIT_SIZE;
        case 'R':
        x[0] = x[0] +UNIT_SIZE;        
    }
    }
    public void checkApple() {

    }

    public void checkCollisions() {
        // check if snake head collides against body trigger game over
        for(int i = bodyParts; i>0; i--) {
            if((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }
        // check if head touches left border
        if(x[0] < 0) {
            running = false;
        }
        // check if head touxhes right border
        if(x[0] > SCREEN_WIDTH) {
            running = false;
        // check if head touches top borde
        if(y[0] < 0) {
            running = false;
        }
    }

    }

    public void gameOver(Graphics g) {

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
                if(running) {
                    move();
                    checkApple();
                    checkCollisions();
                }
                repaint();
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {

        }
    }
}
