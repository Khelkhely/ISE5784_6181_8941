package renderer;

import geometries.Geometry;
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
    /**
     * a constructor for SimpleRayTracer
     * @param scene the scene the rays that the class is tracing go through
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        return intersections == null
                ? scene.background
                : calcColor(ray.findClosestGeoPoint(intersections), ray);
    }

    /**
     * calculates the color of a GeoPoint in the scene
     * @param intersection the intersection GeoPoint between the ray and the scene
     * @param ray the ray from the camera to the point
     * @return the color of the GeoPoint received
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(calcLocalEffects(intersection, ray));
    }

    /**
     * calculates the color of the point by local effects
     * @param gp the geoPoint that it calculates the color for
     * @param ray the ray from the camera to the point
     * @return the color of the local effects of the light on the point
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDirection();
        double nv = alignZero(n.dotProduct(v));
        Color color = gp.geometry.getEmission();
        if (nv == 0) return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(
                        iL.scale(calcDiffusive(material, nl)
                                .add(calcSpecular(material, n, l, nl, v))));
            }
        }
        return color;
    }

    /**
     * calculates the diffusive effects of a light source on the point
     * @param material the material of the point
     * @param nl the dot product between the normal of the geometry from the point
     *           and the vector from the light source to the point.
     * @return the diffusive effects
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(abs(nl));
    }

    /**
     * calculates the specular effects of a light source on the point
     * @param material the material of the point
     * @param n the normal from the point of the geometry
     * @param l the vector from the light source to the point
     * @param nl the dot product between n and l
     * @param v the direction of the ray
     * @return the specular effects
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(nl * 2));
        return material.kS.scale(pow(max(0, - v.dotProduct(r)), material.nShininess));
    }
}
