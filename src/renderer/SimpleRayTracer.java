package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static java.lang.Math.*;
import static primitives.Util.alignZero;

/**
 * Class SimpleRayTracer is a class of simple ray tracers
 * @author Rachel and Tehila
 */
public class SimpleRayTracer extends RayTracerBase {
    /** the maximum depth of the recursion in the calculation of the global effects */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    /** the minimal value of the effect the recursion has on the image in the calculation of the global effects */
    private static final double MIN_CALC_COLOR_K = 0.001;
    /** the initial value of k (the level of effects the color calculation has on the image) */
    private static final Double3 INITIAL_K = Double3.ONE;

    /**
     * a constructor for SimpleRayTracer
     * @param scene the scene the rays that the class is tracing go through
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
    }

    /**
     * calculates the color of a GeoPoint in the scene
     * @param gp the intersection GeoPoint between the ray and the scene
     * @param ray the ray from the camera to the point
     * @return the color of the GeoPoint received
     */
    protected Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * calculates the color of a GeoPoint in the scene
     * @param gp the intersection GeoPoint between the ray and the scene
     * @param ray the ray from the camera to the point
     * @return the color of the GeoPoint received
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(gp, ray, level, k));
    }

    /**
     * calculates the global effects of the color of the point - the transparency and reflection
     * @param gp the geoPoint
     * @param ray the primary ray that intersects the point
     * @param level the level of the recursion
     * @param k the level of effects the color has on the image
     * @return the color of the global effects of the light on the point
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffect(constructRefractedRay(gp, ray), material.kT, level, k)
                .add(calcGlobalEffect(constructReflectedRay(gp, ray), material.kR, level, k));
    }

    /**
     * calculates the reflected ray, from the point of intersection in the direction of the reflection on the geometry
     * @param geoPoint the geoPoint of intersection
     * @param ray the primary ray to reflect
     * @return the reflected ray
     */
    private Ray constructReflectedRay(GeoPoint geoPoint, Ray ray) {
        Vector r = ray.getDirection();
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        r = r.subtract(n.scale(2 * n.dotProduct(r)));
        return new Ray(geoPoint.point, r, n);
    }

    /**
     * calculates the refracted ray, from the point of intersection in the direction of the refraction on the geometry
     * @param geoPoint the geoPoint of intersection
     * @param ray the primary ray to refract
     * @return the refracted ray
     */
    private Ray constructRefractedRay(GeoPoint geoPoint, Ray ray) {
        Point point = geoPoint.point;
        return new Ray(point, ray.getDirection(), geoPoint.geometry.getNormal(point));
    }

    /**
     * calculates the transparency or the reflection effects on a point by the secondary ray that comes out of it
     * @param ray the secondary ray of the Refracted or Reflected from the primary one
     * @param kx the attenuation coefficient of the transparency/reflection of the material
     * @param level the level of the recursion
     * @param k the level of effects the color has on the image
     * @return the color of transparency/reflection effects of the light on the point
     */
    private Color calcGlobalEffect(Ray ray, Double3 kx, int level, Double3 k) {
        Double3 kkx = kx.product(k);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) {
            return Color.BLACK;
        }
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx))
                .scale(kx);
    }

    /**
     * finds the closest intersection geoPoint the ray has with the scene
     * @param ray the ray
     * @return the closest intersection geoPoint the ray has with the scene and null if there are no intersections
     */
    protected GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        return intersections == null
                ? null
                : ray.findClosestGeoPoint(intersections);
    }

    /**
     * calculates the color of the point by local effects
     * @param geoPoint the geoPoint that it calculates the color for
     * @param ray the ray from the camera to the point
     * @return the color of the local effects of the light on the point
     */
    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray, Double3 k) {
        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
        Vector v = ray.getDirection();
        double nv = alignZero(n.dotProduct(v));
        Color color = geoPoint.geometry.getEmission();
        if (nv == 0) {
            return color;
        }

        Material material = geoPoint.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(geoPoint.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Double3 ktr = transparency(geoPoint, lightSource, l, n);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(geoPoint.point).scale(ktr);
                    color = color.add(
                            iL.scale(calcDiffusive(material, nl)
                                    .add(calcSpecular(material, n, l, nl, v))));
                }
            }
        }
        return color;
    }

    /**
     * calculates the diffusive effects of a light source on the point
     * @param material the material of the point
     * @param nDotProductL the dot product between the normal of the geometry from the point
     *           and the vector from the light source to the point.
     * @return the diffusive effects
     */
    private Double3 calcDiffusive(Material material, double nDotProductL) {
        return material.kD.scale(abs(nDotProductL));
    }

    /**
     * calculates the specular effects of a light source on the point
     * @param material the material of the point
     * @param n the normal from the point of the geometry
     * @param l the vector from the light source to the point
     * @param nDotProductL the dot product between n and l
     * @param rayDirection the direction of the ray
     * @return the specular effects
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nDotProductL, Vector rayDirection) {
        Vector r = l.subtract(n.scale(nDotProductL * 2));
        return material.kS.scale(pow(max(0, - rayDirection.dotProduct(r)), material.nShininess));
    }

    /**
     * calculates the transparency of all the intersection points between the light source and the geoPoint
     * and return how much from the light should affect to the point.
     * @param gp the point
     * @param ls the light source
     * @param l  the vector between the light source and the point
     * @param n  the normal of the geometry the point is on at the point
     * @return a Double3 that representing how much from the light should affect to the point.
     */
    private Double3 transparency(GeoPoint gp, LightSource ls, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // vector from point to light source
        Ray ray = new Ray(gp.point, lightDirection, n);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray, ls.getDistance(gp.point));
        Double3 ktr = Double3.ONE;
        if (intersections == null)
            return ktr;
        for (GeoPoint geoPoint : intersections) {
            ktr = ktr.product(geoPoint.geometry.getMaterial().kT);
        }
        return ktr;
    }

}
