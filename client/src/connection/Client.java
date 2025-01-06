package connection;


import networkControllerClient.SendRequestToServer;
import networkControllerClient.TCPClient;

import java.util.Scanner;

import static networkControllerClient.TCPClient.socket;


public class Client {
    static  boolean entrance = true;
   public static boolean gameloop = true;
    static String option = "";
    static Scanner sc = new Scanner(System.in);
    public static boolean isLoggedIn = false;

    public static void main(String[] args) {

        //aüßere Schleife für den ersten Screen
        while (entrance){
            System.out.println("choose your option by inputing a number");
            System.out.println("1) login/register");
            System.out.println("2) quit");
            option = sc.next();
            switch(option){
                case "1":
                   if(!SendRequestToServer.connectToServer()){
                       System.out.println("Connecting to Server failed");
                       break;
                   }
                    login();
                    if (isLoggedIn) {
                        gameLoopView();
                    }
                    break;
                case "2":
                    System.out.println("Program will be closed");
                    entrance = false;
                    break;
                default :
                    System.out.println("no valid input");
                    break;
            }
        }


    }
    //Menü für den Client
    public static void gameLoopView(){
        gameloop = true; //setze auf true falls nicht geschehen
        // if methoden aufruf zum connecten = true
        // else retrun;
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (!gameloop){
            return;
        }
        System.out.println("choose your option by inputing a number");
        System.out.println("1) bet odd");
        System.out.println("2) bet even");
        System.out.println("3) bet die count");
        System.out.println("4) skip round");
        System.out.println("5) exit lobby");
        while (gameloop){ //zweite schleife für das Spiel selbst
            if (socket.isClosed()) {
                gameloop = false;
                isLoggedIn = false;
                break;
            }
            option = sc.next();
                switch (option) {
                    case "1":
                        SendRequestToServer.betOdd();
                        break;
                    case "2":
                        SendRequestToServer.betEven();
                        break;
                    case "3":
                        SendRequestToServer.betNum();
                        break;
                    case "4":
                        SendRequestToServer.skipRound();
                        break;
                    case "5":
                        SendRequestToServer.quitLobby();
                        gameloop = false;
                        break;
                    default:
                        if (!socket.isClosed()){
                            System.out.println("no valid Option");
                        }
                        break;


                }



        }

    }
    //Login Menü für den Client
    public static void login(){
        System.out.println("Enter your username:");
        String username = sc.next();
        System.out.println("Enter your password:");
        String password = sc.next();

        try {
            isLoggedIn = SendRequestToServer.sendLoginRequest(username, password);
        } catch (Exception e) {
            System.out.println("Login failed: " + e.getMessage());
        }
    }

    //Menü loop für den Client
    public static void setGameloop(boolean bool){
        gameloop = bool;

    }



}

