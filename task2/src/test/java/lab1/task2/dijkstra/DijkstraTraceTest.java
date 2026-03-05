package lab1.task2.dijkstra;

import lab1.task2.graph.UndirectedGraph;
import lab1.task2.trace.TraceRecorder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DijkstraTraceTest {

    @Test
    @DisplayName("Минимальный случай: граф из одной вершины")
    void singleVertexGraph() {
        UndirectedGraph g = new UndirectedGraph();
        g.addVertex(0);

        TraceRecorder t = new TraceRecorder();
        PathResult r = Dijkstra.shortestPaths(g, 0, t);

        assertEquals(0.0, r.distanceTo(0), 1e-12);
        assertEquals(List.of(0), r.pathTo(0));

        List<String> expected = List.of(
                "INIT(source=0)",
                "POLL(v=0,d=0.000)",
                "END"
        );
        assertEquals(expected, t.asStrings());
    }

    @Test
    @DisplayName("Трассировка: небольшой граф (есть улучшения и неудачные релаксации)")
    void traceSmallGraph() {
        UndirectedGraph g = new UndirectedGraph();
        // 0--1(2), 0--2(5), 1--2(1), 1--3(3), 2--3(1)
        g.addEdge(0, 1, 2);
        g.addEdge(0, 2, 5);
        g.addEdge(1, 2, 1);
        g.addEdge(1, 3, 3);
        g.addEdge(2, 3, 1);

        TraceRecorder t = new TraceRecorder();
        PathResult r = Dijkstra.shortestPaths(g, 0, t);

        // Проверка результата
        assertEquals(4.0, r.distanceTo(3), 1e-9);
        assertEquals(List.of(0, 1, 2, 3), r.pathTo(3));

        // Эталон последовательности характерных точек
        // Важно: порядок соседей зависит от того, как добавлялись ребра.
        List<String> expected = List.of(
                "INIT(source=0)",

                "POLL(v=0,d=0.000)",
                "RELAX_TRY(0->1,cand=2.000,old=Inf)",
                "RELAX_OK(0->1,new=2.000)",
                "RELAX_TRY(0->2,cand=5.000,old=Inf)",
                "RELAX_OK(0->2,new=5.000)",

                "POLL(v=1,d=2.000)",
                "RELAX_TRY(1->0,cand=4.000,old=0.000)",
                "RELAX_FAIL(1->0)",
                "RELAX_TRY(1->2,cand=3.000,old=5.000)",
                "RELAX_OK(1->2,new=3.000)",
                "RELAX_TRY(1->3,cand=5.000,old=Inf)",
                "RELAX_OK(1->3,new=5.000)",

                "POLL(v=2,d=3.000)",
                "RELAX_TRY(2->0,cand=8.000,old=0.000)",
                "RELAX_FAIL(2->0)",
                "RELAX_TRY(2->1,cand=4.000,old=2.000)",
                "RELAX_FAIL(2->1)",
                "RELAX_TRY(2->3,cand=4.000,old=5.000)",
                "RELAX_OK(2->3,new=4.000)",

                // ВОТ ТУТ ПРАВКА: сначала 3(4.000), потом устаревшая 2(5.000)
                "POLL(v=3,d=4.000)",
                "RELAX_TRY(3->1,cand=7.000,old=2.000)",
                "RELAX_FAIL(3->1)",
                "RELAX_TRY(3->2,cand=5.000,old=3.000)",
                "RELAX_FAIL(3->2)",

                "POLL(v=2,d=5.000)",
                "SKIP_OUTDATED(v=2,d=5.000,best=3.000)",

                "POLL(v=3,d=5.000)",
                "SKIP_OUTDATED(v=3,d=5.000,best=4.000)",

                "END"
        );
        assertEquals(expected, t.asStrings());
    }
    @Test
    @DisplayName("Недостижимая вершина: расстояние Inf, путь пустой")
    void unreachableVertexStrict() {
        UndirectedGraph g = new UndirectedGraph();
        g.addEdge(0, 1, 1);
        g.addVertex(2); // отдельно, без рёбер

        TraceRecorder t = new TraceRecorder();
        PathResult r = Dijkstra.shortestPaths(g, 0, t);

        assertEquals(0.0, r.distanceTo(0), 1e-12);
        assertEquals(1.0, r.distanceTo(1), 1e-12);
        assertTrue(Double.isInfinite(r.distanceTo(2)));
        assertEquals(List.of(), r.pathTo(2));

        // Трасса: обработались только достижимые вершины 0 и 1
        List<String> expected = List.of(
                "INIT(source=0)",
                "POLL(v=0,d=0.000)",
                "RELAX_TRY(0->1,cand=1.000,old=Inf)",
                "RELAX_OK(0->1,new=1.000)",
                "POLL(v=1,d=1.000)",
                "RELAX_TRY(1->0,cand=2.000,old=0.000)",
                "RELAX_FAIL(1->0)",
                "END"
        );

        assertEquals(expected, t.asStrings());
    }
    @Test
    @DisplayName("Равные кратчайшие пути: детерминизм извлечения из очереди")
    void equalPathsTieBreaking() {
        UndirectedGraph g = new UndirectedGraph();
        g.addEdge(0, 1, 1);
        g.addEdge(0, 2, 1);
        g.addEdge(1, 3, 1);
        g.addEdge(2, 3, 1);

        TraceRecorder t = new TraceRecorder();
        PathResult r = Dijkstra.shortestPaths(g, 0, t);

        assertEquals(2.0, r.distanceTo(3), 1e-12);
        // При tie-break по номеру вершины ожидаем, что сначала пойдет через 1
        assertEquals(List.of(0, 1, 3), r.pathTo(3));

        List<String> expected = List.of(
                "INIT(source=0)",

                "POLL(v=0,d=0.000)",
                "RELAX_TRY(0->1,cand=1.000,old=Inf)",
                "RELAX_OK(0->1,new=1.000)",
                "RELAX_TRY(0->2,cand=1.000,old=Inf)",
                "RELAX_OK(0->2,new=1.000)",

                // tie: (1,1) и (2,1) -> сначала v=1
                "POLL(v=1,d=1.000)",
                "RELAX_TRY(1->0,cand=2.000,old=0.000)",
                "RELAX_FAIL(1->0)",
                "RELAX_TRY(1->3,cand=2.000,old=Inf)",
                "RELAX_OK(1->3,new=2.000)",

                "POLL(v=2,d=1.000)",
                "RELAX_TRY(2->0,cand=2.000,old=0.000)",
                "RELAX_FAIL(2->0)",
                "RELAX_TRY(2->3,cand=2.000,old=2.000)",
                "RELAX_FAIL(2->3)",

                "POLL(v=3,d=2.000)",
                "RELAX_TRY(3->1,cand=3.000,old=1.000)",
                "RELAX_FAIL(3->1)",
                "RELAX_TRY(3->2,cand=3.000,old=1.000)",
                "RELAX_FAIL(3->2)",

                "END"
        );

        assertEquals(expected, t.asStrings());
    }
}