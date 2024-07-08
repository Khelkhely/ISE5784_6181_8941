package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * Class Intersectable is the basic class representing a geometric or a group of geometrics that are intersectable.
 */
public abstract class Intersectable {
    /**
     * Class GeoPoint is the basic class representing a point on a geometry object in Cartesian
     * 3-Dimensional coordinate system.
     */
    public static class GeoPoint {
        /** the geometry the point is on */
        public Geometry geometry;
        /** the point on the geometry */
        public Point point;

        /**
         * Constructor to initialize a GeoPoint with a point and a geometry
         * @param geometry the geometry the point is on
         * @param point the point on the geometry
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            return (obj instanceof GeoPoint other)
                    && point.equals(other.point)
                    && geometry == other.geometry;
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

    /**
     * A method that receives a ray and return a list of intersection points between the ray and the current geometry.
     * @param ray a ray that is thrown to the geometry.
     * @return a list of all the intersection points between 'ray' and the geometry.
     */
    public List<Point> findIntersections(Ray ray) {
        List<GeoPoint> geoPoints = findGeoIntersections(ray);
        return geoPoints == null ? null
                : geoPoints.stream().map(GeoPoint -> GeoPoint.point).toList();
    }

    /**
     * A method that receives a ray and return a list of intersection GeoPoints between the ray and the current geometry.
     * @param ray a ray that is thrown to the geometry.
     * @return a list of all the intersection GeoPoints between 'ray' and the geometry.
     */
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }

    /**
     * Helper method for findGeoIntersections.
     * @param ray a ray that is thrown to the geometry.
     * @return a list of all the intersection GeoPoints between 'ray' and the geometry.
     */
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        return null;
    }
}
