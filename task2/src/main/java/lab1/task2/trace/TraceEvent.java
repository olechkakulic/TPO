package lab1.task2.trace;

public record TraceEvent(String type, String details) {
    @Override
    public String toString() {
        return details == null || details.isBlank() ? type : (type + "(" + details + ")");
    }
}