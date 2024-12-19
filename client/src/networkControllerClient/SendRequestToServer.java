package networkControllerClient;

import java.util.Scanner;

public class SendRequestToServer {
    static Scanner sc = new Scanner(System.in);
    public  TCPClient tcpClient = new TCPClient();
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
        tcpClient.tcpSend.sendCode("ODD");
        sendCode("ODD");
        sendDouble(amount);

    }

    public static void betEven(){
        System.out.println("how many points are you betting?");
        double amount = sc.nextDouble();
        //TODO: überprüfen, ob der Client überhaupt so viele Punkte hat.
        sendCode("EVE");
        sendDouble(amount);
    }
    public static void betNum(){
        System.out.println("how many points are you betting");
        double amount = sc.nextDouble();
        //TODO: überprüfen, ob der Client überhaupt so viele Punkte hat.
        System.out.println("select your die count (2-12)");
        int number = sc.nextInt();
        if (number < 2 && number > 12){ //wenn augenzahl kleiner 2 oder größer 12 ist
            System.out.println("not a valid die count. Abort player action");
        }

        sendCode("NUM");
        sendDouble(amount);
        sendInt();

    }
    public static void skipRound(){
        sendCode("SKI");

    }

    public static void quitLobby(){
        sendCode("QUI");

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
