package section;

import java.util.Arrays;

public class LogicalScreenDescriptor {
    private final byte[] bytes;
    private final byte[] canvasHeight;
    private final byte[] canvasWidth;
    private final byte packedField;
    private final byte backGroundColorIndex;
    private final byte PixelAspectRatio;

    public byte[] getCanvasHeight() {
        return canvasHeight;
    }

    public byte[] getCanvasWidth() {
        return canvasWidth;
    }

    public byte getPackedField() {
        return packedField;
    }

    public byte getBackGroundColorIndex() {
        return backGroundColorIndex;
    }

    public byte getPixelAspectRatio() {
        return PixelAspectRatio;
    }

    public LogicalScreenDescriptor(byte[] bytes) {
        this.bytes = bytes.clone();
        this.canvasWidth = Arrays.copyOfRange(bytes, 0, 2);
        this.canvasHeight = Arrays.copyOfRange(bytes, 2, 4);
        this.packedField = bytes[4];
        this.backGroundColorIndex = bytes[5];
        this.PixelAspectRatio = bytes[6];
    }
}
