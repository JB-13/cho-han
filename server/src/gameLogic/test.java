package gameLogic;

import connection.Server;

import java.util.ArrayList;
import java.util.List;

public class test {

    private static List<Lobby> assignedLobbies = new ArrayList<>();

    private static void assignAndAddLobby(Player player) {
        Lobby lobby = Server.assignLobby(player);
        if (!assignedLobbies.contains(lobby)) {
            assignedLobbies.add(lobby);
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Player p1 = new Player("jan1", "1234");
        Player p2 = new Player("jan2", "1234");
        Player p3 = new Player("jan3", "1234");
        Player p4 = new Player("jan4", "1234");
        Player p5 = new Player("jan5", "1234");
        Player p6 = new Player("jan6", "1234");
        Player p7 = new Player("jan7", "1234");
        Player p8 = new Player("jan8", "1234");
        Player p9 = new Player("jan9", "1234");
        Player p10 = new Player("jan10", "1234");
        Player p11 = new Player("jan11", "1234");
        Player p12 = new Player("jan12", "1234");


        // Speichere die Lobbys in einer Liste, um darauf zugreifen zu können
        System.out.println(assignedLobbies.size());
        assignAndAddLobby(p1);
        assignAndAddLobby(p2);
        assignAndAddLobby(p3);
        assignAndAddLobby(p4);
        assignAndAddLobby(p5);
        System.out.println(assignedLobbies.size());
        assignAndAddLobby(p6);
        assignAndAddLobby(p7);
        assignAndAddLobby(p8);
        assignAndAddLobby(p9);
        assignAndAddLobby(p10);
        System.out.println(assignedLobbies.size());
        assignAndAddLobby(p11);
        assignAndAddLobby(p12);
        System.out.println(assignedLobbies.size());


        System.out.println("Wie groß ist die Lobby: " + assignedLobbies.get(0).getLobbySize());
        System.out.println("Wie groß ist die Lobby2: " + assignedLobbies.get(1).getLobbySize());
        System.out.println("Wie groß ist die Lobby2: " + assignedLobbies.get(2).getLobbySize());
        System.out.println("Wie viele Punkte hat p1 " + p1.getBalance());
        System.out.println("Wie viele Punkte hat p12 " + p12.getBalance());

        p1.betEven(100);
        p2.betOdd(100);
        p3.betNumber(7,300);
        p4.skipRound();
        p5.betEven(100);
        p6.betOdd(100);
        p7.betEven(100);
        p8.betEven(100);
        p9.betOdd(100);
        p10.betNumber(7,300);
        p11.skipRound();
        p12.betEven(100);

        System.out.println("p1 " + p1.getBet().isEven());
        System.out.println("p2 " + p2.getBet().isOdd());
        System.out.println("p3 " + p3.getBet().getNumber());
        System.out.println("p4 " + p4.getBet().isSkip());
        System.out.println("p5 " + p5.getBet().isEven());
        System.out.println("p6 " + p6.getBet().isOdd());
        System.out.println("p7 " + p7.getBet().isEven());
        System.out.println("p8 " + p8.getBet().isEven());
        System.out.println("p9 " + p9.getBet().isOdd());
        System.out.println("p10 " + p10.getBet().getNumber());
        System.out.println("p11 " + p11.getBet().isSkip());
        System.out.println("p12 " + p12.getBet().isEven());

        System.out.println();


        assignedLobbies.get(0).startGame();

        System.out.println("Wie viele Punkte" + p1.getBalance());
        System.out.println("Wie viele Punkte" + p2.getBalance());
        System.out.println("Wie viele Punkte" + p3.getBalance());
        System.out.println("Wie viele Punkte" + p4.getBalance());
        System.out.println("Wie viele Punkte" + p5.getBalance());

        System.out.println();

        assignedLobbies.get(1).startGame();
        System.out.println("Wie viele Punkte" + p6.getBalance());
        System.out.println("Wie viele Punkte" + p7.getBalance());
        System.out.println("Wie viele Punkte" + p8.getBalance());
        System.out.println("Wie viele Punkte" + p9.getBalance());
        System.out.println("Wie viele Punkte" + p10.getBalance());

        System.out.println();

        assignedLobbies.get(2).startGame();
        System.out.println("Wie viele Punkte" + p11.getBalance());
        System.out.println("Wie viele Punkte" + p12.getBalance());






    }


}
