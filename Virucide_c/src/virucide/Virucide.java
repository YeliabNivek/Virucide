package virucide;

import Level.Block;
import Level.Entity.Mobile.Player;
import graphics.Render3D;
import graphics.Screen;
import graphics.Texture;
import graphics.game;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;
import networking.connectionHandler;
import networking.user;
import userland.*;
import window.*;

//Todo: remove Alien and gen a new one once it leaves field
/**
 * 6/30/12 Virucide is at 2693 lines
 *
 * @author j0ker
 */
public class Virucide extends Canvas implements Runnable {

    //the wall of variables!!! I should make a seperate class (or interface) for these...
    public static int w = 800;
    public static int h = 600;
    public static final String TITLE = "Virucide v0.1 beta 3";
    private Thread t;
    private boolean running = false;
    private Screen s;
    public static int gridWidth = 100;
    public static game game;
    private BufferedImage img;
    private int[] pixels;
    private InputHandler in;
    private InputHandler2 in2;
    public static int newx = 0;
    public static int oldx = Toolkit.getDefaultToolkit().getScreenSize().width / 2;
    private int fps;
    public static int mousespeed;
    public static int selection = 0;
    public static ArrayList<user> Users = new ArrayList<user>();
    public static connectionHandler connector;
    public static String username;
    public static boolean message = false, chat = false, gotuser = false;
    public static Chat c;
    boolean first = true;
    public static boolean pause = false, menu = true, add = false;
    config cc = new config();
    public static JFrame frame;
    public static username u;
    public static address a;
    public static boolean win = false, loose = false;
    private double sine = 0;
    public static boolean moving = false;

    public static int getGamewidth() {
        return w;
    }

    public static int getGameheight() {
        return h;
    }

    public synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        t = new Thread(this, "game");
        t.start();
    }

    public static void main(String[] args) {
        Virucide v = new Virucide();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        frame = new JFrame();
        frame.add(v);
        frame.setSize(Virucide.getGamewidth(), Virucide.getGameheight());
        frame.setTitle(Virucide.TITLE);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        try {
            frame.setIconImage(ImageIO.read(Texture.class.getResource("/res/rock.png")));
        } catch (Exception e) {
        }
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = Virucide.frame.getSize().width;
        int h = Virucide.frame.getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;
        frame.setLocation(x, y);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true); //not default close operation sel3ected on purpose
        v.start();
    }

    @Override
    public void run() {
        int frames = 0;
        long previousTime = System.nanoTime();
        double ns = 1000000000.0 / 60.0;
        double delta = 0;
        int updates = 0;
        long timer = System.currentTimeMillis();
        requestFocus(); //this is awesome!
        while (running) {
            long currentTime = System.nanoTime();
            delta += (currentTime - previousTime) / ns;
            previousTime = currentTime;
            if (delta >= 1) {
                tick();
                updates++;
                delta--;
            }
            frames++;
            if (menu) {
                if (add) {
                    renderAddress();
                } else {
                    renderMenu();
                }
            } else {
                if (pause) {
                    renderPause();
                } else {
                    if (win) {
                        renderWin();
                    } else if (loose) {
                        renderLoose();
                    } else {
                        render();
                    }
                }
            }
            while (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                fps = frames;
                frames = 0;
                updates = 0;
            }
        }
    }

    public synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;
        try {
            t.join();
        } catch (Exception e) {
            System.out.println(e);
            System.exit(0);
        }
    }

    private void renderWin() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        win w = new win(bs);
        w.render();
    }

    private void renderLoose() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        loose l = new loose(bs);
        l.render();
    }

    private void renderAddress() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        a = new address(bs);
        a.render();
    }

    private void renderMenu() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        if (gotuser) {
            try {
                g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/menu.png")), 0, 0, 800, 600, null);
                if (InputHandler.mousex > 605 && InputHandler.mousex < 605 + 170 && InputHandler.mousey > 130 && InputHandler.mousey < 130 + 35) {
                    g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/single_on.png")), 605, 130, 170, 35, null);
                    g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/arrow.png")), 750, 125, 70, 50, null);
                    g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/multi_off.png")), 605, 170, 170, 35, null);
                    g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/options_off.png")), 650, 205, 70, 50, null);
                    g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/quit_off.png")), 654, 250, 60, 40, null);
                    if (InputHandler.mousebutton == 1) {
                        cc.loadConfig("config.xml");
                        new run("null", false); //too lazy to overload the method
                    }
                } else {
                    g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/single_off.png")), 605, 130, 170, 35, null);
                    if (InputHandler.mousex > 605 && InputHandler.mousex < 605 + 170 && InputHandler.mousey > 170 && InputHandler.mousey < 170 + 35) {
                        g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/multi_on.png")), 605, 170, 170, 35, null);
                        g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/arrow.png")), 750, 165, 70, 50, null);
                        g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/options_off.png")), 650, 205, 70, 50, null);
                        g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/quit_off.png")), 654, 250, 60, 40, null);
                        if (InputHandler.mousebutton == 1) {
                            add = true;
                        }
                    } else {
                        g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/multi_off.png")), 605, 170, 170, 35, null);
                        if (InputHandler.mousex > 650 && InputHandler.mousex < 650 + 70 && InputHandler.mousey > 205 && InputHandler.mousey < 205 + 40) {
                            g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/options_on.png")), 650, 205, 70, 50, null);
                            g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/arrow.png")), 750, 210, 70, 50, null);
                            g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/quit_off.png")), 654, 250, 60, 40, null);
                            if (InputHandler.mousebutton == 1) {
                                new Options();
                            }
                        } else {
                            g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/options_off.png")), 650, 205, 70, 50, null);
                            if (InputHandler.mousex > 650 && InputHandler.mousex < 650 + 70 && InputHandler.mousey > 250 && InputHandler.mousey < 250 + 50) {
                                g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/quit_on.png")), 654, 250, 60, 40, null);
                                g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/arrow.png")), 750, 250, 70, 50, null);
                                if (InputHandler.mousebutton == 1) {
                                    System.exit(0);
                                }
                            } else {
                                g.drawImage(ImageIO.read(Texture.class.getResource("/res/menu/quit_off.png")), 654, 250, 60, 40, null);
                            }
                        }
                    }
                }
            } catch (Exception e) {
            }
            sine += 0.25;
            g.setFont(new Font("Lucida Console", 0, ((int) (Math.abs(Math.sin(sine) * 6.0))) + 30));
            g.setColor(Color.red);
            display("Please don't move this window on your screen!", g);
            g.dispose();
            bs.show();
        } else {
            u = new username(bs);
            u.render();
        }
    }

    private void display(String s, Graphics g) { // this is sexy
        for (int i = 0; i < s.length() - 1; i++) {
            g.drawString(s.substring(i, i + 1), 1 + (18 * i), 100);
        }
        g.drawString(s.substring((s.length() / 2) * 2), 1 + (36 * (s.length() / 2)), 100);
    }

    private void renderPause() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        pause p = new pause(bs);
        p.render();
    }

    private void render() {
//        System.out.println("rendering...");
        if (first) {
            c = new Chat();
            first = false;
        }
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        s.render(game);
        System.arraycopy(s.pixels, 0, pixels, 0, getGamewidth() * getGameheight());
        g.drawImage(img, 0, 0, getGamewidth() + 10, getGameheight() + 10, null);
        sine++;
        g.setFont(new Font("Lucida Console", 0, 50));
        g.setColor(Color.WHITE);
        g.drawString(fps + " fps", 20, 50);
        g.drawString("X: " + Render3D.me.getX(), 100, 100);
        g.drawString("Z: " + Render3D.me.getZ(), 150, 150);
        for (int i = 0; i < Render3D.me.getHearts(); i++) {
            try {
                g.drawImage(ImageIO.read(Texture.class.getResource("/res/heart_b.png")), i * 55, 0, 55, 50, this);
            } catch (Exception e) {
            }
        }
        if (chat) {
            c.setGraphics(g);
            c.drawChat();
        } else {
            c.reset();
        }
        if (run.multiplayer) {
            sendLoc();
        }
        g.dispose();
        bs.show();
    }

    private void tick() {
        game.tick(in.key);
        if (!moving) {
            newx = InputHandler.mousexOn;
            if (newx > oldx) {
                Player.turnRight = true;
            }
            if (newx < oldx) {
                Player.turnLeft = true;
            }
            if (newx == oldx) {
                Player.turnLeft = false;
                Player.turnRight = false;
            }
            mousespeed = Math.abs(newx - oldx);
            oldx = newx;
        }
    }

    public Virucide() {
        Dimension size = new Dimension(getGamewidth(), getGameheight());
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        s = new Screen(getGamewidth(), getGameheight());
        img = new BufferedImage(getGamewidth(), getGameheight(), BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
        game = new game();
        in = new InputHandler();
        in2 = new InputHandler2();
        addKeyListener(in);
        addKeyListener(in2);
        addMouseListener(in);
        addMouseMotionListener(in);
        addFocusListener(in);
        InputHandler.game = true;
    }

    public static void handleString(String s) {
        try {
            displayMessage(s.substring(0, s.lastIndexOf(":")), s.substring(s.lastIndexOf(":") + 1));
        } catch (Exception e) {
        }
    }

    public static void displayMessage(String username, String msg) {
        c.handleMessage("<" + username + ">" + msg);
    }

    public static void init(connectionHandler ch) {
        connector = ch;
    }

    public static void send(String msg) {
        connector.send(msg);
    }

    public void sendLoc() {
        try {
            connector.send("/locs");
            connector.send(username + ":" + Render3D.me.getX() + ":" + Render3D.me.getZ() + ":" + Render3D.me.getY());
        } catch (Exception e) {
        }
    }

    public static void newUser(String s, String user) {
        Users.add(new user(user));
        Users.get(Users.size() - 1).updateLoc(s);
    }

    public static void removeUser(String name) {
        for (int i = 0; i < Users.size(); i++) {
            if (Users.get(i).getUsername().compareTo(name) == 0) {
                Users.remove(i);
            }
        }
    }

    public static void loadCustomMap(Block[] bb) {
        game.newMap(bb);
    }
}