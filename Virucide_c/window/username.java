package window;

import graphics.Texture;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import javax.imageio.ImageIO;
import virucide.Virucide;

/**
 *
 * @author j0ker
 */
public class username {

    BufferStrategy bs;
    static String s = "";
    static int cc = 0, ccc = 0;

    public username(BufferStrategy b) {
        bs = b;
    }

    public void render() {
        Graphics g = bs.getDrawGraphics();
        g.setFont(new Font("Lucida Console", 0, 22));
        g.setColor(Color.WHITE);
        try {
            g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/user.png")), 0, 0, 800, 600, null);
        } catch (IOException ex) {
        }
        g.drawString(s + blink(), 75, 305);
        g.dispose();
        bs.show();
    }

    public static String blink() {
        if (cc < 20) {
            cc++;
            return "_";
        } else {
            if (ccc < 20) {
                ccc++;
                return " ";
            } else {
                cc = 0;
                ccc = 0;
                return "_";
            }
        }
    }

    public void enter() {
        Virucide.username = s;
        Virucide.gotuser = true;
    }

    public void backSpace() {
        try {
            s = s.substring(0, s.length() - 1);
        } catch (Exception e) {
        }
    }

    public void addChar(char c) {
        if (s.length() < 49) {
            s = s + c;
        }
    }
}