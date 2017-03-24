package wckit.addcore;

import clojure.lang.PersistentVector;
import com.kennycason.kumo.CollisionMode;


import java.awt.*;
import java.io.File;
import java.util.List;

/**
 * Interface for core wckit usage.
 *   HIGHLY based on com.kennycason.kumo.cli.CliParameters.
 *     Essentially tries to be a super set that can exist as edn data.
 */
public interface IWCKit {

    /**
     *
     * @param width The width of the resulting word cloud render.
     * @param height The height of the resulting word cloud render.
     * @return self
     */
    IWCKit size(Long width, Long height);


    /**
     *
     * @param n The maximum number of words to render.
     * @return self
     */
    IWCKit limit(Long n);


    /**
     *
     * @param colors A list (single value allowed) representing  a scale of color.
     * @return self
     */
    IWCKit fontColor(List<Color> colors);


    /**
     *
     * @param sizes Data representing the minimum and maximum font size scale.
     * @return self
     */
    public IWCKit fontSize(Long... sizes);


    /**
     *
     * @param s Data representing font style.
     * @return self
     */
    public IWCKit fontStyle(String s);

    /**
     *
     * @param v Set the minimum for font size scale.
     * @return self
     */
    IWCKit minFontSize(Long v);

    /**
     *
     * @param v Set the maximum for font size scale.
     * @return self
     */
    IWCKit maxFontSize(Long v);

    /**
     *
     * @param m Which collision mode algorithm to use.
     * @return self
     */
    IWCKit collisionMode(CollisionMode m);

    /**
     *
     * @param c The plain background color to use for the render.
     * @return self
     */
    IWCKit backgroundColor(String c);


    /**
     *
     * @param args Data representing input data.
     * @return self
     */
//    IWCKit input(List<Object> args);
    IWCKit input(File args);
//    IWCKit input(Object args);


    /**
     * The method that actually does the thing.
     * Spits out a wordcloud png according to what the filepath says.
     *
     * @param filepath
     * @return self
     */
    IWCKit spitPng(String filepath);

}
