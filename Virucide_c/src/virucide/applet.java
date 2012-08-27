package virucide;

import java.applet.Applet;
import java.awt.BorderLayout;

/**
 *
 * @author j0ker
 */
public class applet extends Applet {

    private Virucide bw = new Virucide();

    @Override
    public void init() {
        setLayout(new BorderLayout());
        add(bw);
    }

    @Override
    public void start() {
        bw.start();
    }

    @Override
    public void stop() {
        bw.stop();
    }
}