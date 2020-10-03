package decoder;

import section.LogicalScreenDescriptor;

public class GifDecoder {
    private final String signature;
    private final int screenWidth;
    private final int screenHeight;
    public GifDecoder(byte[] bytes) {
        GifScanner scanner = new GifScanner(new GifBitStream(bytes));
        this.signature = decodeHeader(scanner.getHeader());
        int[] screen = decodeScreen(scanner.getLSD());
        this.screenWidth = screen != null ? screen[0] : 0;
        this.screenHeight = screen != null ? screen[1] : 0;
    }
    private String decodeHeader(byte[] header) {
        StringBuilder buffer = new StringBuilder();
        for(byte b: header) {
            buffer.append((char) b);
        }
        return buffer.toString();
    }
    private int[] decodeScreen(LogicalScreenDescriptor lsd) {
        return null;
    }
    public String getSignature() {
        return this.signature;
    }
    public int getScreenWidth() {
        return this.screenWidth;
    }
    public int getScreenHeight() {
        return this.screenHeight;
    }
}
