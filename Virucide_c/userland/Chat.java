package userland;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import virucide.Virucide;

/**
 *
 * @author 13Baileykt
 */
public class Chat {

    public Graphics me;
    String message = "";
    int off = ("<" + Virucide.username + ">").length();
    boolean first = true;
    ArrayList<String> messages = new ArrayList<String>();
    int c = 1, cc = 0, ccc = 0;

    public void drawChat() {
        me.setFont(new Font("Lucida Console", Font.PLAIN, 20));
        me.setColor(Color.WHITE);
        me.drawString(message + blink(), 10, 550);
        for (int i = messages.size() - 1; i >= 0; i--) {
            c++;
            int off = 550 - (20 * c);
            if (off < 20) {
                break;
            }
            me.drawString(messages.get(i), 10, off);
        }
        c = 1;
    }

    public String blink() {
        if (cc < 15) {
            cc++;
            return "_";
        } else {
            if (ccc < 15) {
                ccc++;
                return " ";
            } else {
                cc = 0;
                ccc = 0;
                return "_";
            }
        }
    }

    public void handleChar(char cc) {
        if (!first && message.length() < 49 - off) {
            message = message + cc;
        } else if (first) {
            first = false;
        }
    }

    public void backSpace() {
        int s = message.length() - 1;
        if (s >= 0) {
            message = message.substring(0, s);
        }
    }

    public void setGraphics(Graphics g) {
        me = g;
    }

    public void reset() {
        first = true;
    }

    public void handleMessage(String message) {
        messages.add(message);
    }

    public void send() {
        if (message.compareTo("/quit") != 0 && message.compareTo("/locs") != 0 && message.compareTo("/win") != 0) {
            String mes = Virucide.username + ":" + message;
            Virucide.handleString(mes);
            try {
                Virucide.connector.send(mes);
            } catch (Exception e) {
            }
            message = "";
        } else {
            message = "";
        }
    }
}