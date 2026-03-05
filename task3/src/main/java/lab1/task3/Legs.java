package lab1.task3;

public class Legs extends RobotPart {

    public Legs(String condition) {
        super(condition);
    }

    @Override
    public String getPartName() {
        return "ноги";
    }

    @Override
    public String toString() {
        return getPartName();
    }
}
