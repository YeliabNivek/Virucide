package Level;

import textures.cup;
import virucide.Virucide;
import virucide.run;

/**
 *
 * @author j0ker
 */
public class Cup extends Item {

    public Cup(int xx, int zz) {
        super(xx, zz); //not necessary, java automatically calls super with no parameters
        pic = cup.floor.pixels;
    }

    @Override
    public void act() {
        //this ends the game so no need to waste time and remove it from the grid
        Virucide.win = true;
        Virucide.frame.getContentPane().setCursor(null);
        if (run.multiplayer) {
            Virucide.connector.send("/win");
            Virucide.connector.send(Virucide.username);
        }
    }
}