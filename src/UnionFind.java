import java.util.*;

public class UnionFind {
    private final Map<String, String> parent;
    private final Map<String, Integer> rank;

    public UnionFind(List<String> vertices) {
        parent = new HashMap<>();
        rank = new HashMap<>();

        for (String vertex : vertices) {
            parent.put(vertex, vertex);
            rank.put(vertex, 0);
        }
    }

    public String find(String item) {
        if (!parent.get(item).equals(item)) {
            parent.put(item, find(parent.get(item)));
        }
        return parent.get(item);
    }

    public boolean union(String x, String y) {
        String rootX = find(x);
        String rootY = find(y);

        if (rootX.equals(rootY)) {
            return false;
        }

        int rankX = rank.get(rootX);
        int rankY = rank.get(rootY);

        if (rankX < rankY) {
            parent.put(rootX, rootY);
        } else if (rankX > rankY) {
            parent.put(rootY, rootX);
        } else {
            parent.put(rootY, rootX);
            rank.put(rootX, rankX + 1);
        }

        return true;
    }
}
