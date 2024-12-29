package networkControllerServer;


import gameLogic.Lobby;
import gameLogic.Player;
import networkControllerServer.marshalling.TCPReceive;



public class HandleRequestFromClient {

    private Player player; // Der aktuelle Spieler
    private Lobby lobby;   // Die zugewiesene Lobby

    public HandleRequestFromClient(Player player) {
        this.player = player;
        this.lobby = Lobby.assignLobby(player); // Spieler einer Lobby zuweisen
    }

    public void handleRequest(TCPReceive tcpRec) throws Exception {
        String code = tcpRec.receiveCode();
        switch (code) {
            case "ODD":
                double oddAmount = tcpRec.receiveDouble();
                player.betOdd(oddAmount);
                break;

            case "EVE":
                double evenAmount = tcpRec.receiveDouble();
                player.betEven(evenAmount);
                break;

            case "NUM":
                double numAmount = tcpRec.receiveDouble();
                int number = tcpRec.receiveInt();
                if (number < 2 || number > 12) {
                    System.out.println("Ungültige Augenzahl: " + number);
                    player.skipRound();
                } else {
                    player.betNumber(number, numAmount);
                }
                break;

            case "SKI":
                player.skipRound();
                break;

            case "QUI":
                player.skipRound(); // Spieler setzt aus
                // lobby.stopLobby();  // Lobby beenden, falls nötig
                break;

            default:
                System.out.println("Unbekannter Code: " + code);
                player.skipRound();
                break;
        }
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
