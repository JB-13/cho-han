package gameLogic;

import networkControllerServer.SendRequestToClient;

import java.util.ArrayList;
import java.util.List;

public class Lobby implements Runnable {

    private List<Player> players = new ArrayList<>();
    private Dealer dealer = new Dealer();
    private boolean active = true;
    public static List<Lobby> Lobbies = new ArrayList<>();
    private static int maxPlayersInLobby = 5;
    private SendRequestToClient sender = new SendRequestToClient(dealer);

    public Lobby() {

    }

    public Lobby(Player player) {
        players.add(player);
    }

    public int getLobbySize() {
        return players.size();
    }

    public synchronized void connectPlayer (Player player) {
        if (!players.contains(player)) { // Prüfe, ob der Spieler bereits in der Liste ist
            players.add(player);
        } else {
            System.out.println("Player " + player.getName() + " ist bereits in der Lobby.");
        }
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


    public void startGame() throws InterruptedException {
        Thread.sleep(15000); // Wartezeit für Wetten
        int number = dealer.rollDice();
        for (Player player : players) {
           // System.out.println(player.getName());
            Bet bet = player.getBet();
            System.out.println("hol spieler Bet");


            if (bet.isSkip()) {
                System.out.println("bet is skipped");
                try {
                    sender.sendRoundOutcome(player, number);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                continue; // Spieler setzt die Runde aus
            }

            if ((bet.isEven() && dealer.isEven(number)) || (bet.isOdd() && dealer.isOdd(number))) {
                // Spieler gewinnt bei "Even" oder "Odd", erhält Gewinn
                player.updateBalance(bet.getAmount());
                System.out.println("gewettet: " + bet.getAmount());
            } else if (bet.getNumber() == number) {
                // Spieler gewinnt bei spezifischer Augenzahl, erhält doppelten Gewinn
                player.updateBalance(bet.getAmount() * 2);
                System.out.println("gewettet: " + bet.getAmount());
               // System.out.println("check die number");

            } else {
                // Spieler verliert, Einsatz wird abgezogen
                player.updateBalance(-bet.getAmount());
            }

            player.skipRound(); // nach dem Wetten soll der Player auf den Default gesetzt werden

            // System.out.println("vor dem Try Block");
            // Ergebnisse an den Spieler senden
            try {
               // System.out.println("information wird an spieler gesendet");
                sender.sendRoundOutcome(player, number);
            } catch (Exception e) {
                System.out.println("Fehler beim Senden der Ergebnisse an Spieler " + player.getName());
            }

        }
    }


    public void run() {
        while (active) {
            try {
                startGame();
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Lobby wurde unterbrochen");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void stopLobby() {
        active = false;
    }

}
