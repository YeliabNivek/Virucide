package virucide;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import networking.connectToServer;
import userland.InputHandler;

/**
 *
 * @author j0ker
 */
public class run {

    public static boolean multiplayer;

    public run(String ip, boolean multi) {
        BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blank");
        Virucide.frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                if (multiplayer) {
                    Virucide.connector.send("/quit");
                    Virucide.connector.send(Virucide.username);
                }
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        try {
            Robot r = new Robot();
            r.mouseMove((dim.width) / 2, (dim.height) / 2);
        } catch (AWTException ex) {
        }
        Virucide.frame.getContentPane().setCursor(blank);
        multiplayer = multi;
        if (multi) {
            connectToServer connectToServer = new connectToServer(ip);
            connectToServer.start();
        }
        InputHandler.init(Virucide.frame);
        Virucide.menu = false;
    }
}