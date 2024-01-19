package practice;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.ConcurrentModificationException;
import java.util.List;


// 자바 3차 과제
// 20200110 정태현

public class BallThreadAssig_20200110 extends Frame implements ActionListener {
    List<Ball> balls = new ArrayList<>();
    private BounceCanvas canvas; 

    public BallThreadAssig_20200110() {
        canvas = new BounceCanvas(balls);
        add("Center", canvas);
        Panel p = new Panel();

        Button s = new Button("Start");
        Button c = new Button("Close");

        p.add(s);
        p.add(c);
        s.addActionListener(this);
        c.addActionListener(this);
        add("South", p);
    }

    public void actionPerformed(ActionEvent evt) {
        if ("Start".equals(evt.getActionCommand())) {

            for (int i = 0; i < 5; i++) {

            	Ball ball = new Ball(canvas, getRandomSpeed(), getRandomSpeed(), balls, 16);
            	balls.add(ball); // ArrayList 에 넣기!
                ball.start(); ; // 스레드 시작!
            	
            }
        } else if ("Close".equals(evt.getActionCommand())) {
            System.exit(0); // 시스템 종료
        }
    }
    
    private int getRandomSpeed() {
        return (int) (Math.random() * 4 + 1) * (Math.random() > 0.5 ? 1 : -1);
    }

    public static void main(String[] args) {
        Frame f = new BallThreadAssig_20200110();
        f.setSize(1000, 1000);
        WindowDestroyer listener = new WindowDestroyer();
        f.addWindowListener(listener);
        f.setVisible(true);
    }
}

class BounceCanvas extends Canvas {
    private List<Ball> balls;

    public BounceCanvas(List<Ball> balls) {
        this.balls = balls;
    }
    
    @Override
    public void paint(Graphics g) {
        synchronized (balls) { 
            for (Ball b : balls) {
                b.draw(g); 
            }
        } 
    }

    @Override
    public void update(Graphics g) {
        synchronized (balls) { // balls 리스트에 대한 동기화 블록 시작
            g.clearRect(0, 0, getWidth(), getHeight());
            paint(g);
        } 
    }
}

class Ball extends Thread {
    private Canvas box;
    int XSIZE = 16;
    int YSIZE = 16;
    int x = 0;
    int y = 0;
    int dx;
    int dy;
    private List<Ball> balls;
    private int movesCount = 0;
    private static final int INITIAL_MOVES = 50;
    private boolean collision = false;

    public Ball(Canvas c, int speedX, int speedY, List<Ball> ballsList, int size) {
    	box = c;
    	Dimension d = box.getSize();
    	XSIZE = size;
        YSIZE = size;
        x = d.width / 2 - size / 2;
        y = d.height / 2 - size / 2;
        dx = speedX;
        dy = speedY;
        balls = ballsList;

    }
    
    // 생성자 하나 더 추가하기!
    public Ball(Canvas c, int speedX, int speedY, List<Ball> ballsList, int size, int x, int y) {
    	box = c;
        Dimension d = box.getSize();
        XSIZE = size;
        YSIZE = size;
        this.x = x;
        this.y = y;
        dx = speedX;
        dy = speedY;
        balls = ballsList;
    }
      

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(x, y, XSIZE, YSIZE);
    }
    
    public void run() {
        try {
            while (!Thread.interrupted()) {
                move();
                if (movesCount > INITIAL_MOVES) {
                    checkCollisionAndResize();
                }
                if (XSIZE <= 1 || YSIZE <= 1) {
                    break; // 공의 크기가 너무 작아지면 스레드 종료
                }
                Thread.sleep(5);
            }
        } catch (InterruptedException e) {
            // 스레드가 인터럽트 되었을 때 처리
        } catch (ConcurrentModificationException e) {
            // 이 예외를 무시하고 싶다면 catch 블록을 비워 두지만, 이것은 권장되지 않습니다.
        } finally {
            // 스레드가 종료되기 전에 실행할 코드, 예를 들어 자원 정리 등
            synchronized (balls) {
                balls.remove(this);
            }
        }
    }

    public void move() {
        if (collision) {
            collision = false;
        }
        x += dx;
        y += dy;
        movesCount++;
        Dimension d = box.getSize();
        if (x < 0 || x + XSIZE >= d.width) { dx = -dx; }
        if (y < 0 || y + YSIZE >= d.height) { dy = -dy; }
        
        if (movesCount > INITIAL_MOVES) {
            checkCollisionAndResize();
        }
        box.repaint();
    }

    
    // 원래 것.
    private void checkCollisionAndResize() {
        synchronized (balls) {
            for (Ball b : balls) {
                if (b != this && b.getBounds().intersects(this.getBounds())) {
                    if (!collision && !b.collision) {
                    	
                    	// 충돌 난 위치!
                    	int accident_x = this.x;
                    	int accident_y = this.y;
                    	
                    	// 사고 났을 때의 사이즈 
                    	int size_this = this.XSIZE;
                    	int size_b = b.XSIZE;
                  	
                    	
                    	synchronized (balls) {
                    		
                    	    Ball b1 = new Ball(box, getRandomSpeed(), getRandomSpeed(), balls, size_this / 2, accident_x, accident_y);
                    	    Ball b2 = new Ball(box, getRandomSpeed(), getRandomSpeed(), balls, size_this / 2, accident_x, accident_y);
                    	    Ball b3 = new Ball(box, getRandomSpeed(), getRandomSpeed(), balls, size_b / 2, accident_x, accident_y);
                    	    Ball b4 = new Ball(box, getRandomSpeed(), getRandomSpeed(), balls, size_b / 2, accident_x, accident_y);

                    	    b1.start();
                    	    b2.start();
                    	    b3.start();
                    	    b4.start();

                    	    balls.add(b1);
                    	    balls.add(b2);
                    	    balls.add(b3);
                    	    balls.add(b4);

                    	    balls.remove(this);
                    	    balls.remove(b);
                    	    this.interrupt();
                    	    b.interrupt();
                    	}

                        return; // 현재 스레드를 종료합니다.
                        
                    }
                } else {
                    collision = false;
                    b.collision = false;
                }
            }
        }
    }
    
  

    private int getRandomSpeed() {
        return (int) (Math.random() * 4 + 1) * (Math.random() > 0.5 ? 1 : -1);
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, XSIZE, YSIZE);
    }
}


// WindowDestroyer

class WindowDestroyer extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
}
