package lab1.task2.trace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TraceRecorder {
    private final List<TraceEvent> events = new ArrayList<>();

    public void add(String type) {
        events.add(new TraceEvent(type, ""));
    }

    public void add(String type, String details) {
        events.add(new TraceEvent(type, details));
    }

    public List<TraceEvent> events() {
        return Collections.unmodifiableList(events);
    }

    public List<String> asStrings() {
        return events.stream().map(TraceEvent::toString).toList();
    }
}