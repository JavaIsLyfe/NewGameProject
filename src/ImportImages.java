import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class ImportImages {

    Random fruitGeneration = new Random();
    int fruitGenerated;
    BufferedImage blueBallBufferedImage;
    BufferedImage redBallBufferedImage;
    private BufferedImage[] fruitCollection = new BufferedImage[7];

    /* constructor of the class ImportImages */
    public ImportImages() {
        URL bananaURL = getClass().getResource("fruitCollection/banana1.png");
        URL blackberryURL = getClass().getResource("fruitCollection/blackberry.png");
        URL cherryURL = getClass().getResource("fruitCollection/cherry.png");
        URL coconutURL = getClass().getResource("fruitCollection/coconut.png");
        URL grapeURL = getClass().getResource("fruitCollection/grape.png");
        URL pineappleURL = getClass().getResource("fruitCollection/pineapple.png");
        URL strawberryURL = getClass().getResource("fruitCollection/strawberry.png");
        URL blueBall = getClass().getResource("BlueBallSpriteTest.PNG");
        URL redBall = getClass().getResource("redballsprite.png");
        try {
            //extraFruits = ImageIO.read(blackberryURL);
            fruitCollection[0] = ImageIO.read(bananaURL);
            fruitCollection[1] = ImageIO.read(blackberryURL);
            fruitCollection[2] = ImageIO.read(cherryURL);
            fruitCollection[3] = ImageIO.read(coconutURL);
            fruitCollection[4] = ImageIO.read(grapeURL);
            fruitCollection[5] = ImageIO.read(pineappleURL);
            fruitCollection[6] = ImageIO.read(strawberryURL);
            fruitGenerated = fruitGeneration.nextInt(0, 7);
            blueBallBufferedImage = ImageIO.read(blueBall);
            redBallBufferedImage = ImageIO.read(redBall);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BufferedImage getFruit() {
        return fruitCollection[fruitGenerated];
    }

    public void fruitGenerator() {
        fruitGenerated = fruitGeneration.nextInt(0, 7);
    }

}