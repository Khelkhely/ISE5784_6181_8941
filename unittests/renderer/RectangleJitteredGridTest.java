package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.List;

import static java.awt.Color.*;

class RectangleJitteredGridTest {

    /**
     * Test method for {@link RectangleJitteredGrid#setNumberOfSamples(int)}
     */
    @Test
    void testGenerateSamples() {
        //TC01: 10x10 grid
        ImageWriter imageWriter1 = new ImageWriter("TargetAreaTest1", 100,100);
        RectangleJitteredGrid targetArea =new RectangleJitteredGrid(100,10,10);
        List<Point> samples = targetArea.generateSamples(new Point(5,5,0),Vector.Y,Vector.X);

        // create a grid
        for (int i = 0; i < imageWriter1.getNx(); i++) {
            for (int j = 0; j < imageWriter1.getNy(); j++) {
                imageWriter1.writePixel(i,j,new Color(YELLOW));
            }
        }

        for (int i = 0; i < imageWriter1.getNx(); i+=10) {
            for (int j = 0; j < imageWriter1.getNy(); j++) {
                imageWriter1.writePixel(i,j,new Color(WHITE));
            }
        }

        for (int i = 0; i < imageWriter1.getNx(); i++) {
            for (int j = 0; j < imageWriter1.getNy(); j+=10) {
                imageWriter1.writePixel(i,j,new Color(WHITE));
            }
        }

        //mark the target points
        for (Point sample : samples) {
            imageWriter1.writePixel((int)(sample.getX() * 10), (int)(sample.getY() * 10), new Color(BLUE));
        }
        imageWriter1.writeToImage();
    }
}