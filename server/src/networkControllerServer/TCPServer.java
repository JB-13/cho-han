package networkControllerServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPServer {

    public static void main(String[] args) {

        //Port bereitstellen und Socket öffnen

        int port = 1234;
        ServerSocket sock;
        Socket connection;
        try {
            sock = new ServerSocket(port);
            System.out.println ("Server hört auf TCP-Port: " + sock.getLocalPort());
            System.out.println ("Warten auf Verbindungsaufbau");
            connection = sock.accept ();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            connection.setSoTimeout (0);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

        InputStream in;
        try {
            in = connection.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println ("Verbindungsaufbau hat statt gefunden");

        //Auslesen der empfangenen Daten
        int offset, readBytes = 0, currentLength, length = 3;
        byte [] buf = new byte [10];

        do {
            offset = readBytes;
            currentLength = length - readBytes;
            try {
                readBytes += in.read (buf, offset, currentLength);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } while (readBytes < length);
        System.out.print ("Es wurde empfangen: ");
        for (int i = 0; i < length; i++) System.out.print ( (char) buf[i]);

        try {
            connection.close ();
            sock.close ();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
