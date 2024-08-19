package geometries;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import primitives.Double3;
import primitives.Point;


class BVHNodeTest {

    /**
     * Test method for {@link BVHNode#buildBVH(List)}
     */
    @Test
    void testBuildBVH() {
        List<BoundaryBox> objects = new ArrayList<>();
        objects.add(new BoundaryBox(new Double3(1, 1, 1), new Double3(2, 2, 2)));
        objects.add(new BoundaryBox(new Double3(3, 3, 3), new Double3(4, 4, 4)));
        objects.add(new BoundaryBox(new Double3(5, 5, 5), new Double3(6, 6, 6)));
        objects.add(new BoundaryBox(new Double3(7, 7, 7), new Double3(8, 8, 8)));

        BVHNode root = BVHNode.buildBVH(objects);
    }
}