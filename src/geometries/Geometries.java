package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {
    final private LinkedList<Intersectable> geometries = new LinkedList<>();

    public Geometries() { }

    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    public void add (Intersectable... geometries) {
        for (Intersectable g : geometries)
        {
            this.geometries.add(g);
        }
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> list = null;
        for (Intersectable g : geometries)
        {
            List<Point> l = g.findIntersections(ray);
            if(l != null)
            {
                if (list == null){
                    list = new LinkedList<>();
                }
                list.addAll(l);
            }
        }
        return list;
    }
}
