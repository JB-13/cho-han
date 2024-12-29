package networkControllerServer;

import gameLogic.Player;
import networkControllerClient.TCPClient;
import networkControllerServer.marshalling.TCPReceive;
import networkControllerServer.marshalling.TCPSend;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static gameLogic.Lobby.assignLobby;

public class TCPServer implements Runnable {
    private int port;
    private  TCPSend tcpSend;
    private  TCPReceive tcpRec;
    private  ServerSocket socket;

    public TCPServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {

        try {
            ServerSocket socket = new ServerSocket(port);

            while (true) {
                System.out.println("Server hört auf TCP-Port: " + socket.getLocalPort ());
                System.out.println("Warten auf Verbindungsaufbau");
                try  {Socket connection = socket.accept(); //hier wird gewartet
                    InputStream in = connection.getInputStream();
                    OutputStream out = connection.getOutputStream();

                    System.out.println("Verbindungsaufbau hat stattgefunden");

                    tcpRec = new TCPReceive(in);
                    tcpSend = new TCPSend(out);
                    // Warten auf Login-Daten

                    // Überprüfe Login-Daten (muss noch implementiert werden)
                    try {
                        HandleRequestFromClient handler = HandleRequestFromClient.handleLogReg(this);

                        // Spieler-Objekt wurde erfolgreich erstellt, gehe zur Lobby-Zuweisung
                        Player player = handler.getPlayer();  // Hole das Spielerobjekt
                        player.setTCPServer(this);
                        assignLobby(player); // Füge den Spieler zur Lobby hinzu

                        while (true) {
                            handler.handleRequest(player.getServer());  // Verarbeite Anforderungen des Spielers
                            Thread.sleep(3000);       // Wartezeit zwischen den Anfragen
                        }

                    } catch (IllegalArgumentException e) {
                       // System.out.println("Login fehlgeschlagen für: " + handler.getPlayer());
                        tcpSend.sendString("Login fehlgeschlagen: " + e.getMessage());
                    }

                } catch (IOException | InterruptedException e) {
                    System.err.println("Fehler bei der Verarbeitung der Verbindung: " + e.getMessage());
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (IOException e) {
            System.err.println("Fehler beim Starten des Servers: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public TCPReceive getTcpRec() {
        return tcpRec;
    }

    public TCPSend getTcpSend() {
        return tcpSend;
    }

    public static void main(String[] args) throws Exception {
/*
        int port = 1234;
        ServerSocket socket = new ServerSocket (port);
        System.out.println ("Server hört auf TCP-Port: " + socket.getLocalPort ());
        System.out.println ("Warten auf Verbindungsaufbau");

        Socket connection = socket.accept ();

        InputStream in = connection.getInputStream();
        OutputStream out = connection.getOutputStream();
        System.out.println ("Verbindungsaufbau hat statt gefunden");
        tcpRec = new TCPReceive (in);
        tcpSend = new TCPSend (out);

        Player p1 = new Player("jan1", "1234");
        assignLobby(p1);

        HandleRequestFromClient handler = new HandleRequestFromClient(p1);
        while (true){
            handler.handleRequest();
            Thread.sleep(3000);

        }*/


/*        System.out.println(tcpRec.receiveCode());
        System.out.println(tcpRec.receiveDouble());
        System.out.println(tcpRec.receiveInt());
        System.out.println(tcpRec.receiveString());*/
/*        connection.close ();
        socket.close ();*/


    }


}
