package networkControllerClient;


import networkControllerServer.marshalling.TCPReceive;
import networkControllerServer.marshalling.TCPSend;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClient {

    public static void main(String[] args) throws Exception {
        int port = 1234;
        InetAddress address = InetAddress.getByName ("127.0.0.1");
        Socket socket = new Socket (address, port);

        OutputStream out = socket.getOutputStream ( );
        InputStream in = socket.getInputStream ( );
        System.out.println ("Verbunden mit " + socket.getInetAddress ( ).toString () + ":" + socket.getPort ());

        TCPSend tcpSend = new TCPSend (out);
        TCPReceive tcpRec = new TCPReceive (in);

/*        System.out.println("client erhält");
        System.out.println( tcpRec.receiveInt ());


        System.out.println("Client sendet 692 und 69");
        tcpSend.sendInt(692);
        tcpSend.sendInt(69);*/
        socket.close ();
    }


}
