package connection;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {

    public static void main(String[] args) {
        //Information die Der Client braucht zum verbinden mit dem Server
        int port = 1234;
        InetAddress address;

        try {
            address = InetAddress.getByName ("127.0.0.1"); //hier localhost; Später server Adresse
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        //Socket aufmachen und Daten schicken
        Socket socket;
        try {
            socket = new Socket(address, port);
            OutputStream out = socket.getOutputStream();
            System.out.println ("Verbunden mit " + socket.getInetAddress ().toString () + ":" + socket.getPort () );
            out.write ('A'); //hier können Codewörter geschickt werden wie SKI, ODD, EVE
            out.write ('B');
            out.write ('C');
            socket.close ();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
