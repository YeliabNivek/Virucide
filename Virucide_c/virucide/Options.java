package virucide;

import java.awt.Choice;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import userland.config;

/**
 *
 * @author j0ker
 */
public class Options extends JFrame {

    private static final long serial = 1L;
    private int w = 550;
    private int h = 440;
    private JButton ok;
    private Rectangle rok, rres;
    private Choice res_choices = new Choice();
    private JTextField tw, th;
    private JLabel lw, lh;
    int ww = 0, hh = 0;
    protected int b_w = 80;
    protected int b_h = 40;
    protected JPanel window = new JPanel();
    config c = new config();

    public Options() {
        setTitle("Options");
        setSize(new Dimension(w, h));
        setLocationRelativeTo(null);
        drawButtons();
        window.setLayout(null);
        add(window);
        setResizable(false);
        setVisible(true);
    }

    private void drawButtons() {
        ok = new JButton("ok");
        rok = new Rectangle((w - 100), (h - 70), b_w, b_h - 10);
        ok.setBounds(rok);
        window.add(ok);
        rres = new Rectangle(50, 80, 80, 25);
        res_choices.setBounds(rres);
        res_choices.add("640, 480");
        res_choices.add("800, 600");
        res_choices.add("1024, 786");
        res_choices.select(1);
        window.add(res_choices);
        lw = new JLabel("Width: ");
        lw.setBounds(30, 150, 120, 20);
        window.add(lw);
        lh = new JLabel("Height: ");
        lh.setBounds(30, 180, 120, 20);
        window.add(lh);
        tw = new JTextField();
        tw.setBounds(80, 150, 60, 20);
        window.add(tw);
        th = new JTextField();
        th.setBounds(80, 180, 60, 20);
        window.add(th);
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
//                new Launcher(0);
                c.saveConfig("width", parseW());
                c.saveConfig("height", parseH());
            }
        });
    }

    private void drop() {
        Virucide.selection = res_choices.getSelectedIndex();
        if (res_choices.getSelectedIndex() == 0) {
            ww = 640;
            hh = 480;
        } else if (res_choices.getSelectedIndex() == 1 || res_choices.getSelectedIndex() == -1) {
            ww = 800;
            hh = 600;
        } else {
            ww = 1024;
            hh = 768;
        }
    }

    private int parseW() {
        try {
            return Integer.parseInt(tw.getText());
        } catch (Exception e) {
            return 800;
        }
    }

    private int parseH() {
        try {
            return Integer.parseInt(th.getText());
        } catch (Exception e) {
            return 600;
        }
    }
}