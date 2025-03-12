import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;

public class GamePanel extends JPanel {

    int speedY = 2;
    int enemyY = 100;
    int enemyX = 50;
    int playerY = 256;
    int playerX = 375;
    int playerWidth = 70;
    int playerHeight = 50;
    int appleY = 100;
    int appleX = 100;
    int fruitWidth = 30;
    int fruitHeight = 30;
    int rotationAngle;
    int bulletX = -100;
    int bulletY = -100;
    int topWallX = 30;
    int bottomWallX = 30;
    int leftWallY = 40;
    int rightWallY = 40;
    int topWallY = 40;
    int bottomWallY = 500;
    int leftWallX = 30;
    int rightWallX = 750;
    int bulletSpeed = 5;
    int score;

    boolean isEnemyStunned;
    boolean gameOver;
    boolean reachedBottom = false;
    boolean isPlayerShooting;
    boolean reachedLeft = false;
    boolean isBulletInFrame;
    String playerDirection = "a";
    String bulletDirection = playerDirection;

    Rectangle playerRectangle;
    Ellipse2D enemyCircle;
    Ellipse2D bulletCircle;
    Rectangle appleRectangle;
    Random randomObject = new Random();

    GameWindow gameWindowObject;
    BufferedImage appleBufferedImage;
    BufferedImage blueBallBufferedImage;
    BufferedImage playerBufferedImage;
    ImportImages imageImporter;
    GameTimer timerThread;

    // this is a constructor of the class
    GamePanel(GameWindow passAGameWindowObject) {
        setFocusable(true);
        requestFocus();
        gameWindowObject = passAGameWindowObject;
        playerRectangle = new Rectangle();
        enemyCircle = new Ellipse2D.Float();
        appleRectangle = new Rectangle();
        bulletCircle = new Ellipse2D.Float();
        imageImporter = new ImportImages(); // OBJ


        attachKeyListener();
        attachMouseListener();
        importFruit();
    }

    public static BufferedImage rotateClockwise90(BufferedImage src, int angle) {
        int width = src.getWidth();
        int height = src.getHeight();
        BufferedImage dest;

        if (angle == 90 || angle == 270) {
            dest = new BufferedImage(height, width, src.getType());
        } else {
            dest = new BufferedImage(width, height, src.getType());
        }

        Graphics2D graphics2D = dest.createGraphics();

        switch (angle) {
            case 90:
                graphics2D.translate(height, 0);
                graphics2D.rotate(Math.PI / 2);
                break;
            case 180:
                graphics2D.translate(width, height);
                graphics2D.rotate(Math.PI);
                break;
            case 270:
                graphics2D.translate(0, width);
                graphics2D.rotate(3 * Math.PI / 2);
                break;
            case 0:
            default:
                graphics2D.drawRenderedImage(src, null);
                return src;
        }

        graphics2D.drawRenderedImage(src, null);
        graphics2D.dispose();
        return dest;
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

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W: // W key
                        rotationAngle = 0;
                        if (playerDirection == "a")
                            playerBufferedImage = rotateClockwise90(playerBufferedImage, 90);
                        else if (playerDirection == "d")
                            playerBufferedImage = rotateClockwise90(playerBufferedImage, 270);
                        else if (playerDirection == "s")
                            playerBufferedImage = rotateClockwise90(playerBufferedImage, 180);

                        playerHeight = 70;
                        playerWidth = 50;
                        playerDirection = "w";
                        break;

                    case KeyEvent.VK_A: // A key
                        rotationAngle = 270;
                        if (playerDirection == "s")
                            playerBufferedImage = rotateClockwise90(playerBufferedImage, 90);
                        else if (playerDirection == "w")
                            playerBufferedImage = rotateClockwise90(playerBufferedImage, 270);
                        else if (playerDirection == "d")
                            playerBufferedImage = rotateClockwise90(playerBufferedImage, 180);
                        playerHeight = 50;
                        playerWidth = 70;
                        playerDirection = "a";
                        break;
                    case KeyEvent.VK_S: // S key
                        rotationAngle = 180;
                        if (playerDirection == "d")
                            playerBufferedImage = rotateClockwise90(playerBufferedImage, 90);
                        else if (playerDirection == "a")
                            playerBufferedImage = rotateClockwise90(playerBufferedImage, 270);
                        else if (playerDirection == "w")
                            playerBufferedImage = rotateClockwise90(playerBufferedImage, 180);
                        playerHeight = 70;
                        playerWidth = 50;
                        playerDirection = "s";
                        break;
                    case KeyEvent.VK_D: // D key
                        rotationAngle = 90;
                        if (playerDirection == "w")
                            playerBufferedImage = rotateClockwise90(playerBufferedImage, 90);
                        else if (playerDirection == "s")
                            playerBufferedImage = rotateClockwise90(playerBufferedImage, 270);
                        else if (playerDirection == "a")
                            playerBufferedImage = rotateClockwise90(playerBufferedImage, 180);

                        playerDirection = "d";
                        playerHeight = 50;
                        playerWidth = 70;
                        break;

                }

            }
        });
    }

    public void rotatePlayer(String key) {
        System.out.println(key);

        if (Objects.equals(key, "w")) {
            //     playerBufferedImage =
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

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
// this is the starting point of the bullet
                if (!isBulletInFrame) {
                    isPlayerShooting = true;
                    bulletDirection = playerDirection;
                    if (playerDirection == "a") {
                        bulletX = playerX + playerWidth / 2;
                        bulletY = playerY + playerHeight / 2;
                    } else if (playerDirection == "w") {
                        bulletX = playerX + playerWidth / 2 - 12;
                        bulletY = playerY + playerHeight / 2;
                    } else if (playerDirection == "d") {
                        bulletX = playerX + playerWidth / 2;
                        bulletY = playerY + playerHeight / 2 - 12;
                    } else {
                        bulletX = playerX + playerWidth / 2 + 5;
                        bulletY = playerY + playerHeight / 2;
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

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
            if (!isEnemyStunned) {
                if (enemyY <= 450 && !reachedBottom) {
                    enemyY = enemyY + speedY;
                    if (enemyY >= 450) {
                        reachedBottom = true;
                    }
                } else if (enemyY > 50) {
                    enemyY = enemyY - speedY;
                    enemyX = enemyX + 1;

                    if (enemyY <= 50) {
                        // This will execute when the ball reaches the bottom
                        // speedY = randomObject.nextInt(2, 3);
                        reachedBottom = false;
                        enemyY = 50;
                    }
                }
                // stops circle leaving rectangle left & right
                if (enemyX <= 40) {
                    reachedLeft = true;
                } else if (enemyX >= 700) {
                    reachedLeft = false;
                }
                if (reachedLeft) {
                    enemyX = enemyX + speedY;
                } else {
                    enemyX = enemyX - speedY;
                }
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
        g.fillRect(topWallX, topWallY, 720, 10); // T
        g.fillRect(bottomWallX, bottomWallY, 720, 10); // B
        g.fillRect(leftWallX, leftWallY, 10, 470); // L
        g.fillRect(rightWallX, rightWallY, 10, 470); // Right


        if (gameOver) {
            gameWindowObject.playerDied();
        } else {
            g.setColor(Color.red);
            enemyCircle.setFrame(enemyX, enemyY, 50, 50);
            g.drawImage(blueBallBufferedImage, enemyX, enemyY, 50, 50, null);

            g.setColor(Color.red);
          //  g.drawRect(playerX, playerY, playerWidth, playerHeight);

            g.setColor(Color.blue);
         //   g.drawRect(appleRectangle.x, appleRectangle.y, appleRectangle.width, appleRectangle.height);
            appleRectangle.setBounds(appleX, appleY, fruitWidth, fruitHeight);
            g.drawImage(imageImporter.getFruit(), appleX, appleY, fruitWidth, fruitHeight, null);
        //    g.drawImage(appleBufferedImage,100,100, fruitWidth, fruitHeight,null);



            g.setColor(Color.BLACK);
            bulletCircle.setFrame(bulletX, bulletY, 15, 15);
            if (!isEnemyStunned) {
                isEnemyStunned = bulletCircle.intersects(enemyX, enemyY, 50, 50);
                if (isEnemyStunned) {
                    timerThread = new GameTimer(3, new GameTimer.timerListener() {
                        @Override
                        public void timerFinished() {
                            System.out.println("finished");
                            isEnemyStunned = false;
                        }
                    });
                }
            }

            //  System.out.println(isEnemyStunned);
            g.fillOval(bulletX, bulletY, 15, 15);

            if (isPlayerShooting) {
                isBulletInFrame = bulletX > topWallX && bulletX < rightWallX
                        && bulletY > topWallY && bulletY < bottomWallY;

                if (bulletDirection == "a") {
                    bulletX = bulletX - bulletSpeed;
                } else if (bulletDirection == "d") {
                    bulletX = bulletX + bulletSpeed;
                } else if (bulletDirection == "w") {
                    bulletY = bulletY - bulletSpeed;
                } else {
                    bulletY = bulletY + bulletSpeed;
                }
            }

            /* drawing player here on top of bullet */
            g.setColor(Color.GREEN);
            playerRectangle.setBounds(playerX, playerY, playerWidth, playerHeight);
            g.drawImage(playerBufferedImage, playerX, playerY, playerWidth, playerHeight, null);

            /* this is where we check collision between player and fruit */
            if (appleRectangle.intersects(playerRectangle)) {
                appleX = randomObject.nextInt(50, 700);
                appleY = randomObject.nextInt(50, 400);
                imageImporter.fruitGenerator();
                score++;
            }

            g.setColor(Color.BLACK);
            g.drawString("Score = " + score, 50, 20);
        }


//        Graphics2D newVariable;
//        newVariable = (Graphics2D) g;
//        newVariable.setStroke(new BasicStroke(5));
//        g.drawLine(50, 500, circleX + 25, circleY + 25);
    }

}








