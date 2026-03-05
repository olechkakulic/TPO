package lab1.task3;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Character {
    private final boolean trillian;

    public static Character trillian() {
        return new Character(true);
    }

    public static Character watcher() {
        return new Character(false);
    }

    public boolean perceive(boolean apparent) {
        if (trillian) {
            return apparent;
        }
        System.out.print("показалось бы ");
        return true;
    }

    @Override
    public String toString() {
        return trillian ? "Триллиан" : "посторонний наблюдатель";
    }
}
