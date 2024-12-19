package connection;

import gameLogic.Lobby;
import gameLogic.Player;

import java.util.ArrayList;
import java.util.List;

public class Server {
    public static List<Lobby> Lobbies = new ArrayList<>();
    private static int maxPlayersInLobby = 5;

    public static void main(String[] args) {

    }


    public static Lobby assignLobby(Player player) {
        // Entferne Lobbies ohne Spieler
        Lobbies.removeIf(lobby -> lobby.getLobbySize() == 0);

        // Prüfe, ob es eine freie Lobby gibt
        for (Lobby lobby : Lobbies) {
            if (lobby.getLobbySize() < maxPlayersInLobby) { // Freie Lobby gefunden
                lobby.connectPlayer(player);
                return lobby; // Rückgabe der zugewiesenen Lobby
            }
        }

        // Keine freie Lobby gefunden, erstelle eine neue
        Lobby newLobby = new Lobby();
        Thread lobbyThread = new Thread(newLobby);
        Lobbies.add(newLobby);
        newLobby.connectPlayer(player);
        lobbyThread.start();
        return newLobby; // Rückgabe der neu erstellten Lobby
    }




}
