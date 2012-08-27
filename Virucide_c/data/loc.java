package data;

/**
 *
 * @author j0ker
 */
public class loc {

    double x, z, y;

    public loc(double xx, double zz, double yy) {
        x = xx;
        z = zz;
        y = yy;
    }

    public loc() {
    }

    public loc(String s) {
        x = Double.parseDouble(s.substring(0, s.indexOf(":") + 1));
        s = s.substring(s.indexOf(":"));
        z = Double.parseDouble(s.substring(0, s.indexOf(":") + 1));
        s = s.substring(s.indexOf(":"));
        y = Double.parseDouble(s);
    }

    public double getX() {
        return x;
    }

    public double getZ() {
        return z;
    }

    public double getY() {
        return y;
    }

    public void setX(double xx) {
        x = xx;
    }

    public void setZ(double zz) {
        z = zz;
    }

    public void setY(double yy) {
        y = yy;
    }
}