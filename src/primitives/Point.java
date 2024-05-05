package primitives;

import java.util.Objects;

/**
 * an object with 3 coordinates, that represent a point in a 3D space
 */

public class Point {
    protected final Double3 xyz;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(xyz, point.xyz);
    }

    @Override
    public String toString() {
        return "Point{" +
                "xyz=" + xyz +
                '}';
    }
}
