package Level.Entity.Mobile;

import Level.Entity.Entity;

/**
 *
 * @author j0ker
 */
public class Mobile extends Entity {

    public void move(int xa, int za, double rot) {
        if (xa != 0 && za != 0) {
            move(xa, 0, rot);
            move(0, za, rot);
            return;
        }
        double nx = xa * Math.cos(rot) + za * Math.sin(rot);
        double nz = za * Math.cos(rot) - xa * Math.sin(rot);
        x += nx;
        z += nz;
    }
}