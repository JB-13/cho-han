package networkControllerServer.marshalling;

import java.io.InputStream;
import java.nio.ByteBuffer;

public class TCPReceive {

    InputStream in = null;
    int offset, readBytes, currentLength, length;
    byte [] buffer;
    ByteBuffer bbuf;
    int bufferLength = 256;
    public TCPReceive (InputStream in) {
        this.in = in;
        buffer = new byte [bufferLength];
        bbuf = ByteBuffer.wrap(buffer);
    } // TCP_Receive

    public int receiveInt ( ) throws Exception {
        System.out.println("ReceiveInt Methoden anfang");
        bbuf.clear();
        int bytesRead = 0;
        while (bytesRead < Integer.BYTES) {
            System.out.println("schleifen anfang in ReceiveInt");
            int result = in.read(buffer, bytesRead, Integer.BYTES - bytesRead); // wenn server und client receive machen und nicht senden, dann sind beide hier stuck
            System.out.println("nach in.read");
            bytesRead += result;
            System.out.println("bytes gelesen "+ bytesRead);


            if (result == -1) {
                System.out.println("ich habe kein int erhalten"); //wenn Client und server receiven und nur einer von den sendet, dann wird diese Zeile erreicht beim anderen erreicht
                return -1;
            }
        }
        return bbuf.getInt();


    } // receiveInt
    public String receiveString ( ) throws Exception{
        return "macht noch nichts";
    }
}

