package connection;

import networkControllerClient.TCPClient;
import networkControllerServer.TCPServer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(5);
       // executor.execute(server);
       for (int i = 0; i < 3; i++) {
            executor.submit(new TCPServer(i));

        }


    }
}