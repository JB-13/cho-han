package networkControllerServer.marshalling;

import java.io.OutputStream;
import java.nio.ByteBuffer;

public class TCPSend {

    OutputStream out = null;
    int bufferLength = 256;
    byte [] buffer;
    ByteBuffer bbuf;
    int writeBytes;
    public TCPSend (OutputStream out) {
        this.out = out;
        buffer = new byte [bufferLength];
        bbuf = ByteBuffer.wrap(buffer);
    } // TCP_Send
    public void sendInt (int n) throws Exception {
        bbuf.clear(); //leere den buffer
        bbuf.putInt(n);
        bbuf.flip(); //read only mode
        while (bbuf.hasRemaining()) {
            out.write(bbuf.get()); // Byte für Byte schreiben, bis der Buffer leer ist
        }
        out.flush();


    } // sendInt
    public void sendString (String str) throws Exception{

    } // sendString
}
