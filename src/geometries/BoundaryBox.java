package geometries;

import primitives.Double3;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * BoundaryBox is a class representing a conservative boundary region - an axis aligned box that restricts
 * an intersectable in a Cartesian 3-Dimensional coordinate system.
 * @author Tehila and Rachel
 */
public class BoundaryBox {
    /** the minimum x value of the boundary box */
    private double minX;
    /** the minimum y value of the boundary box */
    private double minY;
    /** the minimum z value of the boundary box */
    private double minZ;
    /** the maximum x value of the boundary box */
    private double maxX;
    /** the maximum y value of the boundary box */
    private double maxY;
    /** the maximum z value of the boundary box */
    private double maxZ;

    public BoundaryBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public BoundaryBox(double min, double max) {
        this.minX = min;
        this.minY = min;
        this.minZ = min;
        this.maxX = max;
        this.maxY = max;
        this.maxZ = max;
    }

    public BoundaryBox(double x, double y, double z) {
        this.minX = x;
        this.minY = x;
        this.minZ = y;
        this.maxX = y;
        this.maxY = z;
        this.maxZ = z;
    }

    public BoundaryBox(BoundaryBox boundaryBox) {
        this.minX = boundaryBox.minX;
        this.minY = boundaryBox.minY;
        this.minZ = boundaryBox.minZ;
        this.maxX = boundaryBox.maxX;
        this.maxY = boundaryBox.maxY;
        this.maxZ = boundaryBox.maxZ;
    }

    public double getMinX() {
        return minX;
    }

    public BoundaryBox setMinX(double minX) {
        this.minX = minX;
        return this;
    }

    public double getMinY() {
        return minY;
    }

    public BoundaryBox setMinY(double minY) {
        this.minY = minY;
        return this;
    }

    public double getMinZ() {
        return minZ;
    }

    public BoundaryBox setMinZ(double minZ) {
        this.minZ = minZ;
        return this;
    }

    public double getMaxX() {
        return maxX;
    }

    public BoundaryBox setMaxX(double maxX) {
        this.maxX = maxX;
        return this;
    }

    public double getMaxY() {
        return maxY;
    }

    public BoundaryBox setMaxY(double maxY) {
        this.maxY = maxY;
        return this;
    }

    public double getMaxZ() {
        return maxZ;
    }

    public BoundaryBox setMaxZ(double maxZ) {
        this.maxZ = maxZ;
        return this;
    }

    boolean doesIntersect(Ray ray) {
        Vector direction = ray.getDirection();
        double dx = direction.getX();
        double dy = direction.getY();
        double dz = direction.getZ();
        Point head = ray.getHead();
        double px = head.getX();
        double py = head.getY();
        double pz = head.getZ();
        double tMinX, tMaxX, tMinY, tMaxY, tMinZ, tMaxZ;

        // Handle the case where the vector is parallel to the X axis (dx == 0)
        if (dx == 0) {
            if (px <= minX || px >= maxX) {
                return false; // The vector is outside the prism on the X axis
            }
            tMinX = Double.NEGATIVE_INFINITY;
            tMaxX = Double.POSITIVE_INFINITY;
        } else {
            tMinX = (minX - px) / dx;
            tMaxX = (maxX - px) / dx;
            if (tMinX > tMaxX) {
                double temp = tMinX;
                tMinX = tMaxX;
                tMaxX = temp;
            }
        }

        if (dy == 0) {
            if (py <= minY || py >= maxY) {
                return false; // The vector is outside the prism on the Y axis
            }
            tMinY = Double.NEGATIVE_INFINITY;
            tMaxY = Double.POSITIVE_INFINITY;
        } else {
            tMinY = (minY - py) / dy;
            tMaxY = (maxY - py) / dy;
            if (tMinY > tMaxY) {
                double temp = tMinY;
                tMinY = tMaxY;
                tMaxY = temp;
            }
        }

        if (dz == 0) {
            if (pz <= minZ || pz >= maxZ) {
                return false; // The vector is outside the prism on the Y axis
            }
            tMinZ = Double.NEGATIVE_INFINITY;
            tMaxZ = Double.POSITIVE_INFINITY;
        } else {
            tMinZ = (minZ - pz) / dz;
            tMaxZ = (maxZ - pz) / dz;
            if (tMinZ > tMaxZ) {
                double temp = tMinZ;
                tMinZ = tMaxZ;
                tMaxZ = temp;
            }
        }

        // Find the largest tMin and smallest tMax across all axes
        double tMin = Math.max(tMinX, Math.max(tMinY, tMinZ));
        double tMax = Math.min(tMaxX, Math.min(tMaxY, tMaxZ));

        // If tMin is less than or equal to tMax, and tMax is greater than or equal to 0, there is an intersection
        return tMin <= tMax && tMax > 0;
    }

    private double max_component(Double3 t0) {
        return Math.max(Math.max(t0.getD1(),t0.getD2()),t0.getD3());
    }

    private double min_component(Double3 t0) {
        return Math.min(Math.min(t0.getD1(),t0.getD2()),t0.getD3());
    }



    private Double3 max(Double3 t0, Double3 t1) {
        double max1 = Math.max(t0.getD1(),t1.getD1());
        double max2 = Math.max(t0.getD2(),t1.getD2());
        double max3 = Math.max(t0.getD3(),t1.getD3());
        return new Double3(max1, max2, max3);
    }

    /**
     * expand the current boundary box to include the received boundary box as well
     * @param boundaryBox the received boundary box
     */
    public void add(BoundaryBox boundaryBox) {
        if (minX > boundaryBox.minX) {
            minX = boundaryBox.minX;
        }
        if (minY > boundaryBox.minY) {
            minY = boundaryBox.minY;
        }
        if (minZ > boundaryBox.minZ) {
            minZ = boundaryBox.minZ;
        }
        if (maxX < boundaryBox.maxX) {
            maxX = boundaryBox.maxX;
        }
        if (maxY < boundaryBox.maxY) {
            maxY = boundaryBox.maxY;
        }
        if (maxZ < boundaryBox.maxZ) {
            maxZ = boundaryBox.maxZ;
        }
    }

    public static Geometries buildBVH(Geometries geometries) {
        if (geometries.size() <= 2) {
            return geometries.boundaryBox =  // Leaf node
        }

        // Sort objects along the z-axis for splitting (can choose other axes)
        objects.sort(Comparator.comparingDouble(a -> a.getMinZ()));//todo

        int mid = objects.size() / 2;

        List<BoundaryBox> leftObjects = objects.subList(0, mid);
        List<BoundaryBox> rightObjects = objects.subList(mid, objects.size());

        BVHNode left = buildBVH(leftObjects);
        BVHNode right = buildBVH(rightObjects);

        return new BVHNode(left, right); // Internal node
    }
}
}
