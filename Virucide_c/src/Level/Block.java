package Level;

/**
 *
 * @author j0ker
 */
public class Block {

    public boolean solid = false;
    public boolean alive = false;
    public boolean item = false;
    public static Block solidwall = new Tile();
    public static Block wall = new Block();
    public int[] pic;
    public int[] top;
    public int[] bottom;
    public double x, z;
    public int id;

    public int[] getPic() {
        return pic;
    }

    public int[] getTop() {
        return top;
    }

    public int[] getBottom() {
        return bottom;
    }

    public void move() {
    }

    public void act() {
    }

    @Override
    public String toString() {
        return "solid: " + solid + " Alive: " + alive + " item: " + item;
    }
}