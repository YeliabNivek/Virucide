package window;

import graphics.Render3D;
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
public class win {

    BufferStrategy bs;

    public win(BufferStrategy b) {
        bs = b;
    }

    public void render() {
        Graphics g = bs.getDrawGraphics();
        try {
            g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/win.png")), 0, 0, 800, 600, null);
            if (InputHandler.mousex > 100 && InputHandler.mousex < 100 + 160 && InputHandler.mousey > 400 && InputHandler.mousey < 400 + 60) { //this btw is an amazing mouse collision algorithum, never fails
                g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/quit_on_p.png")), 100, 400, 160, 60, null);
                g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/again_off.png")), 400, 400, 240, 60, null);
                if (InputHandler.mousebutton == 1) {
                    if (run.multiplayer) {
                        Virucide.connector.send("/quit");
                        Virucide.connector.send(Virucide.username);
                    }
                    System.exit(0);
                }
            } else {
                g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/quit_off_p.png")), 100, 400, 160, 60, null);
                if (InputHandler.mousex > 400 && InputHandler.mousex < 400 + 240 && InputHandler.mousey > 400 && InputHandler.mousey < 400 + 60) {
                    g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/again_on.png")), 400, 400, 240, 60, null);
                    if (InputHandler.mousebutton == 1) {
                        BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
                        Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blank");
                        Virucide.frame.getContentPane().setCursor(blank);
                        Virucide.game.newMap();
                        Virucide.game.controls.reset();
                        Virucide.win = false;
                        Render3D.me.resethearts();
                    }
                } else {
                    g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/again_off.png")), 400, 400, 240, 60, null);
                }
            }
        } catch (IOException ex) {
        }
        g.dispose();
        bs.show();
    }
}