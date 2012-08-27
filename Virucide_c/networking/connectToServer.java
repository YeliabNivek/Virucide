/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author 13hallht
 */
public class connectToServer extends Thread {

    Socket socket;
    String IP;

    public connectToServer(String ip) {
        IP = ip;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(IP, 7373);
            Runnable connectionHandler = new connectionHandler(socket);
            new Thread(connectionHandler).start();
        } catch (UnknownHostException ex) {
        } catch (IOException ex) {
        }
    }
}