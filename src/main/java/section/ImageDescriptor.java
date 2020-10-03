package section;

public class ImageDescriptor {
    private byte[] bytes;
    ImageDescriptor(byte[] bytes) {
        this.bytes = bytes;
    }

    public boolean hasColorTable() {
        return false;
    }
}
