package networkControllerServer;


import database.UserDatabase;
import gameLogic.Lobby;
import gameLogic.Player;

import java.io.IOException;

import static gameLogic.Lobby.assignLobby;


public class HandleRequestFromClient {

    private Player player; // Der aktuelle Spieler


    public HandleRequestFromClient(String username){
        this.player = UserDatabase.getPlayer(username);
        //assignLobby(player); // Spieler einer Lobby zuweisen (wird bereits in TCPServer getan
    }

    public Player getPlayer() {
        return player;
    }

    //Codes wie im Protokoll beschrieben
    public void handleRequest(TCPServer server) throws Exception {
        String code = server.getTcpRec().receiveCode();
        switch (code) {
            case "ODD":
                double oddAmount = server.getTcpRec().receiveDouble();
                player.betOdd(oddAmount);
                break;

            case "EVE":
                double evenAmount = server.getTcpRec().receiveDouble();
                player.betEven(evenAmount);
                break;

            case "NUM":
                double numAmount = server.getTcpRec().receiveDouble();
                int number = server.getTcpRec().receiveInt();
                if (number < 2 || number > 12) {
                    System.out.println("Invalid number: " + number);
                    player.skipRound();
                } else {
                    player.betNumber(number, numAmount);
                }
                break;

            case "SKI":
                player.skipRound();
                break;

            case "QUI":
                player.skipRound();
                Lobby.removePlayerFromLobby(player);
                server.setActive(false);
                server.closeConnection();
                break;
            case "ALI":break;

            default:
                System.out.println("Invalid Code: " + code);
                player.skipRound();
                break;
        }
    }


    //Login Informationen vom Client verarbeiten
    public static HandleRequestFromClient handleLogReg(TCPServer server) throws Exception {
        String code = server.getTcpRec().receiveCode();
        if (code.equals("LOG")) {
            String loginUsername = server.getTcpRec().receiveString();
            String loginPassword = server.getTcpRec().receiveString();
            try {
                if (UserDatabase.validateLogin(loginUsername, loginPassword)) {
                    server.getTcpSend().sendString("Login successful");
                    return new HandleRequestFromClient(loginUsername);
                } else {
                    server.getTcpSend().sendString("Invalid credentials");
                }
            } catch (Exception e) {
                server.getTcpSend().sendString("Login failed: " + e.getMessage());
            }
        }
        return null;
    }

}




/*
Player Actions (off server)
===========================
connect to server: CON
===========================

Player Action (on server)
===========================
odd: ODD | amount (double)
even: EVE | amount (double)
bet number: NUM | amount (double) | 2-12(int)
skip: SKI
quit: from sever QUI
===========================*/
