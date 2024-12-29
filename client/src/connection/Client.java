package connection;

import networkControllerClient.SendRequestToServer;

import java.util.Scanner;

public class Client {
    static  boolean entrance = true;
    static boolean gameloop = true;
    static String option = "";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        //aüßere Schleife für den ersten Screen
        while (entrance){
            System.out.println("choose your option by inputing a number");
            System.out.println("1) connect to game lobby");
            System.out.println("2) quit");
            option = sc.next();
            switch(option){
                case "1":
                    SendRequestToServer.connectToServer();//einfügen der verifizierung
                    gameLoopView();
                    break;
                case "2":
                    System.out.println("Programm soll beendet werden");
                    entrance = false;
                    break;
                default :
                    System.out.println("keine Valide eingabe");
                    break;
            }
        }


    }

    public static void gameLoopView() throws Exception {
        gameloop = true; //setze auf true falls nicht geschehen
        // if methoden aufruf zum connecten = true
        // else retrun;
        System.out.println("choose your option by inputing a number");
        System.out.println("1) bet odd");
        System.out.println("2) bet even");
        System.out.println("3) bet die count");
        System.out.println("4) skip round");
        System.out.println("5) exit lobby");
        while (gameloop){ //zweite schleife für das Spiel selbst
            option = sc.next();
            switch (option){
                case "1":
                    SendRequestToServer.betOdd(); break;
                case "2":
                    SendRequestToServer.betEven(); break;
                case "3":
                    SendRequestToServer.betNum(); break;
                case "4":
                    SendRequestToServer.skipRound(); break;
                case "5":
                    SendRequestToServer.quitLobby(); gameloop = false; break;
                default:
                    System.out.println("ungültige Option");/* SendRequestToServer.skipRound(); */break;


            }



        }

    }
}

