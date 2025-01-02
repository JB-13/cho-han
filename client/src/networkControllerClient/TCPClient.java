package networkControllerClient;


import networkControllerClient.marshalling.TCPReceive;
import networkControllerClient.marshalling.TCPSend;
import networkControllerServer.TCPServer;

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
    public static  Thread handler;

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) { // Prüfen, ob der Thread unterbrochen wurde
                HandleRequestFromServer.handleRequest();
            }
        } catch (Exception e) {
            System.out.println("Server is not responding anymore");


        } finally {
            Thread.currentThread().interrupt();
            if (!socket.isClosed()) {
                disconnect(); // Verbindung trennen
            }


        }
    }


    public synchronized boolean connect(){
        int port;
        InetAddress address = null;
        OutputStream out = null;
        InputStream in = null;
        try {
            address = InetAddress.getByName ("127.0.0.1");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        for (int i = 50000; i <= 50050; i++) {
            port = i;
                try {
                    socket = new Socket (address, port);
                    socket.setSoTimeout(100); //keep alive msg eventuell notwendig
                    out = socket.getOutputStream ( );
                    in = socket.getInputStream ( );
                    tcpSend = new TCPSend (out);
                    tcpRec = new TCPReceive (in);

                    tcpSend.sendCode("CON");
                    String response = tcpRec.receiveCode();
                    if ("ACK".equals(response)) { // Server bestätigt mit "ACK"
                        System.out.println ("Successfully connected with " + socket.getInetAddress ( ).toString () + ":" + socket.getPort ());
                        socket.setSoTimeout(5000);
                        return true;
                    } else {
                        System.out.println("Server declined connnection.");
                        socket.close(); // Verbindung schließen, falls keine Bestätigung
                    }

                } catch (IOException e) {
                    System.out.println("Port " + port + " is not available or is already in use");
                } catch (Exception e) {
                    System.out.println("Reading took too long");

                }
            }

        System.out.println ("Successfully connected with " + socket.getInetAddress ( ).toString () + ":" + socket.getPort ());


        return true;
    }

    public void startGameHandlerThread(){
        handler = new Thread(this);
        handler.start();

    }

    public synchronized void disconnect() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                System.out.println("*****Connection to Server closed*****");
            }
        } catch (IOException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }


}

class KeepAlive implements Runnable{
    public static  Thread keepalive;

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
               TCPClient.tcpSend.sendCode("ALI");
                System.out.println("keep alive");
                Thread.sleep(5000);

            }
        } catch (IOException e) {
            System.err.println("Connection lost to server: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("KeepAlive-Thread was interrupted: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred with the KeepAlive-Thread: " + e.getMessage());
        } finally {
            System.out.println("KeepAlive-Thread finished.");
            Thread.currentThread().interrupt();
        }
    }

    public void startGameHandlerThread(){
        keepalive = new Thread(this);
        keepalive.start();

    }

}
