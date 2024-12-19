package networkControllerClient.marshalling;

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
        bbuf.flip();
        writeBuffer();


    } // sendInt


    public void sendDouble(double n) throws Exception {
        bbuf.clear();
        bbuf.putDouble(n);
        bbuf.flip();
        writeBuffer();
    } // sendDouble


    public void sendString(String n) throws Exception {
        bbuf.clear();
        byte[] nBytes = n.getBytes("UTF-8");
        bbuf.putInt(nBytes.length); // Länge vom String speichern
        bbuf.put(nBytes); // String als Byte-Array einfügen
        bbuf.flip();
        writeBuffer();
    } // sendString


    public void sendCode (String code) throws Exception{
        if (code.length() != 3) {
            throw new IllegalArgumentException("Code muss genau 3 Zeichen lang sein.");
        }
        bbuf.clear();
        bbuf.put(code.getBytes("UTF-8")); // Fügt die 3 Buchstaben ein
        bbuf.flip();
        writeBuffer();

    }

    private void writeBuffer() throws Exception {
        while (bbuf.hasRemaining()) {
            out.write(bbuf.get());
        }
        out.flush();
    }

}
