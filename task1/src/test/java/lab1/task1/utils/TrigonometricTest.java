package lab1.task1.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

public class TrigonometricTest {

    private static final double EPS = 1e-10;

    @ParameterizedTest(name = "cos({0})")
    @DisplayName("Check corner and special values")
    @ValueSource(doubles = {
            -0.0, 0.0,
            -0.000001, -0.0001, 0.0001, 0.000001,
            -Math.PI, -Math.PI / 2, -Math.PI / 3, -Math.PI / 4, -Math.PI / 6,
            Math.PI / 6, Math.PI / 4, Math.PI / 3, Math.PI / 2, Math.PI,
            -2 * Math.PI, 2 * Math.PI, -10 * Math.PI, 10 * Math.PI,
            -1000.0, 1000.0,
            Double.MIN_VALUE, -Double.MIN_VALUE,
            Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY
    })
    void checkCornerDots(double x) {
        double actual = Trig.cos(x);

        if (Double.isNaN(x) || Double.isInfinite(x)) {
            assertTrue(Double.isNaN(actual), "cos(x) must be NaN for NaN/Infinity");
        } else {
            assertEquals(Math.cos(x), actual, EPS);
        }
    }

    @ParameterizedTest(name = "cos({0}) = {1}")
    @DisplayName("Check values from table")
    @CsvFileSource(resources = "/table_values_cos.csv", numLinesToSkip = 1, delimiter = ';')
    void checkTableValues(double x, double expected) {
        assertEquals(expected, Trig.cos(x), EPS);
    }

    @Test
    @DisplayName("Check evenness: cos(x) == cos(-x)")
    void checkEvenFunctionProperty() {
        for (int i = 0; i < 10_000; i++) {
            double x = ThreadLocalRandom.current().nextDouble(-1000.0, 1000.0);
            assertEquals(Trig.cos(x), Trig.cos(-x), EPS);
        }
    }

    @Test
    @DisplayName("Check periodicity: cos(x) == cos(x + 2πk)")
    void checkPeriodicityProperty() {
        double twoPi = 2.0 * Math.PI;
        for (int i = 0; i < 10_000; i++) {
            double x = ThreadLocalRandom.current().nextDouble(-100.0, 100.0);
            int k = ThreadLocalRandom.current().nextInt(-100, 101);
            assertEquals(Trig.cos(x), Trig.cos(x + k * twoPi), EPS);
        }
    }

    @Test
    @DisplayName("Fuzzy testing vs Math.cos")
    void checkRandomDots() {
        for (int i = 0; i < 200_000; i++) {
            double x = ThreadLocalRandom.current().nextDouble(-10_000.0, 10_000.0);
            assertEquals(Math.cos(x), Trig.cos(x), 1e-9);
        }
    }

    @Test
    @DisplayName("If cos(x, n) exists: check n behavior on a few points")
    void checkNParameterIfPresent() {
        assertAll(
                () -> assertEquals(1.0, Trig.cos(123.456, 0), 0.0),
                () -> assertThrows(IllegalArgumentException.class, () -> Trig.cos(1.0, -1)),
                () -> assertEquals(Math.cos(0.5), Trig.cos(0.5, 5_000), 1e-10),
                () -> assertEquals(Math.cos(10.0), Trig.cos(10.0, 20_000), 1e-9)
        );
    }
}