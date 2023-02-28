import static java.lang.Math.*;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String args[]) {
        int[] c = task1();
        float[] x = task2();
        double[][] s = task3(c, x);
        task4(s);
    }

    public static int[] task1() {
        int[] c = new int[18];
        for (int i = 3; i <= 20; i++)
            c[i - 3] = i;
        return c;
    }

    public static float[] task2() {
        float[] x = new float[19];
        float l = -7.0f, r = 3.0f;
        for (int i = 0; i < x.length; i++)
            x[i] = ((float) Math.random()) * (r - l) + l;
        return x;
    }

    public static double[][] task3(int[] c, float[] x) {
        List<Integer> l = Arrays.asList(4, 6, 12, 13, 14, 15, 16, 18, 19);
        double[][] s = new double[18][19];
        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < 19; j++) {
                if (c[i] == 8)
                    s[i][j] = exp(exp(log(abs(x[j]))));
                else if (l.contains(c[i]))
                    s[i][j] = asin(exp(cbrt(-acos((x[j] - 2) / (1E+1)))));
                else
                    s[i][j] = pow(pow(atan((3 / 4) * ((x[j] - 2) / (1E+1))), 2 * pow(2 / tan(x[j]), tan(x[j]))),
                            2 * cbrt(cbrt(cos(x[j]))));
            }
        }
        return s;
    }

    public static void task4(double[][] s) {
        for (int i = 0; i < s.length; i++) {
            for (int j = 0; j < s[i].length; j++) {
                System.out.printf("%9.5f ", s[i][j]);
            }
            System.out.printf("\n");
        }
    }
}