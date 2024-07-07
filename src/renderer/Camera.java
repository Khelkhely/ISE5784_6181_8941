package renderer;

import primitives.*;

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
    /** the image writer item that makes the image the camera captures*/
    private ImageWriter imageWriter;
    /** the ray tracer that calculates the colors for each pixel by tracing the rays that come from the camera*/
    private RayTracerBase rayTracer;

    /** the point of the center of the view plane of the camera - to calculate and save once instead of many times */
    private Point pc;

    /**
     * private constructor for camera
     */
    private Camera() {}

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
        return new Builder();
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
        Point center = pc; //the center of the pixel the ray goes through
        double ry = viewPlaneHeight / nY; //height of each pixel
        double rx = viewPlaneWidth / nX; //width of each pixel
        double xj = (j - ((float)nX - 1) / 2) * rx; //how much to move horizontally from the center of the view plane
        double yi = -(i - ((float)nY - 1) / 2) * ry; //how much to move vertically from the center of the view plane
        if (!isZero(xj)) { //move the point horizontally
            center = center.add(vRight.scale(xj));
        }
        if (!isZero(yi)) { //move the point vertically
            center = center.add(vUp.scale(yi));
        }
        return new Ray(p0,center.subtract(p0));
    }

    /**
     * creates the image by coloring each pixel according to the rays cast from the camera to the scene
     * @return the camera object
     */
    public Camera renderImage() {
        int nx = imageWriter.getNx();
        int ny = imageWriter.getNy();

        for (int j = 0; j < nx; j++) {
            for (int i = 0; i < ny; i++) {
                castRay(nx, ny, j, i);
            }
        }
        return this;
    }

    /**
     * colors a pixel by casting a ray through it and calculating the appropriate color
     * @param nX the number of columns on the view plane
     * @param nY the number of rows on the view plane
     * @param j the index of the column of the pixel on the view plane
     * @param i the index of the row of the pixel on the view plane
     */
    private void castRay(int nX, int nY, int j, int i) {
        Ray ray = constructRay(nX, nY, j, i);
        Color color = rayTracer.traceRay(ray);
        imageWriter.writePixel(j, i, color);
    }

    /**
     * adds a grid to the image
     * @param interval the size of the squares of the grid
     * @param color the color of the grid
     * @return the camera object
     */
    public Camera printGrid(int interval, Color color) {
        for (int j = 0; j < imageWriter.getNx(); j += interval) { // write vertical lines
            for (int i = 0; i < imageWriter.getNy(); i++) {
                imageWriter.writePixel(j, i, color);
            }
        }
        for (int i = 0; i < imageWriter.getNy(); i += interval) { // write horizontal lines
            for (int j = 0; j < imageWriter.getNx(); j++) {
                imageWriter.writePixel(j, i, color);
            }
        }
        return this;
    }

    /**
     * writes the image the camera captures into a png file
     */
    public void writeToImage() {
        imageWriter.writeToImage();
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

        /**
         * a setter function for the image writer of the camera
         * @param imageWriter the image writer of the camera
         * @return the camera object with the updated image writer
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * a setter function for the ray tracer of the camera
         * @param tracer the ray tracer of the camera
         * @return the camera object with the updated ray tracer
         */
        public Builder setRayTracer(RayTracerBase tracer) {
            camera.rayTracer = tracer;
            return this;
        }

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
            if (camera.imageWriter == null) {
                throw new MissingResourceException(generalDescription, className, "Image writer");
            }
            if (camera.rayTracer == null) {
                throw new MissingResourceException(generalDescription, className, "Ray tracer");
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

            if (!isZero(camera.vTo.length() - 1) || !isZero(camera.vUp.length() - 1)) {
                throw new IllegalArgumentException("Vectors must be normalized");
            }
            if (!isZero(camera.vUp.dotProduct(camera.vTo))) {
                throw new IllegalArgumentException("Vectors to and up must be orthogonal to each other.");
            }
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            camera.pc = camera.p0.add(camera.vTo.scale(camera.viewPlaneDistance));

            try{
                return (Camera) camera.clone();
            }
            catch(CloneNotSupportedException ex) {
                throw new UnsupportedOperationException("cloning unsupported");
            }

        }
    }
}