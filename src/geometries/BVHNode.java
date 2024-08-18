package geometries;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class BVHNode {
    BoundaryBox boundingBox;
    BVHNode left = null, right = null;
    List<BoundaryBox> objects;

    // Leaf node constructor
    BVHNode(List<BoundaryBox> objects) {
        this.objects = objects;
        this.boundingBox = computeBoundingBox(objects);
    }

    // Internal node constructor
    BVHNode(BVHNode left, BVHNode right) {
        this.left = left;
        this.right = right;
        this.boundingBox = BoundaryBox.union(left.boundingBox, right.boundingBox);
    }

    private BoundaryBox computeBoundingBox(List<BoundaryBox> objects) {
        if (objects.isEmpty()) {
            return null;
        }

        BoundaryBox box = objects.getFirst();
        for (int i = 1; i < objects.size(); i++) {
            box = BoundaryBox.union(box, objects.get(i));
        }
        return box;
    }

    static BVHNode buildBVH(List<BoundaryBox> objects) {
        if (objects.size() <= 2) {
            return new BVHNode(objects); // Leaf node
        }

        // Sort objects along the x-axis for splitting (can choose other axes)
        objects.sort(Comparator.comparingDouble(a -> a.getMinX()));//todo

        int mid = objects.size() / 2;

        List<BoundaryBox> leftObjects = objects.subList(0, mid);
        List<BoundaryBox> rightObjects = objects.subList(mid, objects.size());

        BVHNode left = buildBVH(leftObjects);
        BVHNode right = buildBVH(rightObjects);

        return new BVHNode(left, right); // Internal node
    }
}