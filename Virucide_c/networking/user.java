package networking;

import data.loc;
import virucide.Virucide;
import virucide.run;

public class user {

    private String IP_Address;
    private String Username;
    private connectionHandler streams;
    private String address;
    private loc myLoc;
    private int numHearts = 4;

    public user(String name, String ip, connectionHandler stream) {
        Username = name;
        IP_Address = ip;
        streams = stream;
    }

    public user() {
    }

    public user(String name) {
        Username = name;
    }

    public void setLoc(double xx, double zz, double yy) {
        if (myLoc == null) {
            myLoc = new loc(xx, zz, yy);
        } else {
            myLoc.setX(xx);
            myLoc.setZ(zz);
            myLoc.setY(yy);
        }
    }

    public void setLoc(loc l) {
        if (myLoc == null) {
            myLoc = l;
        } else {
            myLoc.setX(l.getX());
            myLoc.setY(l.getY());
            myLoc.setZ(l.getZ());
        }
    }

    public void updateLoc(String s) {
        setLoc(Double.parseDouble(s.substring(0, s.indexOf(":"))), Double.parseDouble(s.substring(s.indexOf(":") + 1, s.lastIndexOf(":"))), Double.parseDouble(s.substring(s.lastIndexOf(":") + 1)));
    }

    public loc getLoc() {
        return myLoc;
    }

    public String getUsername() {
        return Username;
    }

    public String getIP() {
        return IP_Address;
    }

    public void send(String message) {
        streams.send(message);
    }

    public String getAddress() {
        address = Username + "@" + IP_Address;
        return address;
    }

    public double getX() {
        try {
            return myLoc.getX();
        } catch (Exception e) {
            return 0.0;
        }
    }

    public double getZ() {
        try {
            return myLoc.getZ();
        } catch (Exception e) {
            return 0.0;
        }
    }

    public double getY() {
        try {
            return myLoc.getY();
        } catch (Exception e) {
            return 0.0;
        }
    }

    public int getHearts() {
        return numHearts;
    }

    public void minusHeart() {
        numHearts--;
        if (numHearts == 0) {
            Virucide.loose = true;
            Virucide.frame.getContentPane().setCursor(null);
            if (run.multiplayer) {
                streams.send("/quit");
                streams.send(Virucide.username);
            }
        }
    }

    public void addHeart() {
        if (numHearts < 4) {
            numHearts++;
        }
    }

    public void resethearts() {
        numHearts = 4;
    }
}