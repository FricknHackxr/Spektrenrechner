package Spektren;

import java.awt.Color;

public final class Farben {
    public static Color getFarbe(int wellenlänge) {
        if (390 < wellenlänge && wellenlänge < 430) {
            return new Color(255, 0, 255);
        } else if (430 < wellenlänge && wellenlänge < 490) {
            return new Color(0, 0, 255);
        } else if (490 < wellenlänge && wellenlänge < 570) {
            return new Color(0, 255, 0);
        } else if (570 < wellenlänge && wellenlänge < 600) {
            return new Color(255, 255, 0);
        } else if (600 < wellenlänge && wellenlänge < 620) {
            return new Color(255, 125, 0);
        } else if (620 < wellenlänge && wellenlänge < 780) {
            return new Color(255, 0, 0);
        } else {
            return new Color(0, 0, 0);
        }
    }
}
