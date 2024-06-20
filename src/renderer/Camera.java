package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.isZero;

public class Camera implements Cloneable {
    private Point p0;
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;

    private double viewPlaneWidth = 0;
    private double viewPlaneHeight = 0;
    private double viewPlaneDistance = 0;

    //private ImageWriter imageWriter;
    //private RayTracerBase rayTracer;

    //private Point viewPlanePC;

    private Camera() {
    }

    public static class Builder {
        final private Camera camera = new Camera();

        public Builder setLocation(Point location) {
            camera.p0 = location;
            return this;
        }

        public Builder setDirection(Vector to, Vector up) {
            if(!isZero(to.dotProduct(up))) {
                throw new IllegalArgumentException("Vectors to and up must be orthogonal to each other.");
            }
            camera.vTo = to.normalize();
            camera.vUp = up.normalize();
            return this;
        }

        public Builder setVpSize(double width, double height) {
            if (Util.alignZero(width) <= 0 || Util.alignZero(height) <= 0) {
                throw new IllegalArgumentException("Width and height must be positive numbers.");
            }
            camera.viewPlaneWidth = width;
            camera.viewPlaneHeight = height;
            return this;
        }

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
            return (Camera) camera; //.clone();
        }
    }

    /*public static Builder getBuilder() {

    }*/

    public Ray constructRay(int nX, int nY, int j, int i) {
        return null;
    }

    /*public double getDistance() {
        return distance;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }*/

    public Vector getvRight() {
        return vRight;
    }

    /*public Vector getvTp() {
        return vTp;
    }*/

    public Vector getvUp() {
        return vUp;
    }

    public Point getP0() {
        return p0;
    }
}
