package graphics;

import Level.Block;
import Level.Entity.Mobile.Player;
import Level.Level;
import java.awt.event.KeyEvent;
import virucide.Virucide;

/**
 *
 * @author j0ker
 */
public class game {

    public int time;
    public static Player controls;
    public Level l;
    public static int numCreatures = 0;

    public game() {
        controls = new Player();
        l = new Level(Virucide.gridWidth, Virucide.gridWidth);
    }

    public void newMap(Block[] b) {
        l.customMap(b);
    }

    public void newMap() {
        l = new Level(Virucide.gridWidth, Virucide.gridWidth);
    }

    public void tick(boolean[] key) {
        time++;
        boolean forward = key[KeyEvent.VK_W];
        boolean back = key[KeyEvent.VK_S];
        boolean left = key[KeyEvent.VK_A];
        boolean right = key[KeyEvent.VK_D];
        boolean jump = key[KeyEvent.VK_SPACE];
        boolean crouch = key[KeyEvent.VK_X];
        boolean run = key[KeyEvent.VK_SHIFT];
        controls.tick(forward, back, left, right, jump, crouch, run);
    }

    public static void addCreature() {
        numCreatures++;
    }

    public static int getNumofCreatures() {
        return numCreatures;
    }
}