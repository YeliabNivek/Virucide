package Level.Entity.Mobile;

import Level.Level;
import graphics.Render3D;
import userland.InputHandler;
import virucide.Virucide;

/**
 *
 * @author j0ker
 */
public class Player extends Mobile {

    public static double y, rotation, xa, za, rotationa;
    public static boolean turnLeft = false;
    public static boolean turnRight = false;
    public static boolean walk = false;
    public static boolean crouchwalk = false;
    public static boolean runwalk = false;
    public static boolean canWalk = true;
    int jumpLen = 0, yWait = 0;
    boolean noJump = false;
    private double DISTANCE = 1.5;

    public void tick(boolean forward, boolean back, boolean left, boolean right, boolean jump, boolean crouch, boolean run) {
        if (!InputHandler.chat && !Virucide.pause) {
            double rotationSpeed = 0.004 * Virucide.mousespeed;
            double jumpHeight = 0.5;
            double crouchHeight = 0.3;
            double walkSpeed = 1.0;
            int xa = 0;
            int za = 0;
            if (forward) {
                za++;
                walk = true;
            }
            if (back) {
                za--;
                walk = true;
            }
            if (left) {
                xa--;
                walk = true;
            }
            if (right) {
                xa++;
                walk = true;
            }
            if (turnLeft) {
                rotation -= rotationSpeed;
            }
            if (turnRight) {
                rotation += rotationSpeed;
            }
            if (xa != 0 || za != 0) {
                move(xa, za, rotation);
            }
            if (noJump) {
                yWait++;
            }
            if (noJump && yWait > 15) {
                noJump = false;
                jumpLen = 0;
                yWait = 0;
            }
            if (jump && !noJump) {
                jumpLen++;
                if (jumpLen > 25) {
                    noJump = true;
                } else {
                    y += jumpHeight;
                    run = false;
                }
            }
            if (crouch) {
                y -= crouchHeight;
                run = false;
                crouchwalk = true;
                walkSpeed = 0.3;
            }
            if (run) {
                walkSpeed = 2;
                walk = true;
                runwalk = true;
            }
            if (!forward && !back && !left && !right) {
                walk = false;
            }
            if (!crouch) {
                crouchwalk = false;
            }
            if (!run) {
                runwalk = false;
            }
            if (y < 0.01 && !crouch) {
                y = 0.0;
            } else {
                y *= 0.9;
            }
        }
    }

    public static void reset() {
        x = 0.0;
        z = 0.0;
    }
}