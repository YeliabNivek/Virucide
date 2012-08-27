package graphics;

import Level.Block;
import Level.Entity.Mobile.Player;
import Level.Level;
import data.loc;
import java.util.Random;
import networking.user;
import textures.*;
import virucide.Virucide;

/**
 *
 * @author j0ker
 */
public class Render3D extends Render {

    double[] zbuffer, zbufferwall;
    private double renderDistance = 5000;
    private double forward, right, cosine, sine, up, walking;
    int c = 0;
    public static user me = new user();
    int id = -1, cc = 0;

    public Render3D(int width, int height) {
        super(width, height);
        zbuffer = new double[w * h];
        zbufferwall = new double[w];
    }

    public void floor(game gg) {
        for (int x = 0; x < w; x++) {
            zbufferwall[x] = 0;
        }
        double floorPos = 8;
        double cellingPos = 20;
        forward = gg.controls.z;
        right = gg.controls.x;
        double rotation = gg.controls.rotation;
        cosine = Math.cos(rotation);
        sine = Math.sin(rotation);
        up = gg.controls.y;
        walking = 0;
        for (int y = 0; y < h; y++) {
            double ydepth = (y - h / 2.0) / h;
            double z = (floorPos + up) / ydepth;
            c = 0;
            if (Player.walk) {
                walking = Math.sin(gg.time / 6.0) * 0.5;
                z = (floorPos + up + walking) / ydepth;
            }
            if (Player.crouchwalk && Player.walk) {
                walking = Math.sin(gg.time / 6.0) * 0.25;
                z = (floorPos + up + walking) / ydepth;
            }

            if (Player.runwalk && Player.walk) {
                walking = Math.sin(gg.time / 6.0) * 0.8;
                z = (floorPos + up + walking) / ydepth;
            }
            if (ydepth < 0) {
                c = 1;
                z = (cellingPos - up) / -ydepth;
                if (Player.walk) {
                    z = (cellingPos - up - walking) / -ydepth;
                }
            }
            for (int x = 0; x < w; x++) {
                double xdepth = (x - w / 2.0) / h;
                xdepth *= z;
                double xx = xdepth * cosine + z * sine + right;
                double yy = z * cosine - xdepth * sine + forward;
                int xpix = (int) (xx + right);
                int ypix = (int) (yy + forward);
                zbuffer[x + y * w] = z;
                if (c == 1) {
                    pixels[x + y * w] = sky.floor.pixels[((xpix * 8) & 63) + ((ypix * 8) & 63) * 8];
                } else {
                    pixels[x + y * w] = tile.floor.pixels[(xpix & 7) + (ypix & 7) * 8];
                }
                if (z > 500) {
                    pixels[x + y * w] = 0;
                }
            }
        }
        Level l = gg.l;
        int size = Virucide.gridWidth;
        for (int xblock = -size; xblock <= size; xblock++) {
            for (int zblock = -size; zblock <= size; zblock++) {
                Block b = l.create(xblock, zblock);
                Block east = l.create(xblock + 1, zblock);
                Block south = l.create(xblock, zblock + 1);
                if (b.solid) {
                    if (!east.solid) {
                        renderWall(xblock + 1, xblock + 1, zblock, zblock + 1, 0.5, b.getPic());
                    }
                    if (!south.solid) {
                        renderWall(xblock + 1, xblock, zblock + 1, zblock + 1, 0.5, b.getPic());
                    }
                } else {
                    if (east.solid) {
                        renderWall(xblock + 1, xblock + 1, zblock + 1, zblock, 0.5, east.getPic());
                    }
                    if (south.solid) {
                        renderWall(xblock, xblock + 1, zblock + 1, zblock + 1, 0.5, south.getPic());
                    }
                }
            }
        }
        for (int xblock = -size; xblock <= size; xblock++) {
            for (int zblock = -size; zblock <= size; zblock++) {
                Block b = l.create(xblock, zblock);
                Block east = l.create(xblock + 1, zblock);
                Block south = l.create(xblock, zblock + 1);
                if (b.solid) {
                    if (!east.solid) {
                        renderWall(xblock + 1, xblock + 1, zblock, zblock + 1, 0, b.getPic());
                    }
                    if (!south.solid) {
                        renderWall(xblock + 1, xblock, zblock + 1, zblock + 1, 0, b.getPic());
                    }
                } else {
                    if (east.solid) {
                        renderWall(xblock + 1, xblock + 1, zblock + 1, zblock, 0, east.getPic());
                    }
                    if (south.solid) {
                        renderWall(xblock, xblock + 1, zblock + 1, zblock + 1, 0, south.getPic());
                    }
                }
            }
        }
        cc++;
        for (int xblock = -size; xblock <= size; xblock++) {
            for (int zblock = -size; zblock <= size; zblock++) {
                Block b = l.create(xblock, zblock);
                if (b.alive) {
                    renderSprite(b.x, 0.0, b.z + 1, -0.5, b.getTop());
                    renderSprite(b.x, 0.0, b.z + 1, 0.5, b.getBottom());
                    if ((int) b.x == (int) me.getX() && (int) b.z == (int) me.getZ() && b.id != id) {
                        b.act();
                        id = b.id;
                    }
                    if (cc > 50) {
                        id = -1;
                        cc = 0;
                    }
                    b.move();
                } else if (b.item) {
                    renderSprite(xblock, 0.0, zblock + 1, 0.5, b.getPic());
                    if ((int) b.x == (int) me.getX() && (int) b.z == (int) me.getZ()) {
                        b.act();
                    }
                }
            }
        }
        for (int i = 0; i < Virucide.Users.size(); i++) { //Generating other players
            renderSprite(Virucide.Users.get(i).getX(), 0.0, Virucide.Users.get(i).getZ(), -0.5 - Virucide.Users.get(i).getY(), black_top.floor.pixels);
            renderSprite(Virucide.Users.get(i).getX(), 0.0, Virucide.Users.get(i).getZ(), 0.5 - Virucide.Users.get(i).getY(), black_bottom.floor.pixels);
        }
        me.setLoc(new loc(right * 0.25, forward * 0.25, up * 0.1));
    }

    public void renderTexture() {
        Random r = new Random(100);
        for (int i = 0; i < 10000; i++) {
            double xx = r.nextDouble();
            double yy = r.nextDouble();
            double zz = 2;
            int xPixel = (int) (xx / zz * h / 2 + w / 2);
            int yPixel = (int) (yy / zz * h / 2 + h / 2);
            if (xPixel >= 0 && yPixel >= 0 && xPixel < w && yPixel < h) {
                pixels[xPixel + yPixel * w] = 0xffffff;
            }
        }
    }

    public void renderStill(double xLeft, double xRight, double zDistance, double yHeight) {
    }

    public void renderSprite(double x, double y, double z, double yOff, int[] pix) {
        int spriteSize = h / 2;
        double upco = -0.125;
        double rightco = 0.125;
        double forwardco = 0.125;
        double walkco = 0.0625;
        double xc = ((x / 2) - (right * rightco)) * 2 + 0.5;
        double yc = ((y / 2) - (up * upco)) + (walking * walkco) * 2 + yOff;
        double zc = ((z / 2) - (forward * forwardco)) * 2;
        double rotX = xc * cosine - zc * sine;
        double rotY = yc;
        double rotZ = zc * cosine + xc * sine;
        double xcenter = 400.0;
        double ycenter = 300.0;
        double xpixel = (rotX / rotZ * h + xcenter);
        double ypixel = (rotY / rotZ * h + ycenter);
        double xpixelL = xpixel - spriteSize / rotZ;
        double xpixelR = xpixel + spriteSize / rotZ;
        double ypixelL = ypixel - spriteSize / rotZ;
        double ypixelR = ypixel + spriteSize / rotZ;
        int xpl = (int) xpixelL;
        int xpr = (int) xpixelR;
        int ypl = (int) ypixelL;
        int ypr = (int) ypixelR;
        if (xpl < 0) {
            xpl = 0;
        }
        if (xpr > w) {
            xpr = w;
        }
        if (ypl < 0) {
            ypl = 0;
        }
        if (ypr > h) {
            ypr = h;
        }
        rotZ *= 8;
        for (int yp = ypl; yp < ypr; yp++) {
            double pixelrotY = (yp - ypixelR) / (ypixelL - ypixelR);
            int ytexture = (int) (8 * pixelrotY);
            for (int xp = xpl; xp < xpr; xp++) {
                double pixelrotX = (xp - xpixelR) / (xpixelL - xpixelR);
                int xtexture = (int) (8 * pixelrotX);
                if (zbuffer[xp + yp * w] > rotZ) {
                    int col = pix[(xtexture & 7) + (ytexture & 7) * 8];
                    if (col != 0xffff0000) {
                        pixels[xp + yp * w] = col;
                        zbuffer[xp + yp * w] = rotZ;
                    }
                }
            }
        }
    }

    public void renderWall(double xleft, double xright, double zdistanceleft, double zdistanceright, double yheight, int[] pix) {
        double upco = 0.0625;
        double rightco = 0.125;
        double forwardco = 0.125;
        double walkco = -0.0625;
        double xcleft = ((xleft / 2) - (right * rightco)) * 2;
        double zcleft = ((zdistanceleft / 2) - (forward * forwardco)) * 2;
        double rotleftsideX = xcleft * cosine - zcleft * sine;
        double ycornerTL = ((-yheight) - (-up * upco + (walking * walkco))) * 2;
        double ycornerBL = ((+0.5 - yheight) - (-up * upco + (walking * walkco))) * 2;
        double rotleftsideZ = zcleft * cosine + xcleft * sine;
        double xcright = ((xright / 2) - (right * rightco)) * 2;
        double zcright = ((zdistanceright / 2) - (forward * forwardco)) * 2;
        double rotrightsideX = xcright * cosine - zcright * sine;
        double ycornerTR = ((-yheight) - (-up * upco + (walking * walkco))) * 2;
        double ycornerBR = ((+0.5 - yheight) - (-up * upco + (walking * walkco))) * 2;
        double rotrightsideZ = zcright * cosine + xcright * sine;
        double text33 = 0;
        double text44 = 8;
        double clip = 0.5;
        if (rotleftsideZ < clip && rotrightsideZ < clip) {
            return;
        }
        if (rotleftsideZ < clip) {
            double clip0 = (clip - rotleftsideZ) / (rotrightsideZ - rotleftsideZ);
            rotleftsideZ = rotleftsideZ + (rotrightsideZ - rotleftsideZ) * clip0;
            rotleftsideX = rotleftsideX + (rotrightsideX - rotleftsideX) * clip0;
            text33 = text33 + (text44 - text33) * clip0;
        }
        if (rotrightsideZ < clip) {
            double clip0 = (clip - rotleftsideZ) / (rotrightsideZ - rotleftsideZ);
            rotrightsideZ = rotleftsideZ + (rotrightsideZ - rotleftsideZ) * clip0;
            rotrightsideX = rotleftsideX + (rotrightsideX - rotleftsideX) * clip0;
            text44 = text33 + (text44 - text33) * clip0;
        }
        double xpixelleft = (rotleftsideX / rotleftsideZ * h + w / 2);
        double xpixelright = (rotrightsideX / rotrightsideZ * h + w / 2);
        if (xpixelleft >= xpixelright) {
            return;
        }
        int xpixelleftint = (int) (xpixelleft);
        int xpixelrightint = (int) (xpixelright);
        if (xpixelleftint < 0) {
            xpixelleftint = 0;
        }
        if (xpixelrightint > w) {
            xpixelrightint = w;
        }
        double ypixellefttop = (ycornerTL / rotleftsideZ * h + h / 2.0);
        double ypixelleftbottom = (ycornerBL / rotleftsideZ * h + h / 2.0);
        double ypixelrighttop = (ycornerTR / rotrightsideZ * h + h / 2.0);
        double ypixelrightbottom = (ycornerBR / rotrightsideZ * h + h / 2.0);
        double text1 = 1 / rotleftsideZ;
        double text2 = 1 / rotrightsideZ;
        double text3 = text33 / rotleftsideZ;
        double text4 = text44 / rotrightsideZ - text3;
        for (int x = xpixelleftint; x < xpixelrightint; x++) {
            double pixelrot = (x - xpixelleft) / (xpixelright - xpixelleft);
            double zwall = (text1 + (text2 - text1) * pixelrot);
            if (zbufferwall[x] > zwall) {
                continue;
            }
            zbufferwall[x] = zwall;
            int xtexture = (int) ((text3 + text4 * pixelrot) / zwall);
            double ypixeltop = ypixellefttop + (ypixelrighttop - ypixellefttop) * pixelrot;
            double ypixelbottom = ypixelleftbottom + (ypixelrightbottom - ypixelleftbottom) * pixelrot;
            int ypixeltopint = (int) (ypixeltop);
            int ypixelbottomint = (int) (ypixelbottom);
            if (ypixeltopint < 0) {
                ypixeltopint = 0;
            }
            if (ypixelbottomint > h) {
                ypixelbottomint = h;
            }
            for (int y = ypixeltopint; y < ypixelbottomint; y++) {
                double pixelrotY = (y - ypixeltop) / (ypixelbottom - ypixeltop);
                int ytexture = (int) (8 * pixelrotY);
//                pixels[x + y * w] = pix[((xtexture * 16) & 63) + ((ytexture * 16) & 63) * 8];
                pixels[x + y * w] = pix[(xtexture & 7) + (ytexture & 7) * 8];
//                pixels[x + y * w] = xtexture * 100 + ytexture * 100;
                zbuffer[x + y * w] = 1 / (text1 + (text2 - text1) * pixelrot) * 8;
            }
        }
    }

    public void renderDistance() {
        for (int i = 0; i < w * h; i++) {
            int color = pixels[i];
            int brightness = (int) (renderDistance / (zbuffer[i]));
            if (brightness < 0) {
                brightness = 255;
            }
            if (brightness > 255) {
                brightness = 255;
            }
            int r = (color >> 16) & 0xff;
            int g = (color >> 8) & 0xff;
            int b = (color) & 0xff;

            r = r * brightness / 255;
            g = g * brightness / 255;
            b = b * brightness / 255;

            pixels[i] = r << 16 | g << 8 | b;
        }
    }
}
//        for (int xblock = -size; xblock <= size; xblock++) {
//            for (int zblock = -size; zblock <= size; zblock++) {
//                Block b = l.create(xblock, zblock);
//                for (int s = 0; s < b.sprites.size(); s++) {
//                    Sprite ss = b.sprites.get(s);
//                    renderSprite(xblock + ss.x, ss.y, zblock + ss.z, 0.5);
//                }
//            }
//        }
//        renderSprite(right * 0.25, 0.0, forward * 0.25, -0.5, black_top.floor.pixels);
//        renderSprite(right * 0.25, 0.0, forward * 0.25, 0.5, black_bottom.floor.pixels);