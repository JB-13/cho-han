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
        try {
            while (!Thread.currentThread().isInterrupted()) { // Prüfen, ob der Thread unterbrochen wurde
                HandleRequestFromServer.handleRequest();
            }
        } catch (Exception e) {
            System.out.println("Server antwortet nicht mehr");
                disconnect(); // Verbindung trennen
                Thread.currentThread().interrupt();
                System.out.println("Verbindung geschlossen.");

        }
    }


    public boolean connect(){
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
                        System.out.println ("Erfolgreich Verbunden mit " + socket.getInetAddress ( ).toString () + ":" + socket.getPort ());
                        socket.setSoTimeout(5000);
                        return true;
                    } else {
                        System.out.println("Server hat Verbindung abgelehnt.");
                        socket.close(); // Verbindung schließen, falls keine Bestätigung
                    }

                } catch (IOException e) {
                    System.out.println("Port " + port + " ist nicht verfügbar oder wird schon benutzt");
                } catch (Exception e) {
                    System.out.println("Lesen hat zu lange gedauert");

                }
            }

        System.out.println ("Erfolgreich Verbunden mit " + socket.getInetAddress ( ).toString () + ":" + socket.getPort ());


        return true;
    }

    public void startGameHandlerThread(){
        Thread handler = new Thread(this);
        handler.start();

    }

    public synchronized void disconnect() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                System.out.println("*****Verbindung zum Server geschlossen*****");
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Schließen der Verbindung: " + e.getMessage());
        }
    }


}
