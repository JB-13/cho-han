package networkControllerServer;

import gameLogic.Lobby;
import gameLogic.Player;
import networkControllerClient.HandleRequestFromServer;
import networkControllerClient.TCPClient;
import networkControllerServer.marshalling.TCPReceive;
import networkControllerServer.marshalling.TCPSend;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static gameLogic.Lobby.assignLobby;

public class TCPServer implements Runnable {
    private int port;
    private  TCPSend tcpSend;
    private  TCPReceive tcpRec;
    private  ServerSocket socket;
    private Socket connection;
    private boolean active = false;

    public TCPServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {

        try {
            socket = new ServerSocket(port, 50, InetAddress.getByName("0.0.0.0"));

            while (true) {
/*                if (socket.isClosed()){
                    socket = new ServerSocket(port);
                }*/

                System.out.println("Server is listening on TCP-Port: " + socket.getLocalPort());
                System.out.println("Waiting for connection to be established");

                Player player = null;
                try  {
                    connection = socket.accept();//hier wird gewartet
                    InputStream in = connection.getInputStream();
                    OutputStream out = connection.getOutputStream();

                    System.out.println("Connection has been established");
                    tcpRec = new TCPReceive(in);
                    tcpSend = new TCPSend(out);

                    connection.setSoTimeout(10000);
                    // Warten auf Login-Daten

                    player = null;
                    try {
                        String response = tcpRec.receiveCode();
                        if (!response.equals("CON")) {
                            System.out.println("Invalid request from client. Connection is closed.");
                            continue;
                        }

                        tcpSend.sendCode("ACK");


                        HandleRequestFromClient handler = HandleRequestFromClient.handleLogReg(this); //hier wird der Login empfangen
                        // Spieler-Objekt wurde erfolgreich erstellt, gehe zur Lobby-Zuweisung
                        if (handler == null) {
                            System.err.println("Error creating player object. Connection is closed");
                            connection.close();
                        }

                        player = handler.getPlayer();
                        player.setTCPServer(this);
                        tcpSend.sendCode("CBA");
                        tcpSend.sendDouble(player.getBalance()); //sende Information an Client, wie vieln guthaben er hat
                        Thread keepalive = new Thread(new KeepAlive((this)));
                        keepalive.start();
                        assignLobby(player); // Füge den Spieler zur Lobby hinzu
                        active= true;

                        while (active) {
                            handler.handleRequest(player.getServer());  // Verarbeite Anforderungen des Spielers
                        }


                    } /* catch (IllegalArgumentException e) {
                       // System.out.println("Login fehlgeschlagen für: " + handler.getPlayer());
                        tcpSend.sendString("Login fehlgeschlagen: " + e.getMessage());
                    }
                    */ catch (IOException e) {
                        System.err.println("Connection to Client lost: " + e.getMessage());
                        Lobby.removePlayerFromLobby(player);
                        connection.close();
                    } catch (Exception e) {
                        System.err.println("An unexpected error occurred: " + e.getMessage());
                        Lobby.removePlayerFromLobby(player);
                        connection.close();
                    }

                } catch (IOException e) {
                    System.err.println("Error processing connection: " + e.getMessage());
                    Lobby.removePlayerFromLobby(player);
                    connection.close();
                }
            }
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());

        }
    }

    public TCPReceive getTcpRec() {
        return tcpRec;
    }

    public TCPSend getTcpSend() {
        return tcpSend;
    }

    public void setActive(boolean active) {
        this.active = active;
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
            while (!Thread.currentThread().isInterrupted()) {
                server.getTcpSend().sendCode("ALI");
                Thread.sleep(2000);


            }
        } catch (IOException e) {
            System.err.println("Lost connection to Client: " + e.getMessage());
        } catch (InterruptedException e) {
            System.err.println("KeepAlive thread was interrupted: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred in the KeepAlive thread: " + e.getMessage());
        } finally {
            System.out.println("KeepAlive-Thread ended.");
            Thread.currentThread().interrupt();
        }
    }

}


