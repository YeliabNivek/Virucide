package Level;

import java.util.Random;

public class Level {

    public static Block[] b;
    public final int w, h;

    public Level(int width, int height) {
        w = width;
        h = height;
        b = new Block[w * h];
        int c = 0;
        Random r = new Random();
        int cupLoc = r.nextInt(w * h);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                Block bb = null;
                c++; //programming humor
                if (c == cupLoc) {
                    System.out.println(x + " " + y);
                    bb = new Cup(x, y);
                } else if (r.nextInt(5) == 0) {
                    bb = genSolid();
                } else if (r.nextInt(50) == 0) {
                    bb = new Alien(x, y);
                } else if (r.nextInt(50) == 0) {
                    bb = new Heart(x, y);
                } else {
                    bb = new Block();
                }
                b[x + y * w] = bb;
            }
        }
    }

    public void customMap(Block[] bb) {
        b = bb;
    }

    public Block create(int x, int y) {
        if (x < 0 || y < 0 || x >= w || y >= h) {
            return Block.wall;
        }
        return b[x + y * w];
    }

    public SolidBlock genSolid() {
        Random r = new Random();
        if (r.nextInt(18) == 0) {
            return new Tile();
        } else if (r.nextInt(10) == 0) {
            return new Dirt();
        }
        return new Rock();
    }

    public void addCreature(int xx, int zz, Creature c) {
        try {
            b[xx + zz * w] = c;
        } catch (Exception e) {
        }
    }

    public void remove(int xx, int zz) {
        try {
            b[xx + zz * w] = new Block();
        } catch (Exception e) {
        }
    }

    public Block getBlock(int xx, int zz) {
        try {
            if (xx > 0 && zz > 0) {
                return b[xx + zz * w];
            } else {
                return new Block();
            }
        } catch (Exception e) {
            return new Block();
        }
    }
}