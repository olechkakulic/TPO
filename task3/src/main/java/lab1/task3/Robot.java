package lab1.task3;

public class Robot implements Behavior {
    private final String name;
    private RobotState state;

    public Robot(String name) {
        this.name = name;
        this.state = RobotState.NORMAL;
    }

    public void setState(RobotState state) {
        this.state = state;
        System.out.println(name + " теперь в состоянии: " + state);
    }

    @Override
    public String sit(Location location) {
        state = RobotState.TIRED;
        String result = name + " сидит в " + location + ".";
        System.out.println(result);
        return result;
    }

    @Override
    public String standUp(Location from, String effort, RobotPart part) {
        state = RobotState.TIRED;
        String result = name + " " + effort + " поднялся с " + from + " на " + part.getPartName() + ".";
        System.out.println(result);
        return result;
    }

    @Override
    public String cross(Location location) {
        state = RobotState.DETERMINED;

        double speed = state.getSpeedMultiplier();
        int distance = state.getStepLength() * 5;

        String result = name + " героически пытается пересечь " + location +
                " (скорость: " + speed + ", длина шага: " + distance + ").";

        System.out.println(result);
        return result;
    }

    public String move(int x, int y) {
        double speed = state.getSpeedMultiplier();
        int distance = state.getStepLength();

        String result = name + " перемещается к (" + x + ", " + y + ")" +
                " со скоростью " + speed +
                " и длиной шага " + distance + ".";

        System.out.println(result);
        return result;
    }

    public String move(Wall wall) {
        double speed = state.getSpeedMultiplier();
        int distance = state.getStepLength();

        String result = name + " движется к " + wall +
                " со скоростью " + speed +
                " и длиной шага " + distance + ".";

        System.out.println(result);
        return result;
    }

    @Override
    public String stopBefore(Character character) {
        state = RobotState.NORMAL;

        String result = name + " остановился перед " + character + ".";
        System.out.println(result);
        return result;
    }

    @Override
    public String lookThrough(Character character) {
        String result = name + " смотрит сквозь плечо " + character + ".";
        System.out.println(result);
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}