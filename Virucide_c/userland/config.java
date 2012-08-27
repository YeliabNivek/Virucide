package userland;

import java.io.*;
import java.util.Properties;
import virucide.Virucide;

/**
 *
 * @author j0ker
 */
public class config {

    Properties prop = new Properties();

    public void saveConfig(String key, int value) {
        try {
            File f = new File("config.xml");
            if (!f.exists()) {
                f.createNewFile();
            }
            OutputStream write = new FileOutputStream("config.xml");
            prop.setProperty(key, Integer.toString(value));
            prop.storeToXML(write, "Resolution");
        } catch (Exception e) {
        }
    }

    public void loadConfig(String path) {
        try {
            InputStream in = new FileInputStream(path);
            prop.loadFromXML(in);
            String w = prop.getProperty("width");
            String h = prop.getProperty("height");
            setRes(Integer.parseInt(w), Integer.parseInt(h));
            in.close();
        } catch (FileNotFoundException e) {
            saveConfig("width", 800);
            saveConfig("height", 600);
            loadConfig("config.xml");
        } catch (IOException e) {
        }
    }

    public void setRes(int w, int h) {
        Virucide.w = w;
        Virucide.h = h;
    }
}