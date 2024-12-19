package networkControllerServer;

public class HandleRequestFromClient {

    public void handleRequest(){
        //String code = receiveCode();
        String code = ""; //Platzhalter
        switch (code){
            case "ODD":
                //double amount= receiveDouble();
                //TODO: gamelogic bet odd
                break;
            case "EVE":
                //double amount= receiveDouble();
                //TODO: gamelogic bet even
                break;
            case "NUM":
                //double amount= receiveDouble();
                //int number = receiveInt()
                //TODO: gamelogic bet number
                break;
            case "SKI":
                //TODO: gamelogic skip
            case "QUI":
                break;
            default:
                //TODO: gamelogic skip
                break;




        }
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
