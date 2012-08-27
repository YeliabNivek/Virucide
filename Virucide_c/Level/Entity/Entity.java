package Level.Entity;

/**
 *
 * @author j0ker
 */
public class Entity {

    public static double x, z;
    protected boolean removed = false;

    public Entity() {
    }

    public void remove() {
        removed = true;
    }
}