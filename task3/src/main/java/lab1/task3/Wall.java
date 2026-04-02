package lab1.task3;

public enum Wall {
    LEFT("левая стена"),
    RIGHT("правая стена");

    private final String description;

    Wall(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}