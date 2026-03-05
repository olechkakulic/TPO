package lab1.task2.dijkstra;

import lab1.task2.graph.UndirectedGraph;
import lab1.task2.trace.TraceRecorder;

import java.util.*;

public class Dijkstra {

    private record NodeDist(int v, double dist) {}

    public static PathResult shortestPaths(UndirectedGraph g, int source, TraceRecorder trace) {
        Objects.requireNonNull(g, "graph");
        Objects.requireNonNull(trace, "trace");

        trace.add("INIT", "source=" + source);

        Map<Integer, Double> dist = new HashMap<>();
        Map<Integer, Integer> prev = new HashMap<>();

        for (int v : g.vertices()) dist.put(v, Double.POSITIVE_INFINITY);
        dist.put(source, 0.0);

        PriorityQueue<NodeDist> pq = new PriorityQueue<>(
                Comparator.<NodeDist>comparingDouble(NodeDist::dist)
                        .thenComparingInt(NodeDist::v)
        );
        pq.add(new NodeDist(source, 0.0));

        while (!pq.isEmpty()) {
            NodeDist cur = pq.poll();
            trace.add("POLL", "v=" + cur.v + ",d=" + fmt(cur.dist));

            double best = dist.getOrDefault(cur.v, Double.POSITIVE_INFINITY);
            if (cur.dist > best) {
                trace.add("SKIP_OUTDATED", "v=" + cur.v + ",d=" + fmt(cur.dist) + ",best=" + fmt(best));
                continue;
            }

            List<UndirectedGraph.Edge> neigh = new ArrayList<>(g.neighbors(cur.v));
            neigh.sort(Comparator.comparingInt(e -> e.to));

            for (UndirectedGraph.Edge e : neigh) {
                double old = dist.getOrDefault(e.to, Double.POSITIVE_INFINITY);
                double cand = cur.dist + e.w;

                trace.add("RELAX_TRY", cur.v + "->" + e.to + ",cand=" + fmt(cand) + ",old=" + fmt(old));

                if (cand < old) {
                    dist.put(e.to, cand);
                    prev.put(e.to, cur.v);
                    pq.add(new NodeDist(e.to, cand));
                    trace.add("RELAX_OK", cur.v + "->" + e.to + ",new=" + fmt(cand));
                } else {
                    trace.add("RELAX_FAIL", cur.v + "->" + e.to);
                }
            }
        }

        trace.add("END");
        return new PathResult(source, dist, prev);
    }

    private static String fmt(double x) {
        if (Double.isInfinite(x)) return "Inf";
        return String.format(Locale.US, "%.3f", x);
    }
}