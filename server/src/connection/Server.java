package connection;

import gameLogic.Lobby;
import gameLogic.Player;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private List<Lobby> Lobbies = new ArrayList<>();
    private int maxPlayersInLobby = 5;

    public static void main(String[] args) {

    }


    public void assignLobby(Player player) {
        int i = 0;
        for (Lobby lobby : Lobbies) {
            if (Lobbies.size() == i) { //alle lobbies sind voll, neue wird erstellt und Player hinzugefügt
                Lobby lobby2 = new Lobby();
                lobby2.connectPlayer(player);
            }
            else if(lobby.getLobbySize() < maxPlayersInLobby){ //freie lobby gefunden
                lobby.connectPlayer(player);
                break;
            } else {
                i++;
            }
        }
    }

}
