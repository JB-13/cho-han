package connection;

import gameLogic.Lobby;
import gameLogic.Player;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private static List<Lobby> Lobbies = new ArrayList<>();
    private static int maxPlayersInLobby = 5;

    public static void main(String[] args) {

    }


    public static Lobby assignLobby(Player player) {
        if (Lobbies.isEmpty()) {
            Lobby newLobby = new Lobby();
            Lobbies.add(newLobby);
            newLobby.connectPlayer(player);
            return newLobby; // Rückgabe der neu erstellten Lobby
        }
        for (Lobby lobby : Lobbies) {
            if (lobby.getLobbySize() < maxPlayersInLobby) { // Freie Lobby gefunden
                lobby.connectPlayer(player);
                return lobby; // Rückgabe der zugewiesenen Lobby
            }
        }
        // Alle Lobbys sind voll, neue erstellen
        Lobby newLobby = new Lobby();
        Lobbies.add(newLobby);
        newLobby.connectPlayer(player);
        return newLobby; // Rückgabe der neu erstellten Lobby
    }



}
