package connection;

import networkControllerClient.TCPClient;
import networkControllerServer.TCPServer;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {


        ExecutorService executor = Executors.newFixedThreadPool(15);
       // executor.execute(server);
       for (int i = 50000; i < 50010; i++) {
            executor.submit(new TCPServer(i));

        }


    }
}