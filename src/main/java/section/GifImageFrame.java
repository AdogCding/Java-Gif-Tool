package section;


public class GifImageFrame {
    GifExtension GCE;   // graphic control extension
    ImageDescriptor imageDescriptor;
    LocalColorTable localColorTable;
    ImageData imageData;
    GifImageFrame(Builder builder) {
        this.GCE  = builder.GCE;
        this.imageData = builder.imageData;
        this.imageDescriptor = builder.imageDescriptor;
        this.localColorTable = builder.localColorTable;
    }
    public final static class Builder {
        GifExtension GCE;   // graphic control extension
        ImageDescriptor imageDescriptor;
        LocalColorTable localColorTable;
        ImageData imageData;
        public Builder setGCE(GifExtension gce) {
            this.GCE = gce;
            return this;
        }
        public Builder setImageDescriptor(ImageDescriptor imageDescriptor) {
            this.imageDescriptor = imageDescriptor;
            return this;
        }
        public Builder setLocalColorTable(LocalColorTable localColorTable) {
            this.localColorTable = localColorTable;
            return this;
        }
        public Builder setImageData(ImageData imageData) {
            this.imageData = imageData;
            return this;
        }
        public GifImageFrame build() {
            return new GifImageFrame(this);
        }
    }
}
