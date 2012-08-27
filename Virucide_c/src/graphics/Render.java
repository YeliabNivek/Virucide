package graphics;

import virucide.Virucide;

/**
 *
 * @author j0ker
 */
public class Render {

    public final int w;
    public final int h;
    public final int[] pixels;

    public Render(int width, int height) {
        w = width;
        h = height;
        pixels = new int[w * h];
    }

    public void draw(Render r, int xoffset, int yoffset) {
        for (int y = 0; y < r.h; y++) {
            int ypix = y + yoffset;
            if (ypix < 0 || ypix >= Virucide.h) {
                continue;
            }
            for (int x = 0; x < r.w; x++) {
                int xpix = x + xoffset;
                if (xpix < 0 || xpix >= Virucide.w) {
                    continue;
                }

                int aplpha = r.pixels[x + y * r.w];
                if (aplpha > 0) {
                    pixels[xpix + ypix * w] = aplpha;
                }
            }
        }
    }
}