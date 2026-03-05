package lab1.task1.utils;

public class Trig {

    private static final double DEFAULT_EPS = 1e-12;
    private static final int DEFAULT_MAX_TERMS = 100_000;

    public static double cos(double x) {
        return cos(x, DEFAULT_MAX_TERMS, DEFAULT_EPS);
    }

    public static double cos(double x, int n) {
        return cos(x, n, DEFAULT_EPS);
    }

    /**
     * Реализация разложения cos(x) в степенной ряд:
     * cos(x) = 1 - x^2/2! + x^4/4! - ...
     *
     * @param x аргумент функции
     * @param n максимальное количество членов ряда
     * @param eps точность остановки (по модулю очередного члена ряда)
     * @return значение cos(x)
     */
    public static double cos(double x, int n, double eps) {
        // NaN -> NaN, +/-Infinity -> NaN (как Math.cos)
        if (Double.isNaN(x) || Double.isInfinite(x)) return Double.NaN;

        if (n < 0) throw new IllegalArgumentException("n must be non-negative");
        if (eps <= 0) throw new IllegalArgumentException("eps must be positive");
        if (n == 0) return 1.0;

        // Нормализация для лучшей сходимости
        x = normalizeToPi(x);

        double res = 1.0;
        double term = 1.0;

        // term_{k+1} = term_k * ( -x^2 / ((2k+1)(2k+2)) )
        for (int k = 0; k < n; k++) {
            double denom = (2.0 * k + 1.0) * (2.0 * k + 2.0);
            term *= (-x * x) / denom;
            res += term;

            // Остановка по малости очередного члена ряда
            if (Math.abs(term) < eps) break;

            // Доп. защита от численных проблем
            if (Double.isNaN(res) || Double.isInfinite(res)) return Double.NaN;
        }

        return res;
    }

    private static double normalizeToPi(double x) {
        double twoPi = 2.0 * Math.PI;
        x = x % twoPi;
        if (x > Math.PI) x -= twoPi;
        if (x < -Math.PI) x += twoPi;
        return x;
    }
}