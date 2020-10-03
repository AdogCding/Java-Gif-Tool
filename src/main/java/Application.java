
import javax.imageio.*;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) throws IOException {
        String filename = "/Users/keyangli/Desktop/test1.gif";
        String output = "/Users/keyangli/Desktop/out.gif";
        List<BufferedImage> imageList = GifTool.splitToMultipleImages(filename);
        if (imageList.size() == 0) {
            System.out.println("split 0 image");
        }
        BufferedImage firstImage = imageList.get(imageList.size()-1);
        List<File> fileList = new ArrayList<>();
        ImageOutputStream outputStream = new FileImageOutputStream(new File(output));
        ImageInputStream inputStream = new FileImageOutputStream(new File(filename));
        // read first image's metadata
        ImageReader reader = ImageIO.getImageReadersByFormatName("GIF").next();
        reader.setInput(inputStream);
        IIOMetadata metadata = reader.getImageMetadata(0);
        ImageWriter writer = ImageIO.getImageWritersByFormatName("GIF").next();
        writer.setOutput(outputStream);
        ImageWriteParam param = writer.getDefaultWriteParam();
        IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree("javax_imageio_gif_image_1.0");
        IIOMetadataNode graphicControlNode = GifSequenceWriter.getNode(root, "GraphicControlExtension");
        graphicControlNode.setAttribute("delayTime", Integer.toString(25));
        System.out.println("graphic control extensions:"+graphicControlNode.getLength());
        IIOMetadataNode applicationExtensions = GifSequenceWriter.getNode(root, "ApplicationExtensions");
        IIOMetadataNode applicationExtension = (IIOMetadataNode) applicationExtensions.item(0);
        applicationExtension.setAttribute("applicationID", "NETSCAPE");
        applicationExtension.setAttribute("authenticationCode", "2.0");
        applicationExtension.setUserObject(new byte[]{ 0x1, (byte) (1 & 0xFF), (byte) ((1 >> 8) & 0xFF)});
        System.out.println(applicationExtension.getUserObject());
        for(int i = 0; i < applicationExtensions.getLength(); i++) {
            System.out.println(((IIOMetadataNode)applicationExtensions.item(i)).getUserObject());
        }
        IIOMetadata newMetadata = writer.getDefaultImageMetadata(ImageTypeSpecifier
                .createFromBufferedImageType(firstImage.getType()), param);
        newMetadata.setFromTree("javax_imageio_gif_image_1.0",root);
//        for(int i = 48; i >= 0; i--) {
//            fileList.add(new File(target+i+".gif"));
//        }
        writer.prepareWriteSequence(null);
        for(int i = imageList.size()-2; i >= 0; i--) {
//            BufferedImage image = ImageIO.read(file);
            BufferedImage image = imageList.get(i);
            writer.writeToSequence(new IIOImage(image,null, newMetadata), param);
        }
//        writer.close();
//        outputStream.close();
//        GifTool.splitToMultipleImages(filename, dir);

    }
}
