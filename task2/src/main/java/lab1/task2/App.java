package lab1.task2;

import lab1.task2.dijkstra.Dijkstra;
import lab1.task2.dijkstra.PathResult;
import lab1.task2.graph.UndirectedGraph;
import lab1.task2.trace.TraceRecorder;

public class App {
    public static void main(String[] args) {
        UndirectedGraph g = new UndirectedGraph();
        g.addEdge(0, 1, 2);
        g.addEdge(0, 2, 5);
        g.addEdge(1, 2, 1);
        g.addEdge(1, 3, 3);
        g.addEdge(2, 3, 1);

        TraceRecorder trace = new TraceRecorder();
        PathResult res = Dijkstra.shortestPaths(g, 0, trace);

        System.out.println("dist(3) = " + res.distanceTo(3));
        System.out.println("path to 3 = " + res.pathTo(3));
        System.out.println("TRACE:");
        trace.asStrings().forEach(System.out::println);
    }
}