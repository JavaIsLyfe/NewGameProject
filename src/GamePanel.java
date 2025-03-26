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
    boolean gameOver;
    boolean isPlayerShooting;
    boolean isBulletInFrame;
    boolean fruitCollision;
    String playerDirection = "a";
    String bulletDirection = playerDirection;

    Rectangle playerRectangle;
    Ellipse2D enemyCircle;
    Ellipse2D bulletCircle;

    Rectangle appleRectangle;
    Random randomObject = new Random();

    GameWindow gameWindowObject;
    BufferedImage playerBufferedImage;
    ImportImages imageImporter;
    Enemy enemyObject;
    Enemy enemyObject1;
    Enemy[] enemyList = new Enemy[2];

    // this is a constructor of the class
    GamePanel(GameWindow passAGameWindowObject) {
        setFocusable(true);
        requestFocus();
        gameWindowObject = passAGameWindowObject;
        playerRectangle = new Rectangle();
        enemyCircle = new Ellipse2D.Float();
        appleRectangle = new Rectangle();
        bulletCircle = new Ellipse2D.Float(); // Ellipse2D object

        imageImporter = new ImportImages(); // OBJ
        enemyObject = new Enemy(50,100);
        enemyObject1 = new Enemy(300,500);
        enemyList[0] = enemyObject;
        enemyList[1] = enemyObject1;

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

        URL player = getClass().getResource("player.PNG");
        try {
            playerBufferedImage = ImageIO.read(player);
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
            enemyObject.move();
            enemyObject1.move();
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
        fruitCollision = enemyCircle.intersects(appleX, appleY, fruitWidth, fruitHeight);
        if (fruitCollision) {
            fruitRandomizer(false);
        }
        setBackground(Color.LIGHT_GRAY);
        g.fillRect(topWallX, topWallY, 720, 10); // T
        g.fillRect(bottomWallX, bottomWallY, 720, 10); // B
        g.fillRect(leftWallX, leftWallY, 10, 470); // L
        g.fillRect(rightWallX, rightWallY, 10, 470); // Right


        if (gameOver) {
            gameWindowObject.playerDied();
        } else {
            g.setColor(Color.red);

            for (int i = 0; i < 2; i++){
                enemyList[i].draw(bulletCircle,g);
            }
            //enemyObject.draw(bulletCircle,g);
            enemyCircle.setFrame(enemyObject.getX(), enemyObject.getY(), 50, 50);

            g.setColor(Color.red);
            //  g.drawRect(playerX, playerY, playerWidth, playerHeight);

            g.setColor(Color.blue);
            //   g.drawRect(appleRectangle.x, appleRectangle.y, appleRectangle.width, appleRectangle.height);
            appleRectangle.setBounds(appleX, appleY, fruitWidth, fruitHeight);
            g.drawImage(imageImporter.getFruit(), appleX, appleY, fruitWidth, fruitHeight, null);
            //    g.drawImage(appleBufferedImage,100,100, fruitWidth, fruitHeight,null);


            g.setColor(Color.BLACK);
            bulletCircle.setFrame(bulletX, bulletY, 15, 15);

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
            if (appleRectangle.intersects(playerRectangle))
                fruitRandomizer(true);


            g.setColor(Color.BLACK);
            g.drawString("Score = " + score, 50, 20);
        }


//        Graphics2D newVariable;
//        newVariable = (Graphics2D) g;
//        newVariable.setStroke(new BasicStroke(5));
//        g.drawLine(50, 500, circleX + 25, circleY + 25);
    }

    public void fruitRandomizer(boolean scoreUpdate) {
        appleX = randomObject.nextInt(50, 700);
        appleY = randomObject.nextInt(50, 400);
        imageImporter.fruitGenerator();
        if (scoreUpdate) score++;
    }

}








