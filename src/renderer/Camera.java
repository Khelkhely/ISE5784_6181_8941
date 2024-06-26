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
    /** a vector representing the up direction of the camera **/
    private Vector vUp;
    /** a vector representing the direction the camera is facing **/
    private Vector vTo;
    /** a vector representing the right direction of the camera **/
    private Vector vRight;

    /** the width of the view plane*/
    private double viewPlaneWidth = 0;
    /** the height of the view plane*/
    private double viewPlaneHeight = 0;
    /** the distance form the camera to the view plane*/
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
     * a function that returns a builder object which can be used to construct a camera
     * @return a builder object for camera
     */
    public static Builder getBuilder() {
        return null;
    }

    /**
     * creates a ray from the camera to the view plane that goes through the specified pixel in the view plane
     * @param nX the number of columns on the view plane
     * @param nY the number of rows on the view plane
     * @param j the index of the column of the pixel on the view plane
     * @param i the index of the row of the pixel on the view plane
     * @return a ray that starts at the camera and goes through the specified pixel on the view plane
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        return null;
    }

    /**
     * a class of objects to construct a camera
     * @author Rachel and Tehila
     */
    public static class Builder {
        final private Camera camera = new Camera();

        /**
         * setter function for camera location
         * @param location the point that represents the camera's location
         * @return the camera object with the updated location
         */
        public Builder setLocation(Point location) {
            camera.p0 = location;
            return this;
        }

        /**
         * setter function for the direction of the camera
         * @param to the vector that represents the direction the camera is facing
         * @param up the vector that represents the up direction of the camera
         * @return the camera object with the updated direction vectors
         * @throws IllegalArgumentException if the vectors to and up are orthogonal to each other
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
         * a setter function for the size of the view plane
         * @param width the width of the view plane
         * @param height the height of the view plane
         * @return the camera object with the updated view plane size
         * @throws IllegalArgumentException if the width or height are not positive
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
         * a setter function for the distance between the camera and the view plane
         * @param distance the distance between the camera and the view plane
         * @return the camera object with the updated distance
         * @throws IllegalArgumentException if the distance is not positive
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

        /***
         * checks that all the necessary data is set for the camera and is valid, and calculates the missing data
         * @return a copy of the camera object that was built
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

            if (!isZero(camera.vTo.length() - 1) ||
                    !isZero(camera.vUp.length() - 1) ||
                    !isZero(camera.vRight.length() - 1)) {
                throw new IllegalArgumentException("Vectors must be normalized");
            }
            if (!isZero(camera.vRight.dotProduct(camera.vTo))) {
                throw new IllegalArgumentException("Vectors to and up must be orthogonal to each other.");
            }
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();

            try{
                return (Camera) camera.clone();
            }
            catch(CloneNotSupportedException ex) {
                throw new UnsupportedOperationException("cloning unsupported");
            }

        }
    }
}