import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel {

    int playerY = 256;
    int playerX = 375;
    static int playerWidth = 70;
    static int playerHeight = 50;
    int fruitY = 100;
    int fruitX = 100;
    static int fruitWidth = 30;
    static int fruitHeight = 30;
    int rotationAngle;
    int score;
    boolean gameOver;
    boolean isPlayerShooting;
    String playerDirection = "a";

    Rectangle playerRectangle;
    Ellipse2D bulletCircle;

    Rectangle appleRectangle;
    Random randomObject = new Random();

    GameWindow gameWindowObject;
    BufferedImage playerBufferedImage;
    ImportImages imageImporter;
    Enemy enemyObj1;
    ArrayList<Enemy> newEnemyList = new ArrayList<>();
    ArrayList<Bullet> bulletList = new ArrayList<>();

    // this is a constructor of the class
    GamePanel(GameWindow passAGameWindowObject) {
        setFocusable(true);
        requestFocus();
        gameWindowObject = passAGameWindowObject;
        playerRectangle = new Rectangle();
        appleRectangle = new Rectangle();
        bulletCircle = new Ellipse2D.Float(); // Ellipse2D object

        imageImporter = new ImportImages(); // OBJ
        enemyObj1 = new Enemy(50, 100, imageImporter.blueBallBufferedImage);
        newEnemyList.add(enemyObj1);

        attachKeyListener();
        attachMouseListener();
        importFruit();
    }

    public static BufferedImage rotateClockwise90(BufferedImage src, int angle) {
        int width = src.getWidth();
        int height = src.getHeight();
        BufferedImage test;

        if (angle == 90 || angle == 270) {
            test = new BufferedImage(height, width, src.getType());
        } else {
            test = new BufferedImage(width, height, src.getType());
        }

        Graphics2D graphics2D = test.createGraphics();

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
        return test;
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
                        if (playerDirection == "a") playerBufferedImage = rotateClockwise90(playerBufferedImage, 90);
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
                        if (playerDirection == "s") playerBufferedImage = rotateClockwise90(playerBufferedImage, 90);
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
                        if (playerDirection == "d") playerBufferedImage = rotateClockwise90(playerBufferedImage, 90);
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
                        if (playerDirection == "w") playerBufferedImage = rotateClockwise90(playerBufferedImage, 90);
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
//                if (!isBulletInFrame) {
//                    isPlayerShooting = true;
//                    bulletDirection = playerDirection;
//                                    }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                isPlayerShooting = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isPlayerShooting = false;
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

            for (Enemy enemy : newEnemyList) {
                enemy.move();
            }
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
        for (int i = 0; i < newEnemyList.size(); i++) {
            gameOver = newEnemyList.get(i).detectPlayerCollision(playerX, playerY);
            if (gameOver) break;
            if (newEnemyList.get(i).detectFruitCollision(fruitX, fruitY)) {
                fruitRandomizer(false);
            }
        }

        setBackground(Color.LIGHT_GRAY);
        g.fillRect(Constant.TOP_WALL_X, Constant.TOP_WALL_Y, 720, 10); // T
        g.fillRect(Constant.BOTTOM_WALL_X, Constant.BOTTOM_WALL_Y, 720, 10); // B
        g.fillRect(Constant.LEFT_WALL_X, Constant.LEFT_WALL_Y, 10, 470); // L
        g.fillRect(Constant.RIGHT_WALL_X, Constant.RIGHT_WALL_Y, 10, 470); // Right


        if (gameOver) {
            gameWindowObject.playerDied();
        } else {
            g.setColor(Color.red);

            for (Enemy enemy : newEnemyList) {
                enemy.draw(bulletCircle, g);
            }

            g.setColor(Color.red);
            //  g.drawRect(playerX, playerY, playerWidth, playerHeight);

            g.setColor(Color.blue);
            appleRectangle.setBounds(fruitX, fruitY, fruitWidth, fruitHeight);
            g.drawImage(imageImporter.getFruit(), fruitX, fruitY, fruitWidth, fruitHeight, null);

            // bullet is drawn
            g.setColor(Color.BLACK);
            // bulletCircle.setFrame(bulletX, bulletY, 15, 15);

            for (int i = 1; i < bulletList.size();) {
                Bullet bullet = bulletList.get(i);
                if (!bullet.isInFrame()) {
                    bulletList.remove(i); // Don't increment i, because list shrinks
                } else {
                    bullet.shoot(playerDirection);
                    bullet.draw(g);
                    i++; // Only increment if nothing was removed
                }
            }
            if (isPlayerShooting && bulletList.size() < 10) {
                Bullet bullet1 = new Bullet(playerX, playerY,playerDirection);
                bulletList.add(bullet1);


            }

            /* drawing player here on top of bullet */
            g.setColor(Color.GREEN);
            playerRectangle.setBounds(playerX, playerY, playerWidth, playerHeight);
            g.drawImage(playerBufferedImage, playerX, playerY, playerWidth, playerHeight, null);

            /* this is where we check collision between player and fruit */
            if (appleRectangle.intersects(playerRectangle)) fruitRandomizer(true);


            g.setColor(Color.BLACK);
            g.drawString("Score = " + score, 50, 20);
        }


//        Graphics2D newVariable;
//        newVariable = (Graphics2D) g;
//        newVariable.setStroke(new BasicStroke(5));
//        g.drawLine(50, 500, circleX + 25, circleY + 25);
    }

    public void fruitRandomizer(boolean scoreUpdate) {
        fruitX = randomObject.nextInt(50, 700);
        fruitY = randomObject.nextInt(50, 400);
        imageImporter.fruitGenerator();
        if (scoreUpdate) score++;
        if (newEnemyList.size() < 2 && score > 15) addNewEnemy();


    }

    public void addNewEnemy() {
        newEnemyList.add(new Enemy(enemyObj1.getX(), enemyObj1.getY(), imageImporter.redBallBufferedImage));
    } // TODO randomise second enemy X + Y

}








