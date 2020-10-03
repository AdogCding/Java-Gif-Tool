package decoder;

public class GifBitStream {
    private int pointer;
    private byte[] bytes;
    GifBitStream(byte[] file) {
        pointer = 0;
        bytes = file.clone();
    }

    public Byte nextByte() {
        Byte nByte = null;
        if (pointer < bytes.length) {
            nByte = bytes[pointer++];
        }
        return nByte;
    }

    public Byte nextByte(int i) {
        Byte nByte = null;
        if (pointer + i >= 0 && pointer + i < bytes.length)
            nByte = bytes[pointer+i];
        return nByte;
    }

    public int locate() {
        return pointer;
    }
}
