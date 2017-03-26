package wckit.java.core;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.PolarBlendMode;
import com.kennycason.kumo.cli.CliParameters;
import com.kennycason.kumo.font.FontWeight;

import java.awt.*;
import java.io.File;
import java.util.List;

/**
 * Interface for core wckit usage.
 *   HIGHLY based on com.kennycason.kumo.cli.CliParameters.
 *     Essentially tries to be a super set that can exist as edn data.
 *     Every parameter should  be directly edn compatible.
 *
 */
public interface IWCKit {

    /**
     * Given a resource path to an edn config file - create an IWCKit.
     */
    IWCKit fromEdn(String filepath);

    /**
     * Create a word cloud .png at the specified filepath.
     */
    IWCKit spitPng(String filepath);
    IWCKit spitPng();

    /**
     *
     * Self returning setters...
     */

    /**
     * The type of word cloud to generate.
     */
    IWCKit wcType(String t);

    /**
     * One ore more input sources.
     * Input sources may be local files or Urls.
     * If more than one input source is provided they must be comma separated.
     * For standard word clouds only the first input source will be analyzed.
     * Multiple input sources are only relevant for polar or layered word clouds.
     */
    IWCKit input(List<String> ss);

    /**
     * Output file for the generated word cloud.
     */
    IWCKit output(String output);

    /**
     * Output file for the generated word cloud.
     */
    IWCKit minWordLength(Long n);

    /**
     * Number of words from data set to draw to word cloud.
     * After the words are sorted by frequency, the words are attempted to be placed in descending order.
     */
    IWCKit wordCount(Long n);

//    /**
//     * A comma separated list of words to exclude from the word cloud.
//     */
//    IWCKit stopWords(List<String> stopWords);
//
//    /**
//     * A file of stop words. Format should be one word per line.
//     */
//    IWCKit stopWordsFile(File stopWordsFile);

    /**
     * Width of the word cloud. Default is 640px.
     */
    IWCKit width(Long width);

    /**
     * Height of the word cloud. Default is 480px.
     */
    IWCKit height(Long height);

    /**
     * The collision algorithm to use when placing text into the word cloud.
     */
    IWCKit collision(String collisionMode);

    /**
     * The minimum padding allowed between two words in the word cloud.
     * This works with pixel-perfect collision detection as well.
     * Default is 2px.
     */
    IWCKit padding(Long n);

    /**
     * One ore more input sources.
     * Input sources may be local files or Urls of an image used to define the shape of the word cloud.
     * By default the word cloud is drawn onto a rectangle.
     * The word cloud will place text only in places where background image has non-transparent pixels.
     * For standard word clouds only the first input source will be used.
     * Multiple input sources are only relevant for layered word clouds.
     * Each background image will be applied to a layer in the order they are listed.
     */
    IWCKit backgrounds(List<String> bgs);

    /**
     * Background color. Default is Black.
     */
    IWCKit backgroundColor(String c);

    /**
     * A comma separated list of colors to use for the word cloud text.
     * Values must be provided in one of the below formats.
     * Refer to CLI.md for usage examples.
     */
    IWCKit color(String c);

    /**
     * Determine how to blend the two poles of the word cloud.
     */
    IWCKit polarBlendMode(String pbm);

    /**
     * Method to scale font. Default is Linear.
     */
    IWCKit fontScalar(String fs);

    /**
     * Minimum font size, default is 10px.
     */
    IWCKit fontSizeMin(Long fsm);

    /**
     * Maximum font size, default is 40px.
     */
    IWCKit fontSizeMax(Long fsm);

    /**
     * A font weight. Default is Bold.
     */
    IWCKit fontWeight(String fw);

    /**
     * The name of the font to use.
     * The system must have the font loaded already. Default is \"Comic Sans MS\"."
     */
    IWCKit fontType(String ft);

    /**
     * Character Encoding. Default is UTF-8
     */
    IWCKit encoding(String e);

    /**
     * Determine where to start drawing text to the word cloud.
     */
    IWCKit wordStart(String ws);

    /**
     * One or more normalizers to apply to words in the word cloud.
     */
    IWCKit normalizer(List<String> normalizers);

    /**
     * Determine where to start drawing text to the word cloud.
     */
    IWCKit tokenizer(String t);


    /**
     *
     * public getters
     */

    public List<String> getBackgrounds();

    public CollisionMode getCollisionMode();

    public Color getBackgroundColor();

    public List<Color> getColors();
    public Object getRawColorVal();

    public List<List<Color>> getLayeredColors();

    public PolarBlendMode getPolarBlendMode();

    public CliParameters.FontScalarType getFontScalarType();

    public int getFontSizeMax();

    public int getFontSizeMin();

    public String getFontType();

    public FontWeight getFontWeight();

    public String getCharacterEncoding();

    public int getHeight();

    public List<String> getInputSources();

    public int getMinWordLength();

    public List<CliParameters.NormalizerType> getNormalizers();

    public String getOutputSource();

    public int getPadding();

//    public List<String> getStopWords();

//    public String getStopWordsFile();

    public CliParameters.TokenizerType getTokenizer();

    public CliParameters.Type getType();

    public int getWidth();

    public int getWordCount();

    public CliParameters.WordStartType getWordStartType();

}

