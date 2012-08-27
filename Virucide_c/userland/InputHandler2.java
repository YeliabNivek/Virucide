package userland;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import virucide.Virucide;

/**
 *
 * @author j0ker
 */
public class InputHandler2 implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        if (!Virucide.gotuser) {
            if (k == KeyEvent.VK_ENTER) {
                Virucide.u.enter();
            } else if (k == KeyEvent.VK_BACK_SPACE) {
                Virucide.u.backSpace();
            } else if (!(k == KeyEvent.VK_SHIFT)) {
                Virucide.u.addChar(e.getKeyChar());
            }
        } else if (Virucide.add) {
            if (k == KeyEvent.VK_ENTER) {
                Virucide.a.enter();
            } else if (k == KeyEvent.VK_BACK_SPACE) {
                Virucide.a.backSpace();
            } else if (!(k == KeyEvent.VK_SHIFT)) {
                Virucide.a.addChar(e.getKeyChar());
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}