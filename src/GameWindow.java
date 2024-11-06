import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameWindow extends JFrame {
    int frameW = 800;
    int frameH = 600;
    GameWindow gameWindowVariable = this;
    JPanel gameOverPanel;
    GamePanel gamePanelObject;
    Thread newThreadObject = null;

    GameWindow() {
        gameOverPanel = new JPanel();
        JLabel gameOverText = new JLabel();

        // Set the Boxayout to be Y_AXIS from top to down
        gameOverText.setForeground(Color.red);
        gameOverText.setFont(new Font("arial", Font.BOLD, 100));
        gameOverText.setText("GAME OVER");
        gameOverPanel.setBackground(Color.BLACK);
        gameOverPanel.add(gameOverText);
        createGameOverPanel();

        // methods of class JFrame
        setSize(frameW, frameH);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void createGameOverPanel() {
        JButton restartButtonObject = new JButton();
        //restartButtonObject.setFont(font1);
        restartButtonObject.setPreferredSize(new Dimension(10, 10));
        restartButtonObject.setText("RESTART");
        restartButtonObject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GamePanel gamePanelVariable = new GamePanel(gameWindowVariable);
                replacePanel(gamePanelVariable);


                newThreadObject = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            newThreadObject.sleep(500);
                            gamePanelVariable.requestFocus();
                            gamePanelVariable.run();
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });

                newThreadObject.start();
            }
        });
        gameOverPanel.add(restartButtonObject);
        gameOverPanel.setBorder(new EmptyBorder(new Insets(200, 50, 100, 50)));
        gameOverPanel.setLayout(new BoxLayout(gameOverPanel, BoxLayout.Y_AXIS));
    }

    public void startGame() {
        System.out.println("started");
        gamePanelObject = new GamePanel(gameWindowVariable);
        replacePanel(gamePanelObject);
        gamePanelObject.requestFocus();
        gamePanelObject.run();
    }

    public void replacePanel(JPanel passAJPanelObject) {
        getContentPane().removeAll();
        getContentPane().repaint();
        add(passAJPanelObject);
        getContentPane().repaint();
        getContentPane().revalidate();
    }

    public void playerDied() {
        replacePanel(gameOverPanel);
    }

    public static void main(String[] args) {
        GameWindow newObject = new GameWindow();
        newObject.startGame();
    }
}