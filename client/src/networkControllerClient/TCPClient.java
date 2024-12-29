package networkControllerClient;


import networkControllerClient.marshalling.TCPReceive;
import networkControllerClient.marshalling.TCPSend;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient implements Runnable {
    public static  TCPSend tcpSend  = null;
    public static  TCPReceive tcpRec  = null;
    public static Socket socket = null;


/*    @Override
    public void run() {
        int port = 1234;
        InetAddress address = null;
        try {
            address = InetAddress.getByName ("127.0.0.1");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        Socket socket = null;
        try {
            socket = new Socket(address, port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        OutputStream out = null;
        try {
            out = socket.getOutputStream ( );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        InputStream in = null;
        try {
            in = socket.getInputStream ( );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println ("Verbunden mit " + socket.getInetAddress ( ).toString () + ":" + socket.getPort ());

        TCPSend tcpSend = new TCPSend (out);
        TCPReceive tcpRec = new TCPReceive (in);
    }*/

/*    public static void main(String[] args)  {
        int port = 1234;
        InetAddress address = InetAddress.getByName ("127.0.0.1");
        Socket socket = new Socket (address, port);

        OutputStream out = socket.getOutputStream ( );
        InputStream in = socket.getInputStream ( );
        System.out.println ("Verbunden mit " + socket.getInetAddress ( ).toString () + ":" + socket.getPort ());

        TCPSend tcpSend = new TCPSend (out);
        TCPReceive tcpRec = new TCPReceive (in);

        tcpSend.sendCode("ODD");
        tcpSend.sendDouble(10.0);
        tcpSend.sendInt(5);
        tcpSend.sendString("benutzer123");

        socket.close();
    }*/

    @Override
public void run() {
        while(true){
            try {
                HandleRequestFromServer.handleRequest();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    public boolean connect()throws Exception{
         int port = 1;
         InetAddress address = InetAddress.getByName ("127.0.0.1");
         socket = new Socket (address, port);

        OutputStream out = socket.getOutputStream ( );
        InputStream in = socket.getInputStream ( );
        System.out.println ("Verbunden mit " + socket.getInetAddress ( ).toString () + ":" + socket.getPort ());

        tcpSend = new TCPSend (out);
        tcpRec = new TCPReceive (in);
        Thread handler = new Thread(this);
        handler.start();
//TODO: bei connect muss dieser Code passieren und tcpSend und tcpRec muss öffentlich sichtbar sein
        return true;
    }

    public void disconnect() throws Exception {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }


}
