package lab1.task1.utils;

public class Trig {

    public static double cos(double x) {
        return cos(x, Integer.MAX_VALUE);
    }

    /**
     * Реализация разложения cos(x) в степенной ряд.
     *
     * @param x аргумент функции
     * @param n максимальное количество членов ряда для вычисления
     * @return значение cos(x)
     */
    public static double cos(double x, int n) {
        // NaN -> NaN, +/-Infinity -> NaN (как Math.cos)
        if (Double.isNaN(x)) return Double.NaN;
        if (Double.isInfinite(x)) return Double.NaN;

        if (n < 0) {
            throw new IllegalArgumentException("n must be non-negative");
        }
        // При n == 0 возвращаем первый член ряда: 1
        if (n == 0) return 1.0;

        // Нормализуем x в диапазон [-pi, pi] для лучшей сходимости
        x = normalizeToPi(x);

        // cos(x) = 1 - x^2/2! + x^4/4! - ...
        double res = 1.0;
        double term = 1.0;

        // Каждую итерацию добавляем следующий член ряда
        // term_{k+1} = term_k * ( -x^2 / ((2k+1)(2k+2)) )
        for (int k = 0; k < n; k++) {
            double old = res;

            double denom = (2.0 * k + 1.0) * (2.0 * k + 2.0);
            term *= (-x * x) / denom;
            res += term;

            // Проверка сходимости (можно заменить на eps, если по заданию так надо)
            if (Math.abs(res - old) < Double.MIN_VALUE) break;
        }

        return res;
    }

    private static double normalizeToPi(double x) {
        // Приведение к [-pi, pi] через остаток по 2pi
        double twoPi = 2.0 * Math.PI;
        x = x % twoPi; // Java % для double работает как remainder
        if (x > Math.PI) x -= twoPi;
        if (x < -Math.PI) x += twoPi;
        return x;
    }
}