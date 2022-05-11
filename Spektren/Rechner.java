package Spektren;

import java.util.ArrayList;

public final class Rechner {

    public static double energie(int n1, int n2, double E_i) {
        double energie = E_i * (1.0 / (n2 * n2)) - (E_i * (1.0 / (n1 * n1)));
        return energie;
    }

    public static double wellenlänge(int n1, int n2, double E_i) {
        double energie = energie(n1, n2, E_i);
        double delta = (6.626 * Math.pow(10, -34) * 2.998 * 100_000_000) / (energie * 1.602 * Math.pow(10, -19));
        return Math.abs(delta);
    }

    public static int[] spektrum(double E_i, int n_max) {
        ArrayList<Integer> wellenlängen = new ArrayList<>();
        for (int i = 2; i < n_max; i++) {
            for (int j = 1; j < i; j++) {
                wellenlängen.add((int) (Math.round(wellenlänge(j, i, E_i) * 1_000_000_000)));
                System.out.println(
                        i + " -> " + j + ": " + (int) (Math.round(wellenlänge(j, i, E_i) * 1_000_000_000)) + " nm");
            }
        }
        int[] wellenlängen_array = new int[wellenlängen.size()];
        for (int i = 0; i < wellenlängen_array.length; i++) {
            wellenlängen_array[i] = wellenlängen.get(i);
        }
        return wellenlängen_array;
    }
}
