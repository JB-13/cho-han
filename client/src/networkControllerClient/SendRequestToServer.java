package networkControllerClient;

import connection.Client;

import java.util.Scanner;

import static networkControllerClient.TCPClient.*;


public class SendRequestToServer {
    static Scanner sc = new Scanner(System.in);
    public static TCPClient tcpClient = new TCPClient();
    public static KeepAlive keepAlive = new KeepAlive();
    public static boolean connectToServer(){ //boolean weil, man wissen soll, ob es geklappt hat
/*        Thread thread = new Thread(tcpClient);
        thread.start();*/

        try {
            if (tcpClient.connect()){
                return true;
            } else {
                return false;
            }
            //keepAlive.startGameHandlerThread(); //nach erfolgreichem connect, wird der keepAlive thread gestartet
        } catch (Exception e) {
            System.err.println("Error connecting to server: " + e.getMessage());
            return false;

        }

    }

    public static boolean sendLoginRequest(String username, String password) {
        String response="";
        System.out.println("Send login request with username: " + username);
        try {
            tcpSend.sendCode("LOG");
            tcpSend.sendString(username);
            tcpSend.sendString(password);
            response = tcpRec.receiveString();
        } catch (Exception e) {
            System.err.println("Error sending Login information: " + e.getMessage());
            return false;
        }


        System.out.println("Response from Server: " + response);
        if ("Login successful".equals(response)) {
            // Verbindung zum Spiel starten, wenn erfolgreich
            System.out.println("Login successful");
            try {
                HandleRequestFromServer.handleRequest(); //für CBA
            } catch (Exception e) {
                System.err.println("Error receiving from Server: " + e.getMessage());
                return false;
            }
            tcpClient.startGameHandlerThread(); // nach dem Login werden Keep ALive Messages vom Server empfangen
            keepAlive.startGameHandlerThread();
            return true;
        } else {
            System.out.println("Login failed.");
            return false;
        }

    }


    public static void betOdd()  {
        double amount = 0.0;
        boolean validBet = false;
        double currentBalance = 0.0;


        currentBalance = HandleRequestFromServer.getBalance();

        if (currentBalance <= 0) {
            System.out.println("You are bankrupt. Your balance is: " + currentBalance + ". You can only Skip(4) or Exit(5)");
            return;
        }




        while(!validBet) {
            try {
                if (socket.isClosed()) {
                    Client.gameloop = false;
                    Client.isLoggedIn = false;
                    return;
                }
                System.out.println("how many points are you betting?");
                amount = sc.nextDouble();
                currentBalance = HandleRequestFromServer.getBalance();

                if (currentBalance <= 0) {
                    System.out.println("You are bankrupt. Your balance is: " + currentBalance + ". You can only Skip(4) or Exit(5)");
                    return;
                }

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
                if (!socket.isClosed()){
                    System.out.println("Invalid Input");
                    sc.nextLine();
                }


            }
        }

        try {
            tcpSend.sendCode("ODD");
            tcpSend.sendDouble(amount);
        } catch (Exception e) {
            System.err.println("Error sending to Server: " + e.getMessage());
            Client.setGameloop(false);

        }

        System.out.println("You placed an ODD bet of " + amount + " points.");

    }

    public static void betEven()  {
        double amount = 0.0;
        boolean validBet = false;
        double currentBalance = 0.0;



        currentBalance = HandleRequestFromServer.getBalance();

        if (currentBalance <= 0) {
            System.out.println("You are bankrupt. Your balance is: " + currentBalance + ". You can only Skip(4) or Exit(5)");
            return;
        }



        while(!validBet) {
            try {
                System.out.println("how many points are you betting?");
                if (socket.isClosed()) {
                    Client.gameloop = false;
                    Client.isLoggedIn = false;
                    return;
                }
                amount = sc.nextDouble();
                currentBalance = HandleRequestFromServer.getBalance();

                if (currentBalance <= 0) {
                    System.out.println("You are bankrupt. Your balance is: " + currentBalance + ". You can only Skip(4) or Exit(5)");
                    return;
                }

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
                if (!socket.isClosed()){
                    System.out.println("Invalid Input");
                    sc.nextLine();
                }
            }
        }


        try {
            tcpSend.sendCode("EVE");
            tcpSend.sendDouble(amount);
        } catch (Exception e) {
            System.err.println("Error sending to Server: " + e.getMessage());
            Client.setGameloop(false);
        }

        System.out.println("You placed an EVEN bet of " + amount + " points.");
    }
    public static void betNum() {
        double amount = 0.0;
        int number = 0;
        double currentBalance = 0.0;



        currentBalance = HandleRequestFromServer.getBalance();

        if (currentBalance <= 0) {
            System.out.println("You are bankrupt. Your balance is: " + currentBalance + ". You can only Skip(4) or Exit(5)");
            return;
        }


        // Eingabe des Wetteinsatzes
        while (true) {
            try {
                if (socket.isClosed()) {
                    Client.gameloop = false;
                    Client.isLoggedIn = false;
                    return;
                }
                System.out.println("How many points are you betting?");
                amount = sc.nextDouble();
                currentBalance = HandleRequestFromServer.getBalance();

                if (currentBalance <= 0) {
                    System.out.println("You are bankrupt. Your balance is: " + currentBalance + ". You can only Skip(4) or Exit(5)");
                    return;
                }

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
                if (!socket.isClosed()){
                    System.out.println("Invalid Input");
                    sc.nextLine(); // Ungültige Eingabe löschen
                }
            }
        }

        // Eingabe der Augenzahl
        while (true) {
            try {
                if (socket.isClosed()) {
                    Client.gameloop = false;
                    Client.isLoggedIn = false;
                    return;
                }
                System.out.println("Select your die count (2-12):");
                number = sc.nextInt();
                currentBalance = HandleRequestFromServer.getBalance();

                if (currentBalance <= 0) {
                    System.out.println("You are bankrupt. Your balance is: " + currentBalance + ". You can only Skip(4) or Exit(5)");
                    return;
                }

                // Überprüfen, ob die Augenzahl gültig ist
                if (number < 2 || number > 12) {
                    System.out.println("Not a valid die count. Please select a number between 2 and 12.");
                    continue;
                }
                break; // Gültige Eingabe
            } catch (Exception e) {
                if (!socket.isClosed()){
                    System.out.println("Invalid input. Please enter a valid integer between 2 and 12.");
                    sc.next(); // Ungültige Eingabe löschen
                }
            }
        }


        try {
            tcpSend.sendCode("NUM");
            tcpSend.sendDouble(amount);
            tcpSend.sendInt(number);
        } catch (Exception e) {
            System.err.println("Error sending to Server: " + e.getMessage());
            Client.setGameloop(false);
        }


        System.out.println("Your bet has been placed: " + amount + " points on die count " + number);

    }
    public static void skipRound()  {
        try {
            if (socket.isClosed()) {
                Client.gameloop = false;
                Client.isLoggedIn = false;
                return;
            }
            tcpSend.sendCode("SKI");
        } catch (Exception e) {
            System.err.println("Error sending to Server: " + e.getMessage());
            Client.setGameloop(false);
        }
        //   HandleRequestFromServer.handleRoundOutcome();

    }

    public static void quitLobby() {
        try {
            if (socket.isClosed()) {
                Client.gameloop = false;
                Client.isLoggedIn = false;
                return;
            }
            tcpSend.sendCode("QUI");
        } catch (Exception e) {
            System.err.println("Error sending to Server: " + e.getMessage());
            Client.setGameloop(false);
        }
        TCPClient.handler.interrupt();
        KeepAlive.keepalive.interrupt();
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
