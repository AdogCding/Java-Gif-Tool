package decoder;

import section.*;

import java.util.List;

public class GifScanner {
    byte[] header;  // header
    LogicalScreenDescriptor logicalScreenDescriptor; // logical screen descriptor
    GlobalColorTable globalColorTable = null; // global color table, optional
    GifExtension plainTextExtension = null;
    GifExtension applicationExtension = null;
    GifExtension CommentExtension = null;
    List<GifImageFrame> imageFrames = null;
    GifScanner(GifBitStream bitStream) {
        this.header = parseHeader(bitStream);
        this.logicalScreenDescriptor = parseLSD(bitStream);
        if ((logicalScreenDescriptor.getPackedField() >> 7 & 1) == 1) {
            this.globalColorTable = parseGCT(bitStream);
        }
        Byte current, next;
        while ((current = bitStream.nextByte(0)) != null) {
            if (current == (byte)0x21) {
                GifExtension extension = parseExtension(bitStream);
                if(extension instanceof GifGraphicControlExtension) {
                    current = bitStream.nextByte(0);
                    if (current == (byte)0x21) {
                        this.plainTextExtension = parseExtension(bitStream);
                    } else {
                        ImageDescriptor imageDescriptor = parseImageDescriptor(bitStream);
                        LocalColorTable localColorTable = null;
                        if (imageDescriptor.hasColorTable()) {
                            localColorTable = parseLocalColorTable(bitStream);
                        }
                        ImageData imageData = parseImageData(bitStream);
                    }
                }
            }
        }
    }

    private ImageData parseImageData(GifBitStream bitStream) {
        return null;
    }

    // header
    public byte[] getHeader() {
        return this.header;
    }
    private byte[] parseHeader(GifBitStream bitStream) {
        int headerSize = 6;
        byte[] header = new byte[headerSize];
        for(int i = 0; i < headerSize; i++) {
            header[i] = bitStream.nextByte();
        }
        return header;
    }
    // lsd
    public LogicalScreenDescriptor getLSD() {
        return this.logicalScreenDescriptor;
    }
    private LogicalScreenDescriptor parseLSD(GifBitStream bitStream) {
        int lsdSize = 6;
        byte[] lsd = new byte[lsdSize];
        for(int i = 0; i < lsdSize; i++) {
            lsd[i] = bitStream.nextByte();
        }
        return new LogicalScreenDescriptor(lsd);
    }

    // GCT
    private GlobalColorTable parseGCT(GifBitStream bitStream) {
        return null;
    }

    // extensions
    private GifExtension parseExtension(GifBitStream bitStream) {
        byte introduce = bitStream.nextByte();
        byte identifier = bitStream.nextByte();
        if (identifier == (byte)0xF9) {
            // graphic control extension

        } else if (identifier == (byte)0x01) {
            // plain text extension
        } else if (identifier == (byte)0xFF) {
            // application extension
        } else if (identifier == (byte)0xFE) {
            // comment extension
        }
        return null;
    }

    private GlobalColorTable parseGlobalColorTable(GifBitStream bitStream) {
        return null;
    }
    private ImageDescriptor parseImageDescriptor(GifBitStream bitStream) {
        return null;
    }
    private LocalColorTable parseLocalColorTable(GifBitStream bitStream) {
        return null;
    }
}
