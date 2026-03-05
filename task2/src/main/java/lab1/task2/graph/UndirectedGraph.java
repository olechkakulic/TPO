package lab1.task2.graph;

import java.util.*;

public class UndirectedGraph {
    private final Map<Integer, List<Edge>> adj = new HashMap<>();

    public void addVertex(int v) {
        adj.computeIfAbsent(v, k -> new ArrayList<>());
    }

    public void addEdge(int u, int v, double w) {
        if (w < 0) throw new IllegalArgumentException("Dijkstra requires non-negative weights");
        addVertex(u);
        addVertex(v);
        adj.get(u).add(new Edge(v, w));
        adj.get(v).add(new Edge(u, w));
    }

    public List<Edge> neighbors(int v) {
        return adj.getOrDefault(v, List.of());
    }

    public Set<Integer> vertices() {
        return Collections.unmodifiableSet(adj.keySet());
    }

    public static final class Edge {
        public final int to;
        public final double w;

        public Edge(int to, double w) {
            this.to = to;
            this.w = w;
        }
    }
}