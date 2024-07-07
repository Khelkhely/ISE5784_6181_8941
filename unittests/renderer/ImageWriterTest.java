package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static java.awt.Color.*;
import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    /**
     * Test method for {@link ImageWriter#writeToImage()}
     */
    @Test
    void testWriteToImage() {
        ImageWriter imageWriter = new ImageWriter("yellow", 800, 500);
        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                if (i % 50 == 0 || j % 50 == 0) {
                    imageWriter.writePixel(i, j, new Color(RED));
                } else {
                    imageWriter.writePixel(i, j, new Color(YELLOW));
                }
            }
        }
        imageWriter.writeToImage();
    }
}