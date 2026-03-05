package lab1.task3;

public interface Behavior {
    String sit(Location location);
    String standUp(Location from, String effort, RobotPart part);
    String cross(Location location);
    String stopBefore(Character character);
    String lookThrough(Character character);
}
