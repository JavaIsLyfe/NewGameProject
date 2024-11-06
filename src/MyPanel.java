import javax.swing.*;
import java.awt.*;

public class MyPanel extends JPanel {

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);

        g.setColor(Color.BLACK);
        g.drawRect(100,100,100,50);
    }
}
