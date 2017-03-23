package common;

import com.kennycason.kumo.palette.ColorPalette;

import java.awt.Color;



public class Wtf {

//    public static void whoknows(Integer... ints) {
    public static void whoknows(String... ints) {
//    public static void whoknows(String ints) {
        System.out.println("fucks sake");
        System.out.println(ints);
    }

    public static void saySomething() {
        System.out.println("ehlllo");
    }

    public static String[] testStrings = new String[] {"hello", "bob"};

    public static Color[] testColors = new Color[] {Color.BLACK, Color.BLUE};

    public static ColorPalette testColorPallette  = new ColorPalette(testColors);

}
