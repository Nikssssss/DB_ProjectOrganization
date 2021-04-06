package common;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private static volatile MainWindow mainWindow;

    public static void createWindow() {
        if (mainWindow == null) {
            synchronized (MainWindow.class) {
                if (mainWindow == null) {
                    mainWindow = new MainWindow();
                    mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    mainWindow.setSize(400, 400);
                    mainWindow.centerWindow();
                    mainWindow.setVisible(true);
                }
            }
        }
    }

    public static MainWindow getMainWindow() {
        return mainWindow;
    }

    public void showPanel(JPanel panel){
        mainWindow.getContentPane().removeAll();
        mainWindow.getContentPane().add(panel);
        mainWindow.pack();
        mainWindow.centerWindow();
    }

    //MARK: private methods

    private MainWindow(){
        super("Проектная организация");
    }

    private void centerWindow() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
    }

}
