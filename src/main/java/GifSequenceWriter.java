import javax.imageio.*;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.RenderedImage;
import java.io.IOException;

public class GifSequenceWriter {
    protected ImageWriter writer;
    protected ImageWriteParam params;
    protected IIOMetadata metadata;
    public GifSequenceWriter(ImageOutputStream out, int imageType, int delay, boolean loop) throws IOException {
        writer = ImageIO.getImageWritersByFormatName("gif").next();
        params = writer.getDefaultWriteParam();

        ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(imageType);
        metadata = writer.getDefaultImageMetadata(typeSpecifier, params);
        configureRootMetadata(delay, loop);
        writer.setOutput(out);
        writer.prepareWriteSequence(null);
    }
    private void configureRootMetadata(int delay, boolean loop) throws IIOInvalidTreeException {
        String metadataFormatName = metadata.getNativeMetadataFormatName();
        System.out.println(metadataFormatName);
        IIOMetadataNode root = (IIOMetadataNode) metadata.getAsTree(metadataFormatName);
        IIOMetadataNode graphicControlExtensionNode = getNode(root, "GraphicControlExtension");
        graphicControlExtensionNode.setAttribute("disposalMethod", "none");
        graphicControlExtensionNode.setAttribute("userInputFlag", "FALSE");
        graphicControlExtensionNode.setAttribute("transparentColorFlag", "FALSE");
        graphicControlExtensionNode.setAttribute("delayTime", Integer.toString(delay/10));
        graphicControlExtensionNode.setAttribute("transparentColorIndex", "0");
        //
        IIOMetadataNode commentsNode = getNode(root, "CommentExtensions");
        commentsNode.setAttribute("CommentExtension", "Created by: keyang li");
        //
        IIOMetadataNode appExtensionsNode = getNode(root, "ApplicationExtensions");
        IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");
        child.setAttribute("applicationID", "NETSCAPE");
        child.setAttribute("authenticationCode", "2.0");
        int loopContinuously = loop ? 0 : 1;
        child.setUserObject(new byte[]{ 0x1, (byte) (loopContinuously & 0xFF), (byte) ((loopContinuously >> 8) & 0xFF)});
        appExtensionsNode.appendChild(child);
        metadata.setFromTree(metadataFormatName, root);
    }
    public static IIOMetadataNode getNode(IIOMetadataNode root, String nodeName) {
        int nNodes = root.getLength();
        for(int i = 0; i < nNodes; i++) {
            if (root.item(i).getNodeName().equalsIgnoreCase(nodeName)) {
                return (IIOMetadataNode) root.item(i);
            }
        }
        IIOMetadataNode node = new IIOMetadataNode(nodeName);
        root.appendChild(node);
        return node;
    }

    public void writeToSequence(RenderedImage img) throws IOException {
        writer.writeToSequence(new IIOImage(img, null, metadata), params);
    }

    public void close() throws IOException {
        writer.endWriteSequence();
    }
}
