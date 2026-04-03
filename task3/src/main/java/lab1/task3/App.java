package lab1.task3;

public class App {
    public static void main(String[] args) {
        Character trillian  = Character.trillian();
        Character bystander = Character.watcher();

        Location corner = new Location("угол");
        Location room   = new Location("комната");

        Robot robot = new Robot("Робот");
        Head  head  = new Head("неподвижная");
        Legs  legs  = new Legs("согнутые");

        robot.sit(corner);
        head.move("резко", "дернулась вверх");
        head.move("едва заметно", "закачалась из стороны в сторону");
        robot.standUp(corner, "тяжело", legs);

        bystander.perceive(false);

        robot.cross(room);

        robot.move(2, 3);
        robot.move(Wall.LEFT);

        robot.stopBefore(trillian);
        robot.lookThrough(trillian);
    }
}