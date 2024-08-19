package geometries;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import static geometries.BoundaryBox.union;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Class Geometry is the class representing a list of geometric bodies or shapes in Cartesian
 * 3-Dimensional coordinate system.
 * @author Rachel and Tehila
 */
public class Geometries extends Intersectable {
    /** a list of geometries */
    final private LinkedList<Intersectable> geometries = new LinkedList<>();

    /**
     * an empty constructor to initialize an empty list of geometries
     */
    public Geometries() {
        boundaryBox = new BoundaryBox(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
    }

    /**
     * a constructor to initialize a list of geometries with the geometries sent as parameters
     * @param geometries the geometries to initialize the list with
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    /**
     * a constructor to initialize a geometries with a list of the geometries sent as parameters
     * @param geometries the list of geometries to initialize the geometry list with
     */
    public Geometries(List<Intersectable> geometries) {
        add(geometries);
    }

    /**
     * adds the geometries sent as parameters to the geometries list
     * @param geometries the geometries to add to the list
     */
    public void add (Intersectable... geometries) {
        this.geometries.addAll(Arrays.asList(geometries));
    }

    /**
     * adds the geometries sent in a list of geometries to the geometries
     * @param geometries the list of the geometries to add to the list
     */
    public void add (List<Intersectable> geometries) {
        this.geometries.addAll(geometries);
    }

    /**
     * return the amount of intersectables inside the geometries object
     * @return the amount of intersectables inside the geometries object
     */
    public int size() {
        return geometries.size();
    }

    public Intersectable getItem(int index) {
        return geometries.get(index);
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersections = null;
        for (Intersectable geometry : geometries) {
            List<GeoPoint> geometryIntersections = geometry.findGeoIntersectionsHelper(ray,maxDistance);
            if (geometryIntersections != null) {
                if (intersections == null) {
                    intersections = new LinkedList<>();
                }
                intersections.addAll(geometryIntersections);
            }
        }
        return intersections;
    }

    @Override
    public void calcBoundaryBox() {
        if (geometries.size() > 0) {
            boundaryBox = new BoundaryBox(Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY);
            for (Intersectable geometry : geometries) {
                geometry.calcBoundaryBox();
                boundaryBox.add(geometry.boundaryBox);
            }
            boundaryBoxFlag = true;
        }
    }

    public void buildBVH() {
        calcBoundaryBox();
        if (geometries.size() > 2) {
            // Sort objects along the z-axis for splitting
            geometries.sort(Comparator.comparingDouble(a -> a.boundaryBox.getMinZ()));

            int mid = geometries.size() / 2;

            List<Intersectable> leftObjects = geometries.subList(0, mid);
            List<Intersectable> rightObjects = geometries.subList(mid, geometries.size());

            geometries.clear();
            Geometries right = new Geometries(rightObjects.toArray(new Intersectable[0]));
            Geometries left = new Geometries(leftObjects.toArray(new Intersectable[0]));
            right.buildBVH();
            left.buildBVH();
            add(right, left);
        }
    }

}
