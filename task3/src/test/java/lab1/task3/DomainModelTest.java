package lab1.task3;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DomainModelTest {

    private Robot robot;
    private Character trillian;
    private Character bystander;
    private Location corner;
    private Location room;
    private Head head;
    private Legs legs;

    @BeforeEach
    void setUp() {
        robot     = new Robot("Робот");
        trillian  = Character.trillian();
        bystander = Character.watcher();
        corner    = new Location("угол");
        room      = new Location("комната");
        head      = new Head("неподвижная");
        legs      = new Legs("согнутые");
    }

    @Test
    @DisplayName("Location корректно хранит имя")
    void testLocationName() {
        assertAll(
                () -> assertEquals("угол", corner.getName()),
                () -> assertEquals("угол", corner.toString()),
                () -> assertEquals("комната", room.toString())
        );
    }

    @Test
    @DisplayName("Head имеет правильное имя части и начальное состояние")
    void testHeadCreation() {
        assertAll(
                () -> assertEquals("голова", head.getPartName()),
                () -> assertEquals("неподвижная", head.getCondition()),
                () -> assertEquals("голова", head.toString())
        );
    }

    @Test
    @DisplayName("Head.move обновляет состояние и возвращает описание")
    void testHeadMove() {
        assertAll(
                () -> assertEquals(
                        "Голова робота резко дернулась вверх.",
                        head.move("резко", "дернулась вверх")),
                () -> assertEquals("резко, дернулась вверх", head.getCondition()),
                () -> assertEquals(
                        "Голова робота едва заметно закачалась из стороны в сторону.",
                        head.move("едва заметно", "закачалась из стороны в сторону"))
        );
    }

    @Test
    @DisplayName("Legs имеют правильное имя части и состояние")
    void testLegsCreation() {
        assertAll(
                () -> assertEquals("ноги", legs.getPartName()),
                () -> assertEquals("согнутые", legs.getCondition()),
                () -> assertEquals("ноги", legs.toString())
        );
    }

    @Test
    @DisplayName("Character корректно определяет Триллиан")
    void testIsTrillian() {
        assertAll(
                () -> assertTrue(trillian.isTrillian()),
                () -> assertFalse(bystander.isTrillian())
        );
    }

    @Test
    @DisplayName("Character.perceive работает корректно")
    void testPerceive() {
        assertAll(
                () -> assertTrue(bystander.perceive(true)),
                () -> assertTrue(bystander.perceive(false)),
                () -> assertTrue(trillian.perceive(true)),
                () -> assertFalse(trillian.perceive(false))
        );
    }

    @Test
    @DisplayName("Character.toString возвращает корректное имя")
    void testCharacterToString() {
        assertAll(
                () -> assertEquals("Триллиан", trillian.toString()),
                () -> assertEquals("посторонний наблюдатель", bystander.toString())
        );
    }

    @Test
    @DisplayName("Robot.toString возвращает имя робота")
    void testRobotToString() {
        assertAll(
                () -> assertEquals("Робот", robot.toString()),
                () -> assertNotEquals("Виталя", robot.toString())
        );
    }

    @Test
    @DisplayName("Robot.sit возвращает корректное описание")
    void testSit() {
        assertAll(
                () -> assertEquals("Робот сидит в угол.", robot.sit(corner)),
                () -> assertEquals("Робот сидит в комната.", robot.sit(room))
        );
    }

    @Test
    @DisplayName("Robot.standUp возвращает корректное описание")
    void testStandUp() {
        assertAll(
                () -> assertEquals(
                        "Робот тяжело поднялся с угол на ноги.",
                        robot.standUp(corner, "тяжело", legs)),
                () -> assertEquals(
                        "Робот медленно поднялся с комната на ноги.",
                        robot.standUp(room, "медленно", legs))
        );
    }

    @Test
    @DisplayName("Robot.cross возвращает описание с учетом состояния")
    void testCross() {
        String result = robot.cross(room);

        assertAll(
                () -> assertTrue(result.contains("пересечь комната")),
                () -> assertTrue(result.contains("скорость")),
                () -> assertTrue(result.contains("длина шага"))
        );
    }

    @Test
    @DisplayName("Robot.stopBefore возвращает корректное описание")
    void testStopBefore() {
        assertAll(
                () -> assertEquals("Робот остановился перед Триллиан.", robot.stopBefore(trillian)),
                () -> assertEquals("Робот остановился перед посторонний наблюдатель.", robot.stopBefore(bystander))
        );
    }

    @Test
    @DisplayName("Robot.lookThrough возвращает корректное описание")
    void testLookThrough() {
        assertAll(
                () -> assertEquals("Робот смотрит сквозь плечо Триллиан.", robot.lookThrough(trillian)),
                () -> assertEquals("Робот смотрит сквозь плечо посторонний наблюдатель.", robot.lookThrough(bystander))
        );
    }

    @Test
    @DisplayName("move с координатами учитывает состояние робота")
    void testMoveWithCoordinates() {
        robot.standUp(corner, "тяжело", legs);

        String result = robot.move(2, 3);

        assertAll(
                () -> assertTrue(result.contains("(2, 3)")),
                () -> assertTrue(result.contains("скоростью")),
                () -> assertTrue(result.contains("длиной шага"))
        );
    }

    @Test
    @DisplayName("move с enum Wall работает корректно")
    void testMoveWithWall() {
        robot.standUp(corner, "тяжело", legs);

        String result = robot.move(Wall.LEFT);

        assertAll(
                () -> assertTrue(result.contains("левая стена")),
                () -> assertTrue(result.contains("скоростью")),
                () -> assertTrue(result.contains("длиной шага"))
        );
    }

    @Test
    @DisplayName("Состояние робота влияет на движение")
    void testStateAffectsMovement() {
        robot.standUp(corner, "тяжело", legs);
        String tiredMove = robot.move(1, 1);

        robot.cross(room);
        String determinedMove = robot.move(1, 1);

        assertAll(
                () -> assertNotEquals(tiredMove, determinedMove),
                () -> assertTrue(tiredMove.contains("скоростью")),
                () -> assertTrue(determinedMove.contains("скоростью"))
        );
    }

    @Test
    @DisplayName("RobotState содержит корректные значения")
    void testRobotStateValues() {
        assertAll(
                () -> assertEquals(0.5, RobotState.TIRED.getSpeedMultiplier()),
                () -> assertEquals(1, RobotState.TIRED.getStepLength()),
                () -> assertEquals(1.5, RobotState.DETERMINED.getSpeedMultiplier()),
                () -> assertEquals(3, RobotState.DETERMINED.getStepLength())
        );
    }

    @Test
    @DisplayName("Wall корректно возвращает строковое представление")
    void testWallEnum() {
        assertAll(
                () -> assertEquals("левая стена", Wall.LEFT.toString()),
                () -> assertEquals("правая стена", Wall.RIGHT.toString())
        );
    }
}