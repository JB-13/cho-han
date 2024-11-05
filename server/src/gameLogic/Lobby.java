package gameLogic;

import java.util.ArrayList;
import java.util.List;

public class Lobby {

    private List<Player> players = new ArrayList<>();
    private Dealer dealer = new Dealer();

    public Lobby() {

    }

    public Lobby(Player player) {
        players.add(player);
    }

    public int getLobbySize() {
        return players.size();
    }

    public void connectPlayer (Player player) {
            players.add(player);
    }

    public void startGame() throws InterruptedException {
        Thread.sleep(15000);  // Wartezeit für alle Spieler, ihre Wetten zu platzieren
        int number = dealer.rollDice();

        for (Player player : players) {
            Bet bet = player.getBet();

            if (bet.isSkip()) {
                continue; // Spieler setzt die Runde aus
            }

            if ((bet.isEven() && dealer.isEven(number)) || (bet.isOdd() && dealer.isOdd(number))) {
                // Spieler gewinnt bei "Even" oder "Odd", erhält Gewinn
                player.updateBalance(bet.getAmount());
            } else if (bet.getNumber() == number) {
                // Spieler gewinnt bei spezifischer Augenzahl, erhält doppelten Gewinn
                player.updateBalance(bet.getAmount() * 2);
            } else {
                // Spieler verliert, Einsatz wird abgezogen
                player.updateBalance(-bet.getAmount());
            }
        }
    }

}
