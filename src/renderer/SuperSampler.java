package renderer;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.*;

/**
 * Class SuperSampler is a class that manages super sampling for rays in the image for improvements
 * @author Rachel and Tehila
 */
public abstract class SuperSampler {
    int numOfRays = 50;
    double areaSize;

    public List<Ray> createSampleRays(Ray ray, Vector right, Vector up, Point point){
        int n = (int)floor(sqrt(numOfRays));
        Point head = ray.getHead();
        double interval = areaSize / n;
        List<Ray> list = new LinkedList<>();
        point = point.add(up.scale(-0.5 * areaSize)).add(right.scale(-0.5 * areaSize));
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                list.add(new Ray(
                        head,
                        point.add(up.scale(interval * i + 0.5 * interval))
                                .add(right.scale(interval + j + 0.5 * interval))
                                .subtract(head)));
            }
        }
        return list;
    }

    public abstract Double3 calculateValue(Ray ray);
}
