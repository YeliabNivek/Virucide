package userland;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import virucide.Virucide;

/**
 *
 * @author j0ker
 */
public class InputHandler implements KeyListener, FocusListener, MouseListener, MouseMotionListener {

    public boolean[] key = new boolean[68836];
    public static int mousex;
    public static int mousey;
    public static int mousedx;
    public static int mousedy;
    public static int mousepx;
    public static int mousepy;
    public static int mousexOn;
    public static int mousebutton;
    public static boolean dragged = false;
    public static boolean game = false;
    private static JFrame jf;
    private boolean clickedAgain = false;
    public static boolean chat = false;
    public static KeyEvent character;

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        try {
            int keycode = ke.getKeyCode(), comp;
            if (!chat) {
                if (keycode > 0 && keycode < key.length) {
                    key[keycode] = true;
                }
                comp = KeyEvent.VK_R;
            } else {
                comp = KeyEvent.VK_ESCAPE;
            }
            if (!Virucide.menu && keycode == KeyEvent.VK_P) {
                Virucide.pause = true;
                Virucide.frame.getContentPane().setCursor(null);
            }
            if (keycode == comp) {
                if (!clickedAgain) {
                    jf.getContentPane().setCursor(null); //the try catch is for this line, during username it is null
                    Virucide.message = true;
                    clickedAgain = true;
                    chat = true;
                    Virucide.chat = true;
                } else {
                    try {
                        Robot r = new Robot();
                        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                        r.mouseMove((dim.width) / 2, (dim.height) / 2);
                    } catch (AWTException ex) {
                    }
                    BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
                    Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blank");
                    jf.getContentPane().setCursor(blank);
                    Virucide.message = false;
                    clickedAgain = false;
                    chat = false;
                    Virucide.chat = false;
                }
            }
            if (chat && keycode == KeyEvent.VK_ENTER) {
                Virucide.c.send();
            } else if (chat && keycode == KeyEvent.VK_BACK_SPACE) {
                Virucide.c.backSpace();
            } else if (chat && keycode == KeyEvent.VK_SHIFT) {
                //do nothing
            } else if (chat) {
                Virucide.c.handleChar(ke.getKeyChar());
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if (!chat) {
            int keycode = ke.getKeyCode();
            if (keycode > 0 && keycode < key.length) {
                key[keycode] = false;
            }
        } else {
            //do nothing
        }
    }

    @Override
    public void focusGained(FocusEvent fe) {
    }

    @Override
    public void focusLost(FocusEvent fe) {
        for (int i = 0; i < key.length; i++) {
            key[i] = false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mousebutton = e.getButton();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousepx = e.getX();
        mousepy = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        dragged = false;
        mousebutton = e.getButton();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Virucide.moving = true;
        try {
            Robot r = new Robot();
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            if (game && !Virucide.menu && !Virucide.pause && !clickedAgain && !Virucide.loose && !Virucide.win) {
                r.mouseMove((dim.width) / 2, (dim.height) / 2); //basically if the mouse moves off screen set it back to the center
                Virucide.oldx = (dim.width) / 2;
                Virucide.newx = (dim.width) / 2;
            }
        } catch (AWTException ex) {
        }
        Virucide.moving = false;
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        mousedx = me.getX();
        mousedy = me.getY();
        dragged = true;
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        mousex = me.getX();
        mousey = me.getY();
        mousexOn = me.getXOnScreen();
        mousebutton = me.getButton();

//        Robot r;
//        try {
//            r = new Robot();
//            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
//            if (game && !Virucide.menu && !Virucide.pause && !clickedAgain && !Virucide.loose && !Virucide.win) {
//                r.mouseMove((dim.width) / 2, (dim.height) / 2);
//            }
//        } catch (AWTException ex) {
//        }
    }

    public static void init(JFrame j) {
        jf = j;
    }
}