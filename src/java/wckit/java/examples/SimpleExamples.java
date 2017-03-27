package wckit.java.examples;

import wckit.core;
import wckit.java.core.IWCKit;

import java.util.Arrays;

public class SimpleExamples {

    public static IWCKit bananaExample =
        core.createNew()
            .input(Arrays.asList("https://en.wikipedia.org/wiki/Banana"))
            .backgroundColor("blue")
            .color("red");

    public static IWCKit pinkFloydExample =
            core.createNew()
                .input(Arrays.asList("./resources/freedata/pinkfloyd.txt"))
                .backgroundColor("black")
                .color("blue")
                .fontSizeMin(20L)
                .fontSizeMax(220L)
                .width(1600L)
                .height(800L)
                .padding(20L)
                .filters(Arrays.asList("top-english"))
                ;
}
