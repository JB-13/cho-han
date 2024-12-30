package networkControllerServer;

import gameLogic.Player;
import networkControllerClient.HandleRequestFromServer;
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
             socket = new ServerSocket(port);

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
                    String response = tcpRec.receiveCode();
                    if (response.equals("CON")){
                        tcpSend.sendCode("ACK");
                    } else{
                        continue;
                    }

                    try {
                        HandleRequestFromClient handler = HandleRequestFromClient.handleLogReg(this); //hier wird der Login empfangen
                        // Spieler-Objekt wurde erfolgreich erstellt, gehe zur Lobby-Zuweisung
                        if (handler.equals(null)){

                            connection.close();
                        } else{
                        Player player = handler.getPlayer();  // Hole das Spielerobjekt
                        player.setTCPServer(this);
                        tcpSend.sendCode("CBA");
                        tcpSend.sendDouble(player.getBalance()); //sende Information an Client, wie vieln guthaben er hat
                            Thread keepalive = new Thread(new KeepAlive((this)));
                            keepalive.start();
                        assignLobby(player); // Füge den Spieler zur Lobby hinzu

                        while (true) {
                            handler.handleRequest(player.getServer());  // Verarbeite Anforderungen des Spielers

                        }
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

}

class KeepAlive implements Runnable{
    private TCPServer server;
    public KeepAlive(TCPServer server) {
        this.server = server;
    }
    @Override
    public void run() {
        try {
            while (true) {
                server.getTcpSend().sendCode("ALI");
                Thread.sleep(2000);


            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
