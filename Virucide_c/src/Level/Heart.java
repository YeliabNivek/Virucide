package Level;

import graphics.Render3D;
import textures.heart;
import virucide.Virucide;

/**
 *
 * @author j0ker
 */
public class Heart extends Item {

    public Heart(int xx, int zz) {
        super(xx, zz);
        pic = heart.floor.pixels;
    }

    @Override
    public void act() {
        Render3D.me.addHeart();
        Virucide.game.l.remove((int) x, (int) z);
    }
}