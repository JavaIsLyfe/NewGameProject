import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Enemy {

    private int enemyY;
    private int enemyX;
    int speedY = 2;
    boolean reachedBottom = false;
    boolean reachedLeft = false;
    boolean isEnemyStunned;
    GameTimer timerThread;
    ImportImages enemyImage = new ImportImages();

    public Enemy(int x,int y){
        enemyX = x;
        enemyY = y;
    };

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

    public void draw(Ellipse2D bulletCircle, Graphics g){

        g.drawImage(enemyImage.blueBallBufferedImage, enemyX, enemyY, 50, 50, null);
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

    }

    public int getX(){
        return enemyX;
    }

    public int getY(){
        return enemyY;
    }

}
