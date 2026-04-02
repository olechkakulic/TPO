package lab1.task3;

public enum RobotState {
    TIRED(0.5, 1),
    NORMAL(1.0, 2),
    DETERMINED(1.5, 3);

    private final double speedMultiplier;
    private final int stepLength;

    RobotState(double speedMultiplier, int stepLength) {
        this.speedMultiplier = speedMultiplier;
        this.stepLength = stepLength;
    }

    public double getSpeedMultiplier() {
        return speedMultiplier;
    }

    public int getStepLength() {
        return stepLength;
    }
}