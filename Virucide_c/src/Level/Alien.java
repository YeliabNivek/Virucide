package Level;

import graphics.Render3D;
import textures.black_bottom;
import textures.black_top;

/**
 *
 * @author j0ker
 */
public class Alien extends Creature {

    public Alien(int x, int z) {
        super(x, z);
        top = black_top.floor.pixels;
        bottom = black_bottom.floor.pixels;
    }

    @Override
    public void act() {
        Render3D.me.minusHeart();
    }
}