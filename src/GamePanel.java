import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 18;
    static final int GAME_UNITS =  (SCREEN_HEIGHT*SCREEN_WIDTH/UNIT_SIZE);
    static final int DELAY = 75; //higher the number, the slower the game
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 1;
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
        if(running) {
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            //draw Points
            g.setColor(Color.RED);
            g.fillOval(pointX, pointY, UNIT_SIZE, UNIT_SIZE);

            //draw Snake
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    System.out.println('H' + i);
                    System.out.println(running);
                    g.setColor(Color.YELLOW);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    System.out.println('B' + i);
                    g.setColor(Color.ORANGE);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.RED);
            g.setFont(new Font("Cambria",Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + pointsEaten,(SCREEN_WIDTH - metrics.stringWidth("Score: " + pointsEaten)) /2, g.getFont().getSize());
        }
        else{
            gameOver(g);
        }
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
        if((x[0] == pointX) && y[0] == pointY){
            bodyParts++;
            pointsEaten++;
            newPoint();
        }
    }

    public void checkCollisions(){
        //PROBLEM WITH THIS FOR LOOP
        //GIVES COLLUSION WITH BODY BY MOVING DOWN AND THEN LEFT
        //if head collides with body
        for(int i = bodyParts; i > 0 ; i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }
        //if head touch left border
        if((x[0] < 0))
            running = false;

        //if head touch right border
        if(x[0] > SCREEN_WIDTH)
            running = false;

        //if head touch top border
        if(y[0] < 0)
            running = false;

        //if head touch bottom border
        if(y[0] > SCREEN_HEIGHT)
            running = false;

        if(!running)
            timer.stop();
    }

    public void gameOver(Graphics g){
        //GAME OVER Text
        g.setColor(Color.RED);
        g.setFont(new Font("Cambria",Font.BOLD,75));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("GAME OVER",(SCREEN_WIDTH - metrics.stringWidth("GAME OVER")) /2, SCREEN_HEIGHT / 2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkPoints();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdpter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
