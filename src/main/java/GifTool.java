import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GifTool {
    public static List<BufferedImage> splitToMultipleImages(String file) {
        File image = new File(file);
        List<BufferedImage> bufferedImages = new ArrayList<>();
        try {
            String[] imageAttributes = new String[]{
                    "imageLeftPosition",
                    "imageTopPosition",
                    "imageWidth",
                    "imageHeight"
            };

            ImageReader reader = ImageIO.getImageReadersByFormatName("gif").next();
            ImageInputStream in = ImageIO.createImageInputStream(image);
            reader.setInput(in, false);
            // copy
            int noi = reader.getNumImages(true);
            BufferedImage master = null;

            for (int i = 0; i < noi; i++) {
                BufferedImage bufferedImage = reader.read(i);
                IIOMetadata metadata = reader.getImageMetadata(i);

                Node tree = metadata.getAsTree("javax_imageio_gif_image_1.0");
                NodeList children = tree.getChildNodes();

                for (int j = 0; j < children.getLength(); j++) {
                    Node nodeItem = children.item(j);

                    if(nodeItem.getNodeName().equals("ImageDescriptor")){
                        Map<String, Integer> imageAttr = new HashMap<String, Integer>();

                        for (String imageAttribute : imageAttributes) {
                            NamedNodeMap attr = nodeItem.getAttributes();
                            Node attnode = attr.getNamedItem(imageAttribute);
                            imageAttr.put(imageAttribute, Integer.valueOf(attnode.getNodeValue()));
                        }
                        if(i==0){
                            master = new BufferedImage(imageAttr.get("imageWidth"),
                                    imageAttr.get("imageHeight"), BufferedImage.TYPE_INT_ARGB);
                        }
                        master.getGraphics().drawImage(bufferedImage,
                                imageAttr.get("imageLeftPosition"), imageAttr.get("imageTopPosition"), null);
                        break;
                    }
                }
//                ImageIO.write(master, "GIF", new File( dir+"/"+"test"+i+".gif"));
                BufferedImage copyImage = new BufferedImage(master.getWidth(), master.getHeight(), master.getType());
                Graphics copyGraphic = copyImage.getGraphics();
                copyGraphic.drawImage(master, 0, 0, null);
                copyGraphic.dispose();
                bufferedImages.add(copyImage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufferedImages;
    }

}
