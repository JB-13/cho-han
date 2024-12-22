package networkControllerClient.marshalling;

import java.io.OutputStream;
import java.nio.ByteBuffer;

public class TCPSend {

    private OutputStream out;
    private int bufferLength = 256;
    private byte[] buffer;
    private ByteBuffer bbuf;

    public TCPSend(OutputStream out) {
        this.out = out;
        buffer = new byte[bufferLength];
        bbuf = ByteBuffer.wrap(buffer);
    }

    public void sendInt(int n) throws Exception {
        if (bbuf.position() > 0 || bbuf.limit() > 0) {
            bbuf.clear();
        }
        bbuf.putInt(n);
        bbuf.flip();
        writeBuffer();
    }

    public void sendDouble(double n) throws Exception {
        if (bbuf.position() > 0 || bbuf.limit() > 0) {
            bbuf.clear();
        }
        bbuf.putDouble(n);
        bbuf.flip();
        writeBuffer();
    }

    public void sendString(String n) throws Exception {
        if (bbuf.position() > 0 || bbuf.limit() > 0) {
            bbuf.clear();
        }
        byte[] nBytes = n.getBytes("UTF-8");
        bbuf.putInt(nBytes.length); // Länge vom String speichern
        bbuf.put(nBytes); // String als Byte-Array einfügen
        bbuf.flip();
        writeBuffer();
    }

    public void sendCode(String code) throws Exception {
        if (code.length() != 3) {
            throw new IllegalArgumentException("Code muss genau 3 Zeichen lang sein.");
        }
        if (bbuf.position() > 0 || bbuf.limit() > 0) {
            bbuf.clear();
        }
        bbuf.put(code.getBytes("UTF-8"));
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
