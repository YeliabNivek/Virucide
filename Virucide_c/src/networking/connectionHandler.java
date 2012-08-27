package networking;

import Level.Block;
import Level.Tile;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;
import virucide.Virucide;

public class connectionHandler implements Runnable {

    Socket socket;
    DataOutputStream out;
    DataInputStream in;
    boolean go = true;
    boolean dont = false, found = true; //sorry about the poor variable names...
    String user;

    public connectionHandler(Socket s) {
        socket = s;
    }

    @Override
    public void run() {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            while (go) {
                out.writeUTF(Virucide.username);
                out.flush();
                String s = in.readUTF();
                if (s.compareTo("denied") == 0) {
                    String u = JOptionPane.showInputDialog(null, "This username has already been taken on the server, Please choose another", "Error!", JOptionPane.ERROR_MESSAGE);
                    Virucide.username = u;
                } else {
                    go = false;
                }
            }
            Block[] b = new Block[10000]; //grabbing world from host
            for (int i = 0; i < 10000; i++) {
                int temp = Integer.parseInt(in.readUTF());
                if (temp == 0) {
                    b[i] = new Block();
                } else {
                    b[i] = new Tile();
                }
            }
            String inp = in.readUTF();
            while (inp.compareTo("done!") != 0) {
                Virucide.newUser(inp, in.readUTF());
                inp = in.readUTF();
            }
//            Render3D.goldCup = new loc(Double.parseDouble(in.readUTF()), Double.parseDouble(in.readUTF()), 0.5);
            Virucide.loadCustomMap(b);
            Virucide.init(this);
            System.out.println("initialized");
            while (true) {
                String input = in.readUTF();
                if (input.compareTo("/quit") == 0) {
                    JOptionPane.showMessageDialog(null, "The Server has disconnected, game over", "Thanks for Playin!", JOptionPane.PLAIN_MESSAGE);//should NEVER happen
                    System.exit(0);
                } else if (input.compareTo("/locs") == 0) {
                    String get = in.readUTF();
                    try {
                        Virucide.Users.get(Integer.parseInt(get.substring(0, get.indexOf(":")))).updateLoc(get.substring(get.indexOf(":") + 1)); //update location of person
                    } catch (Exception e) {
                        Virucide.newUser(get, in.readUTF());
                    }
                } else if (input.compareTo("/new") == 0) {
                    String get = in.readUTF();
                    Virucide.newUser(get, in.readUTF());
                } else if (input.compareTo("/remove") == 0) {
                    Virucide.removeUser(in.readUTF());
                } else if (input.compareTo("/win") == 0) {
                    Virucide.loose = true;
                    Virucide.frame.getContentPane().setCursor(null);
                } else {
                    Virucide.handleString(input);
                }
            }
        } catch (IOException ex) {
        }
    }

    public void send(String s) {
        try {
            out.writeUTF(s);
            out.flush();
        } catch (IOException ex) {
        }
    }
}