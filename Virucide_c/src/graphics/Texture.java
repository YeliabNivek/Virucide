package graphics;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 *
 * @author j0ker
 */
public class Texture {

    public static Render floor = loadBitmap("/res/tile.png");

    public static Render loadBitmap(String filename) {
        try {
            BufferedImage image = ImageIO.read(Texture.class.getResource(filename));
            int w = image.getWidth();
            int h = image.getHeight();
            Render result = new Render(w, h);
            image.getRGB(0, 0, w, h, result.pixels, 0, w);
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}