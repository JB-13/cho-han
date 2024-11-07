package networkControllerServer;

import networkControllerServer.marshalling.TCPReceive;
import networkControllerServer.marshalling.TCPSend;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPServer {

    public static void main(String[] args) throws Exception {

        int port = 1234;
        ServerSocket socket = new ServerSocket (port);
        System.out.println ("Server hört auf TCP-Port: " + socket.getLocalPort ());
        System.out.println ("Warten auf Verbindungsaufbau");

        Socket connection = socket.accept ();

        InputStream in = connection.getInputStream();
        OutputStream out = connection.getOutputStream();
        System.out.println ("Verbindungsaufbau hat statt gefunden");

        TCPReceive tcpRec = new TCPReceive (in);
        TCPSend tcpSend = new TCPSend (out);

        System.out.println("Server sendet 42");
        tcpSend.sendInt (42);

        System.out.println("Server erhält ein int");
        System.out.println(tcpRec.receiveInt ());
        connection.close ();
        socket.close ();


    }


}
