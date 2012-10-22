package Learning.BMPReading;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: Piers
 * Date: 10/05/12
 * Time: 21:03
 */
public class Reader {

    public static void main(String[] args) {
        try {
            BufferedImage image = ImageIO.read(new File("Content/Maps/Normal/map.png"));

            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    int argb = image.getRGB(x, y);

                    int rgb[] = new int[]{
                            (argb >> 16) & 0xff, //red
                            (argb >> 8) & 0xff, //green
                            (argb) & 0xff  //blue
                    };
                    System.out.print("[");
                    for(int i : rgb){
                        System.out.print(i + " ");
                    }
                    System.out.print("]");
                    System.out.println();
                }
            }

        } catch (IOException ioe) {
            System.err.println("Broke, couldn't find it");

        }
    }
}
