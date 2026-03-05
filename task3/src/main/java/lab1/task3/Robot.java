package lab1.task3;

public class Robot implements Behavior {
    private final String name;

    public Robot(String name) {
        this.name = name;
    }

    @Override
    public String sit(Location location) {
        String result = name + " сидит в " + location + ".";
        System.out.println(result);
        return result;
    }

    @Override
    public String standUp(Location from, String effort, RobotPart part) {
        String result = name + " " + effort + " поднялся с " + from + " на " + part.getPartName() + ".";
        System.out.println(result);
        return result;
    }

    @Override
    public String cross(Location location) {
        String result = name + " героически пытается пересечь " + location + ".";
        System.out.println(result);
        return result;
    }

    @Override
    public String stopBefore(Character character) {
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
