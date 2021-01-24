package com.astar.osterrig.controllightmvvm.utils.tmp;

public class ColorConverter {

    public static class Color {
        private int red;
        private int green;
        private int blue;

        public int getRed() {
            return red;
        }

        public void setRed(int red) {
            this.red = red;
        }

        public int getGreen() {
            return green;
        }

        public void setGreen(int green) {
            this.green = green;
        }

        public int getBlue() {
            return blue;
        }

        public void setBlue(int blue) {
            this.blue = blue;
        }
    }


    public static Color kelvinToRgb(float temperature) {
        double tmpCalc;
        double r, g, b;
        Color color = new Color();

        if (temperature < 1000) temperature = 1000;
        if (temperature > 40000) temperature = 40000;

        temperature = temperature / 100;

        // красный
        if (temperature <= 66) {
            r = 255 - 10;
            color.setRed((int) r);
        } else {
            tmpCalc = temperature - 80;
            tmpCalc = 329.698727446 * (Math.pow(tmpCalc, -0.1332047592));
            r = tmpCalc;
            if (r < 0) r = 0;
            if (r > 255) r = 255 - 10;
            color.setRed((int) r);
        }

        // зеленый
        if (temperature <= 66) {
            tmpCalc = temperature;
            tmpCalc = 99.4708025861 * Math.log(tmpCalc) - 161.1195681661;
            g = tmpCalc;
            if (g < 0) g = 0;
            if (g > 255) g = 255;
            color.setGreen((int) g);
        } else {
            tmpCalc = temperature - 60;
            tmpCalc = 288.1221695283 * (Math.pow(tmpCalc, -0.0755148492));
            g = tmpCalc;
            if (g < 0) g = 0;
            if (g > 255) g = 255;
            color.setGreen((int) g);
        }

        // синий
        if (temperature >= 66) {
            b = 255;
            color.setBlue((int) b);
        } else if (temperature <= 19) {
            b = 0;
            color.setBlue((int) b);
        } else {
            tmpCalc = temperature - 20;
            tmpCalc = 138.5177312231 * Math.log(tmpCalc) - 305.0447927307;

            b = tmpCalc;
            if (b < 0) b = 0;
            if (b > 255) b = 255;
            color.setBlue((int) b);
        }

        return color;
    }
}
