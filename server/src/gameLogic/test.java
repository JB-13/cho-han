package gameLogic;

public class test {

    public static void main(String[] args) throws InterruptedException {
        Player p1 = new Player("jan1", "1234");
        Player p2 = new Player("jan2", "1234");
        Player p3 = new Player("jan3", "1234");
        Player p4 = new Player("jan4", "1234");
        Lobby lobby = new Lobby();

        lobby.connectPlayer(p1);
        lobby.connectPlayer(p2);
        lobby.connectPlayer(p3);
        lobby.connectPlayer(p4);

        System.out.println("Wie große ist die Lobby: " + lobby.getLobbySize());
        System.out.println("Wie viele Punkte " + p1.getBalance());
        System.out.println("Wie viele Punkte " + p2.getBalance());
        System.out.println("Wie viele Punkte " + p3.getBalance());
        System.out.println("Wie viele Punkte " + p4.getBalance());

        p1.betEven(100);
        p2.betOdd(100);
        p3.betNumber(7,300);
        p4.skipRound();

        System.out.println("p1 " + p1.getBet().isEven());
        System.out.println("p2 " + p2.getBet().isOdd());
        System.out.println("p3 " + p3.getBet().getNumber());
        System.out.println("p4 " + p4.getBet().isSkip());

        lobby.startGame();

        System.out.println("Wie viele Punkte" + p1.getBalance());
        System.out.println("Wie viele Punkte" + p2.getBalance());
        System.out.println("Wie viele Punkte" + p3.getBalance());
        System.out.println("Wie viele Punkte" + p4.getBalance());






    }
}
