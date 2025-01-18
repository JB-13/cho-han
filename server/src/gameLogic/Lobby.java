package gameLogic;

import networkControllerServer.SendRequestToClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Lobby implements Runnable {
    private List<Player> players = new ArrayList<>();
    private Dealer dealer = new Dealer();
    private boolean active = true;
    public static List<Lobby> Lobbies = new ArrayList<>();
    private static int maxPlayersInLobby = 5;
    private SendRequestToClient sender = new SendRequestToClient(dealer);
    private int id = 0;
    private static final AtomicInteger idCounter = new AtomicInteger(0);


    public Lobby() {
        this.id = idCounter.incrementAndGet();
    }


    public Lobby(Player player) {
        this.id = idCounter.incrementAndGet();
        this.players.add(player);
    }

    public int getId() {
        return id;
    }

    //Spieler von einer Lobby entfernen
    public static synchronized boolean removePlayerFromLobby(Player player) {
        for (Lobby lobby : Lobbies) { // Durchsuche alle Lobbies
            if (lobby.players.contains(player)) { // Spieler gefunden
                lobby.players.remove(player); // Entferne den Spieler
                System.out.println("User " + player.getName() + " was removed from Lobby " + lobby.getId() + ".");

                // Optional: Entferne leere Lobbies
                if (lobby.getLobbySize() == 0) {
                    Lobbies.remove(lobby);
                    System.out.println("Lobby " + lobby.getId() + " was removed, because it was empty.");
                }
                return true; // Erfolgreich entfernt
            }
        }
        System.out.println("User " + player.getName() + " was not found in any Lobby.");
        return false; // Spieler war in keiner Lobby
    }

    //Spieler in einer Lobby suchen
    public boolean containsPlayer(String username) {
        return players.stream().anyMatch(player -> player.getName().equals(username));
    }


    //Lobbygröße ausgeben
    public int getLobbySize() {
        return players.size();
    }

    //Spieler zur Liste von Spielern hinzufügen
    public synchronized void connectPlayer (Player player) {
        players.add(player);
    }



    //Spieler einer Lobby zuweisen
    public static Lobby assignLobby(Player player) {
        // Überprüfe, ob der Spieler bereits einer Lobby zugeordnet ist
        for (Lobby lobby : Lobbies) {
            if (lobby.containsPlayer(player.getName())) {
                System.out.println("User " + player.getName() + " is already in a lobby.");
                try {
                    player.getServer().getTcpSend().sendCode("ERR");
                } catch (Exception e) {
                    System.err.println("Error Sending Error Code: " + e.getMessage());
                }
                return null; // Der Spieler ist bereits in einer Lobby
            }
        }

        // Prüfe, ob es eine freie Lobby gibt
        for (Lobby lobby : Lobbies) {
            if (lobby.getLobbySize() < maxPlayersInLobby) { // Freie Lobby gefunden
                lobby.connectPlayer(player);
                System.out.println("User " + player.getName() + " joined Lobby " + lobby.getId() + ".");
                return lobby; // Rückgabe der zugewiesenen Lobby
            }
        }

        // Keine freie Lobby gefunden, erstelle eine neue
        Lobby newLobby = new Lobby();
        Thread lobbyThread = new Thread(newLobby);
        Lobbies.add(newLobby);
        newLobby.connectPlayer(player);
        System.out.println("User " + player.getName() + " joined Lobby " + newLobby.getId() + ".");
        lobbyThread.start();
        return newLobby; // Rückgabe der neu erstellten Lobby
    }


    //Spiel innerhalb einer Lobby starten
    public void startGame(){
        try {
            Thread.sleep(15000); // Wartezeit für Wetten
        } catch (InterruptedException e) {
            System.err.println("Error during trhead sleep: " + e.getMessage());
        }
        if (players.isEmpty()) {
            // Entferne Lobbies ohne Spieler
            Lobbies.removeIf(lobby -> lobby.getLobbySize() == 0);
            Thread.currentThread().interrupt();
            return;
        }
        System.out.println("LobbyNr: " + getId());
        int number = dealer.rollDice();
        for (Player player : players) {

            Bet bet = player.getBet();



            if (bet.isSkip()) {
                try {
                    sender.sendRoundOutcome(player, number);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                continue; // Spieler setzt die Runde aus
            }

            if ((bet.isEven() && dealer.isEven(number))) {
                // Spieler gewinnt bei "Even", erhält Gewinn
                player.updateBalance(bet.getAmount());
                System.out.println("Player: " + player.getName() + " with even bet: " + bet.getAmount() + " and balance: " + player.getBalance());

            } else if ((bet.isOdd() && dealer.isOdd(number))) {
                // Spieler gewinnt bei "Odd", erhält Gewinn
                player.updateBalance(bet.getAmount());
                System.out.println("Player: " + player.getName() + " with odd bet: " + bet.getAmount() + " and balance: " + player.getBalance());


            } else if (bet.getNumber() == number) {
                // Spieler gewinnt bei spezifischer Augenzahl, erhält doppelten Gewinn
                player.updateBalance(bet.getAmount() * 3);
                System.out.println("Player: " + player.getName() + " with num " + bet.getNumber() + " bet: " + bet.getAmount() + " and balance: " + player.getBalance());


            } else {
                // Spieler verliert, Einsatz wird abgezogen
                player.updateBalance(-bet.getAmount());
                System.out.println("Player: " + player.getName() + " with losing bet: " + bet.getAmount() + " and balance: " + player.getBalance());
            }

            player.skipRound(); // nach dem Wetten soll der Player auf den Default gesetzt werden

            // Ergebnisse an den Spieler senden
            try {
                sender.sendRoundOutcome(player, number);
            } catch (Exception e) {
                System.out.println("Error sending results to players " + player.getName());
            }

        }
    }


    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                startGame();
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();

            }
        }
    }


}
