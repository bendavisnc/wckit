package wckit.addcore;

import clojure.lang.PersistentVector;
import com.kennycason.kumo.CollisionMode;


import java.io.File;
import java.util.List;

/**
 * Interface for core wckit usage.
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
    IWCKit fontcolor(String... colors);


    /**
     *
     * @param sizes Data representing the minimum and maximum font size scale.
     * @return self
     */
    public IWCKit fontsize(Long... sizes);


    /**
     *
     * @param s Data representing font style.
     * @return self
     */
    public IWCKit fontstyle(String s);

    /**
     *
     * @param v Set the minimum for font size scale.
     * @return self
     */
    IWCKit minfontsize(Long v);

    /**
     *
     * @param v Set the maximum for font size scale.
     * @return self
     */
    IWCKit maxfontsize(Long v);

    /**
     *
     * @param m Which collision mode algorithm to use.
     * @return self
     */
    IWCKit collisionmode(CollisionMode m);

    /**
     *
     * @param c The plain background color to use for the render.
     * @return self
     */
    IWCKit backgroundcolor(String c);


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
    IWCKit spitpng(String filepath);

}
