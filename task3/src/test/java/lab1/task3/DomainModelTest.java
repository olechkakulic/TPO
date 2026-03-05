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

    // ── Location ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Location stores its name correctly")
    void testLocationName() {
        assertAll(
            () -> assertEquals("угол", corner.getName()),
            () -> assertEquals("угол", corner.toString()),
            () -> assertEquals("комната", room.toString())
        );
    }

    // ── RobotPart subclasses ──────────────────────────────────────────────────

    @Test
    @DisplayName("Head has correct part name and initial condition")
    void testHeadCreation() {
        assertAll(
            () -> assertEquals("голова", head.getPartName()),
            () -> assertEquals("неподвижная", head.getCondition()),
            () -> assertEquals("голова", head.toString())
        );
    }

    @Test
    @DisplayName("Head.move updates condition and returns description")
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
    @DisplayName("Legs have correct part name and initial condition")
    void testLegsCreation() {
        assertAll(
            () -> assertEquals("ноги", legs.getPartName()),
            () -> assertEquals("согнутые", legs.getCondition()),
            () -> assertEquals("ноги", legs.toString())
        );
    }

    // ── Character ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Character: isTrillian correctly identifies Trillian")
    void testIsTrillian() {
        assertAll(
            () -> assertTrue(trillian.isTrillian()),
            () -> assertFalse(bystander.isTrillian())
        );
    }

    @Test
    @DisplayName("Character.perceive: bystander always true, Trillian mirrors the argument")
    void testPerceive() {
        assertAll(
            () -> assertTrue(bystander.perceive(true)),
            () -> assertTrue(bystander.perceive(false)),
            () -> assertTrue(trillian.perceive(true)),
            () -> assertFalse(trillian.perceive(false))
        );
    }

    @Test
    @DisplayName("Character.toString: Trillian by name, others as 'посторонний наблюдатель'")
    void testCharacterToString() {
        assertAll(
            () -> assertEquals("Триллиан", trillian.toString()),
            () -> assertEquals("посторонний наблюдатель", bystander.toString())
        );
    }

    // ── Robot ─────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Robot.toString returns the robot's name")
    void testRobotToString() {
        assertAll(
            () -> assertEquals("Робот", robot.toString()),
            () -> assertNotEquals("Виталя", robot.toString())
        );
    }

    @Test
    @DisplayName("Robot.sit returns correct sitting description")
    void testSit() {
        assertAll(
            () -> assertEquals("Робот сидит в угол.", robot.sit(corner)),
            () -> assertEquals("Робот сидит в комната.", robot.sit(room))
        );
    }

    @Test
    @DisplayName("Robot.standUp returns correct rising description")
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
    @DisplayName("Robot.cross returns correct crossing description")
    void testCross() {
        assertAll(
            () -> assertEquals("Робот героически пытается пересечь комната.", robot.cross(room)),
            () -> assertEquals("Робот героически пытается пересечь угол.", robot.cross(corner))
        );
    }

    @Test
    @DisplayName("Robot.stopBefore returns correct stop description")
    void testStopBefore() {
        assertAll(
            () -> assertEquals("Робот остановился перед Триллиан.", robot.stopBefore(trillian)),
            () -> assertEquals("Робот остановился перед посторонний наблюдатель.", robot.stopBefore(bystander))
        );
    }

    @Test
    @DisplayName("Robot.lookThrough returns correct gaze description")
    void testLookThrough() {
        assertAll(
            () -> assertEquals("Робот смотрит сквозь плечо Триллиан.", robot.lookThrough(trillian)),
            () -> assertEquals("Робот смотрит сквозь плечо посторонний наблюдатель.", robot.lookThrough(bystander))
        );
    }
}
