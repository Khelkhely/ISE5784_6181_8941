package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.List;

import static java.awt.Color.*;
import static org.junit.jupiter.api.Assertions.*;

class RectangleJitteredTargetAreaTest {

    /**
     * Test method for {@link RectangleJitteredTargetArea#generateSamples(Point)}
     */
    @Test
    void testGenerateSamples() {
        ImageWriter imageWriter = new ImageWriter("TargetAreaTest", 100,100);
        RectangleJitteredTargetArea targetArea =
                new RectangleJitteredTargetArea(25,10,10, Vector.Y, Vector.X);
        List<Point> samples = targetArea.generateSamples(new Point(5,5,0));



        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                if (i % 20 == 0 || j % 20 == 0) {
                    imageWriter.writePixel(i, j, new Color(RED));
                } else {
                    imageWriter.writePixel(i, j, new Color(YELLOW));
                }
            }
        }

        for (Point sample : samples) {
            imageWriter.writePixel((int)(sample.getX() * 10), (int)(sample.getY() * 10), new Color(BLUE));
        }
        imageWriter.writeToImage();
    }
}