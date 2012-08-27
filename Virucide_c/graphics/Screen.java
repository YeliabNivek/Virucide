package graphics;

import Level.Entity.Mobile.Player;
import java.util.Random;

/**
 *
 * @author j0ker
 */
public class Screen extends Render {

    private Render r;
    private Render3D r3D;
    boolean first = true;
    int c = 0;

    public Screen(int width, int height) {
        super(width, height);
        r = new Render(256, 256);
        Random rand = new Random();
        r3D = new Render3D(width, height);
        for (int i = 0; i < 256 * 256; i++) {
            r.pixels[i] = rand.nextInt();
        }
    }

    public void render(game g) {
        for (int i = 0; i < w * h; i++) {
            pixels[i] = 0;
        }
        if (c != 100) {
            c++;
        } else {
            c = 0;
        }
        r3D.floor(g);
        r3D.renderDistance();
//        r3D.renderTexture();
        draw(r3D, 0, 0);
    }

    public void setLoc(double x, double z) {
        Player.x = x;
        Player.z = z;
    }
}