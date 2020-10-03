package section;

public class GifGraphicControlExtension extends GifExtension {
    private byte introduce;
    private byte identifier;
    private byte byteSize;
    private byte blockTerminator;
    private byte packedField;
    private byte delayTime;
    private byte transparentColorIndex;
    private static final class Builder {
        private byte introduce;
        private byte identifier;
        private byte byteSize;
        private byte blockTerminator;
        private byte packedField;
        private byte delayTime;
        private byte transparentColorIndex;
        public Builder setIntroduce(byte introduce) {
            this.introduce = introduce;
            return this;
        }
        public Builder setIdentifier(byte identifier) {
            this.identifier = identifier;
            return this;
        }
    }
    private GifGraphicControlExtension(Builder builder) {
        this.introduce = builder.introduce;
        this.identifier = builder.identifier;
        this.byteSize = builder.byteSize;
        this.blockTerminator = builder.blockTerminator;
        this.packedField = builder.packedField;
        this.delayTime = builder.delayTime;
        this.transparentColorIndex = builder.transparentColorIndex;
    }
}
