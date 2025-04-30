import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class Enemy {

    private int enemyY;
    private int enemyX;
    int speedY = 2;
    int timerVariable = 0;
    boolean reachedBottom = false;
    boolean reachedLeft = false;
    boolean isEnemyStunned;
    boolean flag;
    Timer timer;
    Ellipse2D collisionBox;
    GameTimer timerThread;
    BufferedImage redEnemyImage;


    public Enemy(int x, int y, BufferedImage redEnemyImage) {
        enemyX = x;
        enemyY = y;
        this.redEnemyImage = redEnemyImage;
        collisionBox = new Ellipse2D.Float();
    }

    public void move() {
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
    }

    public void draw(Graphics g) {
        g.drawImage(redEnemyImage, enemyX, enemyY, 50, 50, null);

        if (isEnemyStunned) {
            if (timerVariable > 1000) {
                timerVariable = 0;
                isEnemyStunned = false;
            }

            timerVariable++;
//            System.out.println(timerVariable);
        }
    }


    public boolean detectPlayerCollision(int playerX, int playerY) {
        collisionBox.setFrame(enemyX, enemyY, 50, 50);
        return collisionBox.intersects(playerX, playerY, GamePanel.playerWidth, GamePanel.playerHeight);
    }

    public boolean detectFruitCollision(int fruitX, int fruitY) {
        collisionBox.setFrame(enemyX, enemyY, 50, 50);
        return collisionBox.intersects(fruitX, fruitY, GamePanel.fruitWidth, GamePanel.fruitHeight);
    }

    public int getX() {
        return enemyX;
    }

    public int getY() {
        return enemyY;
    }

}
