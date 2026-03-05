package lab1.task3;

import lombok.Getter;

public abstract class RobotPart {
    @Getter
    protected String condition;

    public RobotPart(String condition) {
        this.condition = condition;
    }

    public abstract String getPartName();
}
