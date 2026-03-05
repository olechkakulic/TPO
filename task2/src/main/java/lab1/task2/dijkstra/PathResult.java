package lab1.task2.dijkstra;

import java.util.*;

public class PathResult {
    private final Map<Integer, Double> dist;
    private final Map<Integer, Integer> prev;
    private final int source;

    public PathResult(int source, Map<Integer, Double> dist, Map<Integer, Integer> prev) {
        this.source = source;
        this.dist = dist;
        this.prev = prev;
    }

    public int source() {
        return source;
    }

    public double distanceTo(int v) {
        return dist.getOrDefault(v, Double.POSITIVE_INFINITY);
    }

    public Map<Integer, Double> distances() {
        return Collections.unmodifiableMap(dist);
    }

    public List<Integer> pathTo(int target) {
        if (Double.isInfinite(distanceTo(target))) return List.of();

        LinkedList<Integer> path = new LinkedList<>();
        Integer cur = target;
        while (cur != null) {
            path.addFirst(cur);
            if (cur == source) break;
            cur = prev.get(cur);
        }
        if (path.isEmpty() || path.getFirst() != source) return List.of();
        return path;
    }
}