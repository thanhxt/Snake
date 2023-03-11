import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS =  (SCREEN_HEIGHT*SCREEN_WIDTH/UNIT_SIZE);
    static final int DELAY = 75; //higher the number, the slower the game
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int pointsEaten;
    int pointX;
    int pointY;
    char direction = 'R'; //begin which direction the game starts
    boolean running = false;
    Timer timer;
    Random random;

    public GamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdpter());
        startGame();
    }

    public void startGame(){
        newPoint();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        //Draw lines inside our Grid
        for(int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE;i++){
            g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE,SCREEN_HEIGHT);
            g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i*UNIT_SIZE);
        }
        //draw Points
        g.setColor(Color.RED);
        g.fillOval(pointX,pointY,UNIT_SIZE,UNIT_SIZE);

       
    }

    /**
     * creating a new Point that the Snake is eating
     */
    public void newPoint(){
        pointX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        pointY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    /**
     * moving the Snake
     */
    public void move(){
        for(int i = bodyParts; i > 0 ; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
            switch(direction){
                case 'U':
                    y[0] = y[0] - UNIT_SIZE;
                case 'D':
                    y[0] = y[0] + UNIT_SIZE;
                case 'L':
                    x[0] = x[0] - UNIT_SIZE;
                case 'R':
                    x[0] = x[0] + UNIT_SIZE;
                break;
            }
        }
    }

    /**
     * checks how much Points have been eaten
     */
    public void checkPoints(){

    }

    public void checkCollisions(){

    }

    public void gameOver(Graphics g){

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public class MyKeyAdpter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){

        }
    }
}
