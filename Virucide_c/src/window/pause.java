package window;

import graphics.Texture;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import userland.InputHandler;
import virucide.Virucide;
import virucide.run;

/**
 *
 * @author j0ker
 */
public class pause {

    BufferStrategy bs;

    public pause(BufferStrategy b) {
        bs = b;
    }

    public void render() {
        Graphics g = bs.getDrawGraphics();
        try {
            g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/paused.png")), 0, 0, 800, 600, null);
            if (InputHandler.mousex > 100 && InputHandler.mousex < 100 + 160 && InputHandler.mousey > 400 && InputHandler.mousey < 400 + 60) {
                g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/quit_on_p.png")), 100, 400, 160, 60, null);
                g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/back_off.png")), 400, 400, 300, 60, null);
                if (InputHandler.mousebutton == 1) {
                    if (run.multiplayer) {
                        Virucide.connector.send("/quit");
                        Virucide.connector.send(Virucide.username);
                    }
                    System.exit(0);
                }
            } else {
                g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/quit_off_p.png")), 100, 400, 160, 60, null);
                if (InputHandler.mousex > 400 && InputHandler.mousex < 400 + 300 && InputHandler.mousey > 400 && InputHandler.mousey < 400 + 60) {
                    g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/back_on.png")), 400, 400, 300, 60, null);
                    if (InputHandler.mousebutton == 1) {
                        BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
                        Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blank");
                        Virucide.frame.getContentPane().setCursor(blank);
                        Virucide.pause = false;
                    }
                } else {
                    g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/back_off.png")), 400, 400, 300, 60, null);
                }
            }
        } catch (IOException ex) {
        }
        g.dispose();
        bs.show();
    }
}