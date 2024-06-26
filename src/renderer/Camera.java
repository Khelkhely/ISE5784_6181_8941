package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.MissingResourceException;
import static primitives.Util.isZero;

/**
 * Class Camera is the basic class representing a camera in Cartesian 3-Dimensional coordinate system.
 * @author Rachel and Tehila
 */
public class Camera implements Cloneable {
    /** a point that representing the camera's location **/
    private Point p0;
    /**     */
    private Vector vUp;
    /**     */
    private Vector vTo;
    /**     */
    private Vector vRight;

    /**     */
    private double viewPlaneWidth = 0;
    /**     */
    private double viewPlaneHeight = 0;
    /**     */
    private double viewPlaneDistance = 0;

    //private ImageWriter imageWriter;
    //private RayTracerBase rayTracer;

    //private Point viewPlanePC;

    /**
     *
     */
    private Camera() {
    }

    /**
     * getter function for viewPlaneDistance
     * @return the distance between the camera and the view plane
     */
    public double getDistance() {
        return viewPlaneDistance;
    }

    /**
     * getter function for viewPlaneHeight
     * @return the height of the view plane
     */
    public double getHeight() {
        return viewPlaneHeight;
    }

    /**
     * getter function for viewPlaneWidth
     * @return the width of the view plane
     */
    public double getWidth() {
        return viewPlaneWidth;
    }

    /**
     * getter function for camera's vRight
     * @return the vector representing the camera's Right direction
     */
    public Vector getvRight() {
        return vRight;
    }

    /**
     * getter function for camera's vTo
     * @return the vector representing the camera's To direction
     */
    public Vector getvTp() {
        return vTo;
    }

    /**
     * getter function for camera's vUp
     * @return the vector representing the camera's Up direction
     */
    public Vector getvUp() {
        return vUp;
    }

    /**
     * getter function for camera's p0
     * @return the point representing the camera's location
     */
    public Point getP0() {
        return p0;
    }

    /**
     *
     * @return
     */
    public static Builder getBuilder() {
        return null;
    }

    /**
     *
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @return
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        return null;
    }

    /**
     *
     */
    public static class Builder {
        final private Camera camera = new Camera();

        /**
         *
         * @param location
         * @return
         */
        public Builder setLocation(Point location) {
            camera.p0 = location;
            return this;
        }

        /**
         *
         * @param to
         * @param up
         * @return
         */
        public Builder setDirection(Vector to, Vector up) {
            if(!isZero(to.dotProduct(up))) {
                throw new IllegalArgumentException("Vectors to and up must be orthogonal to each other.");
            }
            camera.vTo = to.normalize();
            camera.vUp = up.normalize();
            return this;
        }

        /**
         *
         * @param width
         * @param height
         * @return
         */
        public Builder setVpSize(double width, double height) {
            if (Util.alignZero(width) <= 0 || Util.alignZero(height) <= 0) {
                throw new IllegalArgumentException("Width and height must be positive numbers.");
            }
            camera.viewPlaneWidth = width;
            camera.viewPlaneHeight = height;
            return this;
        }

        /**
         *
         * @param distance
         * @return
         */
        public Builder setVpDistance(double distance) {
            if (Util.alignZero(distance) <= 0) {
                throw new IllegalArgumentException("Distance must be positive numbers.");
            }
            camera.viewPlaneDistance = distance;
            return this;
        }

        /*public Builder setImageWriter(ImageWriter imageWriter) {
            camera.imageWriter = imageWriter;
            return this;
        }

        public Builder setRayTracer(RayTracerBase tracer) {
            camera.rayTracer = tracer;
            return this;
        }*/

        /**
         *
         * @return
         */
        public Camera build() {
            String generalDescription = "Rendering data missing.";
            String className = "Camera";
            if (camera.p0 == null) {
                throw new MissingResourceException(generalDescription, className, "Position point");
            }
            if (camera.vUp == null) {
                throw new MissingResourceException(generalDescription, className, "Up vector");
            }
            if (camera.vTo == null) {
                throw new MissingResourceException(generalDescription, className, "To vector");
            }
            if (Util.alignZero(camera.viewPlaneWidth) <= 0) {
                throw new IllegalArgumentException("Plane view width must be positive.");
            }
            if (Util.alignZero(camera.viewPlaneHeight) <= 0) {
                throw new IllegalArgumentException("Plane view height must be positive.");
            }
            if (Util.alignZero(camera.viewPlaneDistance) <= 0) {
                throw new IllegalArgumentException("Plane view distance must be positive.");
            }
            if (!isZero(camera.vRight.dotProduct(camera.vTo))) {
                throw new IllegalArgumentException("Vectors to and up must be orthogonal to each other.");
            }
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();

            return (Camera) camera;//.clone();
        }
    }
}
