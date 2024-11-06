import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;

public class GamePanel extends JPanel {

    int speedY = 2;
    int circleY = 100;
    int circleX = 50;
    int playerY = 256;
    int playerX = 375;
    int playerWidth = 70;
    int playerHeight = 50;
    int appleY = 100;
    int appleX = 100;
    int appleWidth = 30;
    int appleHeight = 30;
    boolean gameOver;
    boolean reachedBottom = false;
    boolean reachedLeft = false;
    String playerDirection = "a";

    Rectangle playerRectangle;
    Ellipse2D enemyCircle;
    Rectangle appleRectangle;
    Random randomObject = new Random();

    GameWindow gameWindowObject;
    BufferedImage appleBufferedImage;
    BufferedImage blueBallBufferedImage;
    BufferedImage playerBufferedImage;

    // this is a constructor of the class
    GamePanel(GameWindow passAGameWindowObject) {
        setFocusable(true);
        requestFocus();
        gameWindowObject = passAGameWindowObject;
        playerRectangle = new Rectangle();
        enemyCircle = new Ellipse2D.Float();
        appleRectangle = new Rectangle();

        attachKeyListener();
        attachMouseListener();
        importFruit();
    }

    public void importFruit() {
        URL apple;
        URL blueBall = getClass().getResource("BlueBallSpriteTest.PNG");
        URL player = getClass().getResource("player.PNG");
        apple = getClass().getResource("apple.png");
        try {
            blueBallBufferedImage = ImageIO.read(blueBall);
            playerBufferedImage = ImageIO.read(player);
            appleBufferedImage = ImageIO.read(apple);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void attachKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                if (e.getKeyChar() == 'w') {
                    movePlayer("w");

                } else if (e.getKeyChar() == 'd') {
                    movePlayer("d");
                } else if (e.getKeyChar() == 's') {
                    movePlayer("s");
                } else if (e.getKeyChar() == 'a') {
                    movePlayer("a");
                }

            }
        });
    }

    public void movePlayer(String key) {
        if (Objects.equals(playerDirection, key)) {
            return;
        }

        if (playerDirection == "w" && key == "d") {
            playerBufferedImage = rotateClockwise90(playerBufferedImage);
            playerWidth = 70;
            playerHeight = 50;
        } else if (playerDirection == "w" && key == "s") {
            playerBufferedImage = rotateClockwise90(playerBufferedImage);
            playerBufferedImage = rotateClockwise90(playerBufferedImage);
            playerWidth = 50;
            playerHeight = 70;
        } else if (playerDirection == "w" && key == "a") {
            playerBufferedImage = rotateClockwise90(playerBufferedImage);
            playerBufferedImage = rotateClockwise90(playerBufferedImage);
            playerBufferedImage = rotateClockwise90(playerBufferedImage);
            playerWidth = 70;
            playerHeight = 50;
        }
    }

    public void attachMouseListener() {
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                movePlayer(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                movePlayer(e);
            }
        });
    }

    public void movePlayer(MouseEvent event) {
        // System.out.println(event.getX() + "," + event.getY());
        if (event.getX() <= 700 && event.getX() >= 40) {
            playerX = event.getX();
        }
        if (event.getY() <= 450 && event.getY() >= 50) {
            playerY = event.getY();
        }

    }

    /**
     * this method starts the game
     **/
    public void run() {
        while (!gameOver) {
//            System.out.println(circleX + "," + circleY + "," + rectX + "," + rectY);
            // stops circle leaving rectangle top & bottom
            if (circleY <= 450 && !reachedBottom) {
                circleY = circleY + speedY;
                if (circleY >= 450) {
                    reachedBottom = true;
                }
            } else if (circleY > 50) {
                circleY = circleY - speedY;
                circleX = circleX + 1;

                if (circleY <= 50) {
                    // This will execute when the ball reaches the bottom
                    // speedY = randomObject.nextInt(2, 3);
                    reachedBottom = false;
                    circleY = 50;
                }
            }
            // stops circle leaving rectangle left & right
            if (circleX <= 40) {
                reachedLeft = true;
            } else if (circleX >= 700) {
                reachedLeft = false;
            }
            if (reachedLeft) {
                circleX = circleX + speedY;
            } else {
                circleX = circleX - speedY;
            }

            // System.out.println(speedY + " " + circleY);

            repaint();

            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        gameOver = enemyCircle.intersects(playerX, playerY, 50, 50);
        setBackground(Color.LIGHT_GRAY);
        g.fillRect(30, 40, 720, 10); // T
        g.fillRect(30, 500, 720, 10); // B
        g.fillRect(30, 40, 10, 470); // L
        g.fillRect(750, 40, 10, 470); // R


        if (gameOver) {
            gameWindowObject.playerDied();
        } else {
            g.setColor(Color.red);
            enemyCircle.setFrame(circleX, circleY, 50, 50);
            g.drawImage(blueBallBufferedImage, circleX, circleY, 50, 50, null);

            g.setColor(Color.GREEN);
            playerRectangle.setBounds(playerX, playerY, playerWidth, playerHeight);
            g.drawImage(playerBufferedImage, playerX, playerY, playerWidth, playerHeight, null);

            g.setColor(Color.red);
            g.drawRect(playerX, playerY, playerWidth, playerHeight);

            g.setColor(Color.blue);
            g.drawRect(appleRectangle.x, appleRectangle.y, appleRectangle.width, appleRectangle.height);
            appleRectangle.setBounds(appleX, appleY, appleWidth, appleHeight);
            g.drawImage(appleBufferedImage, appleX, appleY, appleWidth, appleHeight, null);

            if (appleRectangle.intersects(playerRectangle)) {

                appleX = randomObject.nextInt(50, 700);
                appleY = randomObject.nextInt(50, 400);
            }
        }


//        Graphics2D newVariable;
//        newVariable = (Graphics2D) g;
//        newVariable.setStroke(new BasicStroke(5));
//        g.drawLine(50, 500, circleX + 25, circleY + 25);
    }

    public static BufferedImage rotateClockwise90(BufferedImage src) {
        int width = src.getWidth();
        int height = src.getHeight();

        BufferedImage dest = new BufferedImage(height, width, src.getType());

        Graphics2D graphics2D = dest.createGraphics();
        graphics2D.translate((height - width) / 2, (height - width) / 2);
        graphics2D.rotate(Math.PI / 2, height / 2, width / 2);
        graphics2D.drawRenderedImage(src, null);

        return dest;
    }
}








