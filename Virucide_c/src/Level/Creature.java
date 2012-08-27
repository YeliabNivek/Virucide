package Level;

import graphics.game;
import java.util.Random;
import virucide.Virucide;

/**
 *
 * @author j0ker
 */
public class Creature extends Block {

    /* Creatures must have a move method, their act method must NOT remove them from the grid */
    int direction, offset, c = 0, cc = 0;
    Random r = new Random();
    double speed = 0.1;

    public Creature(int ex, int zed /*
             * read the variables aloud
             */) {
        alive = true;
        x = ex;
        z = zed;
        genNewDirection();
        game.addCreature();
        id = game.getNumofCreatures();
    }

    @Override
    public void move() {
        switch (direction) {
            case 0: //right
                if (!Virucide.game.l.getBlock((int) (x + speed), (int) z).solid && !Virucide.game.l.getBlock((int) (x + speed), (int) z).item) {
                    if (!Virucide.game.l.getBlock((int) (x + speed), (int) z).alive || Virucide.game.l.getBlock((int) (x + speed), (int) z).id == id) {
                        Virucide.game.l.remove((int) x, (int) z);
                        Virucide.game.l.addCreature((int) (x + speed), (int) z, this);
                        x += speed;
                    } else {
                        genNewDirection();
                    }
                } else {
                    genNewDirection();
                }
                break;
            case 1: //left
                if (!Virucide.game.l.getBlock((int) (x - speed), (int) z).solid && !Virucide.game.l.getBlock((int) (x - speed), (int) z).item) {
                    if (!Virucide.game.l.getBlock((int) (x - speed), (int) z).alive || Virucide.game.l.getBlock((int) (x - speed), (int) z).id == id) {
                        Virucide.game.l.remove((int) x, (int) z);
                        Virucide.game.l.addCreature((int) (x - speed), (int) z, this);
                        x -= speed;
                    } else {
                        genNewDirection();
                    }
                } else {
                    genNewDirection();
                }
                break;
            case 2: //forward
                if (!Virucide.game.l.getBlock((int) x, (int) (z + speed)).solid && !Virucide.game.l.getBlock((int) x, (int) (z + speed)).item) {
                    if (!Virucide.game.l.getBlock((int) x, (int) (z + speed)).alive || Virucide.game.l.getBlock((int) x, (int) (z + speed)).id == id) {
                        Virucide.game.l.remove((int) x, (int) z);
                        Virucide.game.l.addCreature((int) x, (int) (z + speed), this);
                        z += speed;
                    } else {
                        genNewDirection();
                    }
                } else {
                    genNewDirection();
                }
                break;
            case 3: //backwards
                if (!Virucide.game.l.getBlock((int) x, (int) (z - speed)).solid && !Virucide.game.l.getBlock((int) x, (int) (z - speed)).item) {
                    if (!Virucide.game.l.getBlock((int) x, (int) (z - speed)).alive || Virucide.game.l.getBlock((int) x, (int) (z - speed)).id == id) {
                        Virucide.game.l.remove((int) x, (int) z);
                        Virucide.game.l.addCreature((int) x, (int) (z - speed), this);
                        z -= speed;
                    } else {
                        genNewDirection();
                    }
                } else {
                    genNewDirection();
                }
                break;
            default: //will never happen
        }
        offset--;
        if (offset < 0) {
            genNewDirection();
        }
    }

    public void genNewDirection() {
        direction = r.nextInt(4);
        offset = r.nextInt(Virucide.gridWidth * 5);
    }
}