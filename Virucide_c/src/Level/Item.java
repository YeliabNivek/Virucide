package Level;

/**
 *
 * @author j0ker
 */
public class Item extends Block {

    /* In the act method of an item, the item MUST be removed from the grid. It must not have a move method!  */
    public Item() {
        item = true;
    }

    public Item(int xx, int zz) {
        item = true;
        x = xx;
        z = zz;
    }
}