package networkControllerClient;

import java.util.Scanner;
import static networkControllerClient.TCPClient.tcpSend;


public class SendRequestToServer {
    static Scanner sc = new Scanner(System.in);
    public static TCPClient tcpClient = new TCPClient();
    public static boolean connectToServer(){ //boolean weil, man wissen soll, ob es geklappt hat
/*        Thread thread = new Thread(tcpClient);
        thread.start();*/

        try {
            tcpClient.connect();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return true;
    }


    public static void betOdd() throws Exception {
        System.out.println("how many points are you betting?");
        double amount = sc.nextDouble();
        //TODO: überprüfen, ob der Client überhaupt so viele Punkte hat.
        tcpSend.sendCode("ODD");
        tcpSend.sendDouble(amount);
        //HandleRequestFromServer.handleRoundOutcome();


    }

    public static void betEven() throws Exception {
        System.out.println("how many points are you betting?");
        double amount = sc.nextDouble();
        //TODO: überprüfen, ob der Client überhaupt so viele Punkte hat.
        tcpSend.sendCode("EVE");
        tcpSend.sendDouble(amount);
        //HandleRequestFromServer.handleRoundOutcome();
    }
    public static void betNum() throws Exception {
        System.out.println("how many points are you betting");
        double amount = sc.nextDouble();
        //TODO: überprüfen, ob der Client überhaupt so viele Punkte hat.
        System.out.println("select your die count (2-12)");
        int number = sc.nextInt();
        if (number < 2 && number > 12){ //wenn augenzahl kleiner 2 oder größer 12 ist
            System.out.println("not a valid die count. Abort player action");
        }

        tcpSend.sendCode("NUM");
        tcpSend.sendDouble(amount);
        tcpSend.sendInt(number);
        //HandleRequestFromServer.handleRoundOutcome();

    }
    public static void skipRound() throws Exception {
        tcpSend.sendCode("SKI");
     //   HandleRequestFromServer.handleRoundOutcome();

    }

    public static void quitLobby() throws Exception {
        tcpSend.sendCode("QUI");
        tcpClient.disconnect();
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
