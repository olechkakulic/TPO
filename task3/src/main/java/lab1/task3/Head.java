package lab1.task3;

public class Head extends RobotPart {

    public Head(String condition) {
        super(condition);
    }

    public String move(String manner, String direction) {
        condition = manner + ", " + direction;
        String result = "Голова робота " + manner + " " + direction + ".";
        System.out.println(result);
        return result;
    }

    @Override
    public String getPartName() {
        return "голова";
    }

    @Override
    public String toString() {
        return getPartName();
    }
}
