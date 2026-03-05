package lab1.task3;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Location {
    private final String name;

    @Override
    public String toString() {
        return name;
    }
}
