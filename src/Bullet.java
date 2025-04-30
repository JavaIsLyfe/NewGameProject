import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Objects;

public class Bullet {
    int x;
    int y;
    String bulletDirection;
    Ellipse2D bulletHitBox = new Ellipse2D.Float();

    Bullet(int playerX, int playerY, String playerDirection) {
        bulletDirection = playerDirection;
        if (Objects.equals(playerDirection, "a")) {
            x = playerX + GamePanel.playerWidth / 2;
            y = playerY + GamePanel.playerHeight / 2;
        } else if (Objects.equals(playerDirection, "w")) {
            x = playerX + GamePanel.playerWidth / 2 - 12;
            y = playerY + GamePanel.playerHeight / 2;
        } else if (Objects.equals(playerDirection, "d")) {
            x = playerX + GamePanel.playerWidth / 2;
            y = playerY + GamePanel.playerHeight / 2 - 12;
        } else {
            x = playerX + GamePanel.playerWidth / 2 + 5;
            y = playerY + GamePanel.playerHeight / 2;
        }
    }

    public void draw(Graphics g) {
        g.fillOval(x, y, Constant.BULLET_SIZE, Constant.BULLET_SIZE);
    }

    public void shoot(String playerDirection) {
        if (!isInFrame()){
            bulletDirection = playerDirection;
        }

        if (Objects.equals(bulletDirection, "a")) {
            x = x - Constant.BULLET_SPEED;
        } else if (Objects.equals(bulletDirection, "d")) {
            x = x + Constant.BULLET_SPEED;
        } else if (Objects.equals(bulletDirection, "w")) {
            y = y - Constant.BULLET_SPEED;
        } else {
            y = y + Constant.BULLET_SPEED;
        }
    }

    public boolean isInFrame() {
        return x > Constant.TOP_WALL_X && x < Constant.RIGHT_WALL_X && y > Constant.TOP_WALL_Y && y < Constant.BOTTOM_WALL_Y;
    }

    public Enemy checkEnemyCollision(ArrayList<Enemy> enemyList){
        bulletHitBox.setFrame(x,y,Constant.BULLET_SIZE,Constant.BULLET_SIZE);
        for (Enemy enemy : enemyList){
            if (bulletHitBox.intersects(enemy.getX(), enemy.getY(), Constant.ENEMY_SIZE,Constant.ENEMY_SIZE)){
                return enemy;
            }
        }
        return null;
    }
}