package networkControllerClient;

import java.util.Scanner;

import static networkControllerClient.TCPClient.tcpRec;
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
    double amount = 0.0;
    boolean validBet = false;
        double currentBalance = 0.0;


         currentBalance = HandleRequestFromServer.getBalance();




    while(!validBet) {
        try {
            System.out.println("how many points are you betting?");
            amount = sc.nextDouble();

            if (amount <= 0) {
                System.out.println("Bet must be greater than zero. Type again");
                continue;
            }

            if (amount > currentBalance) {
                System.out.println("You do not have enough balance to place this bet. Your balance is: " + currentBalance);
                continue;
            }


            validBet = true;
        } catch (Exception e) {
            System.out.println("Invalid Input");
            sc.nextLine();
        }
    }

        tcpSend.sendCode("ODD");
        tcpSend.sendDouble(amount);
        System.out.println("You placed an ODD bet of " + amount + " points.");

    }

    public static void betEven() throws Exception {
        double amount = 0.0;
        boolean validBet = false;
        double currentBalance = 0.0;



        currentBalance = HandleRequestFromServer.getBalance();



        while(!validBet) {
            try {
                System.out.println("how many points are you betting?");
                amount = sc.nextDouble();

                if (amount <= 0) {
                    System.out.println("Bet must be greater than zero. Type again");
                    continue;
                }


                if (amount > currentBalance) {
                    System.out.println("You do not have enough balance to place this bet. Your balance is: " + currentBalance);
                    continue;
                }


                validBet = true;
            } catch (Exception e) {
                System.out.println("Invalid Input");
                sc.nextLine();
            }
        }


        tcpSend.sendCode("EVE");
        tcpSend.sendDouble(amount);
        System.out.println("You placed an EVEN bet of " + amount + " points.");
    }
    public static void betNum() throws Exception {
        double amount = 0.0;
        int number = 0;
        double currentBalance = 0.0;



        currentBalance = HandleRequestFromServer.getBalance();


        // Eingabe des Wetteinsatzes
        while (true) {
            try {
                System.out.println("How many points are you betting?");
                amount = sc.nextDouble();

                // Überprüfen, ob der Spieler genügend Guthaben hat
                if (amount <= 0) {
                    System.out.println("Bet amount must be greater than 0. Please try again.");
                    continue;
                }


                if (amount > currentBalance) {
                    System.out.println("You do not have enough balance to place this bet. Your balance is: " + currentBalance);
                    continue;
                }

                break; // Gültige Eingabe
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.next(); // Ungültige Eingabe löschen
            }
        }

        // Eingabe der Augenzahl
        while (true) {
            try {
                System.out.println("Select your die count (2-12):");
                number = sc.nextInt();

                // Überprüfen, ob die Augenzahl gültig ist
                if (number < 2 || number > 12) {
                    System.out.println("Not a valid die count. Please select a number between 2 and 12.");
                    continue;
                }
                break; // Gültige Eingabe
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid integer between 2 and 12.");
                sc.next(); // Ungültige Eingabe löschen
            }
        }


        tcpSend.sendCode("NUM");
        tcpSend.sendDouble(amount);
        tcpSend.sendInt(number);

        System.out.println("Your bet has been placed: " + amount + " points on die count " + number);

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
