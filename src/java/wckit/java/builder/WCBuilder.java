package wckit.java.builder;

import com.kennycason.kumo.LayeredWordCloud;
import com.kennycason.kumo.PolarWordCloud;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.Background;
import com.kennycason.kumo.bg.PixelBoundryBackground;
import com.kennycason.kumo.cli.CliParameters;
import com.kennycason.kumo.font.FontWeight;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.FontScalar;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.font.scale.LogFontScalar;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.filter.Filter;
import com.kennycason.kumo.nlp.normalize.*;
import com.kennycason.kumo.nlp.tokenizer.ChineseWordTokenizer;
import com.kennycason.kumo.nlp.tokenizer.EnglishWordTokenizer;
import com.kennycason.kumo.nlp.tokenizer.WhiteSpaceWordTokenizer;
import com.kennycason.kumo.nlp.tokenizer.WordTokenizer;
import com.kennycason.kumo.palette.ColorPalette;
import com.kennycason.kumo.wordstart.CenterWordStart;
import com.kennycason.kumo.wordstart.RandomWordStart;
import com.kennycason.kumo.wordstart.WordStartStrategy;
import org.apache.commons.lang3.StringUtils;
import wckit.java.core.IWCKit;
import wckit.java.filter.FilterType;
//import wckit.java.filter.TopEnglishWordsFilter;

import java.awt.Color;
import java.awt.Dimension;
import java.io.*;
import java.net.URL;
import java.util.*;

import static com.kennycason.kumo.cli.CliParameters.NormalizerType.LOWERCASE;
import static org.apache.commons.lang.StringUtils.isBlank;

/**
 * HIGHLY based (copy n paste) on com.kennycason.kumo.cli.KumoCli.
 *   Basically the same thing, but uses an wckit.java.core.IWCKit for input.
 */
public class WCBuilder {

    private final IWCKit wcKit;

    public WCBuilder(IWCKit wcK) {
        this.wcKit = wcK;
    }

    public WordCloud buildWordCloud() {
//        switch(this.wcKit.getType().ordinal() + 1) {
        switch(this.wcKit.getType()) {
            case STANDARD:
                return this.buildStandardWordCloud();
            case POLAR:
                return this.buildPolarWordCloud();
            case LAYERED:
//                this.return buildLayeredWordCloud();
//                break;
                throw new UnsupportedOperationException("Unsupported type: (still todo)" + this.wcKit.getType());
            default:
                throw new UnsupportedOperationException("Unsupported type: " + this.wcKit.getType());
            }
    }

//    private LayeredWordCloud buildLayeredWordCloud() {
//        if(this.wcKit.getInputSources().size() == 1) {
//            return this.buildStandardWordCloud();
//        } else if(this.wcKit.getInputSources().size() != this.wcKit.getBackgrounds().size()) {
//            throw new IllegalArgumentException("Number of input sources does not equal the number of backgrounds.");
//        } else if(this.wcKit.getInputSources().size() != this.wcKit.getLayeredColors().size()) {
//            throw new IllegalArgumentException("Number of input sources does not equal the number of colors.");
//        } else {
//            LayeredWordCloud wordCloud = new LayeredWordCloud(this.wcKit.getInputSources().size(), new Dimension(this.wcKit.getWidth(), this.wcKit.getHeight()), this.wcKit.getCollisionMode());
//            wordCloud.setBackgroundColor(this.wcKit.getBackgroundColor());
//
//            for(int i = 0; i < this.wcKit.getInputSources().size(); ++i) {
//                wordCloud.setBackground(i, buildBackground((String)this.wcKit.getBackgrounds().get(i)));
//                wordCloud.setColorPalette(i, new ColorPalette((java.util.List)this.wcKit.getLayeredColors().get(i)));
//                wordCloud.setFontScalar(i, this.buildFontScalar(this.wcKit.getFontScalarType()));
//                wordCloud.setPadding(i, this.wcKit.getPadding());
//                wordCloud.setKumoFont(i, this.buildKumoFont(this.wcKit.getFontWeight()));
//                wordCloud.build(i, this.loadFrequencies((String)this.wcKit.getInputSources().get(i)));
//            }
//            return wordCloud;
//        }
//    }

    public static List<Color> getColors(String rawColorVal) {
        return StringUtils.isBlank(rawColorVal)? Collections.emptyList():(new CliParameters.ColorsConverter()).convert(rawColorVal);
    }

    public static List<List<Color>> getLayeredColors(String rawColorVal) {
        if (isBlank(rawColorVal)) {
            return Collections.emptyList();
        }
        final List<List<Color>> layeredColors = new ArrayList<>();
        for (final String layeredColorSet : rawColorVal.split("\\|")) {
            layeredColors.add(new CliParameters.ColorsConverter().convert(layeredColorSet));
        }
        return layeredColors;
    }

    private WordCloud buildPolarWordCloud() {
        if(this.wcKit.getInputSources().size() != 2) {
            throw new IllegalArgumentException("Polar word clouds require exactly 2 input sources. Found: " + this.wcKit.getInputSources().size());
        } else {
            PolarWordCloud wordCloud = new PolarWordCloud(new Dimension(this.wcKit.getWidth(), this.wcKit.getHeight()), this.wcKit.getCollisionMode(), this.wcKit.getPolarBlendMode());
            if(!this.wcKit.getBackgrounds().isEmpty()) {
                wordCloud.setBackground(buildBackground((String)this.wcKit.getBackgrounds().get(0)));
            }

            wordCloud.setBackgroundColor(this.wcKit.getBackgroundColor());
            if(this.wcKit.getLayeredColors().size() >= 1) {
                wordCloud.setColorPalette(new ColorPalette((java.util.List)this.wcKit.getLayeredColors().get(0)));
            }

            if(this.wcKit.getLayeredColors().size() >= 2) {
                wordCloud.setColorPalette2(new ColorPalette((java.util.List)this.wcKit.getLayeredColors().get(1)));
            }

            wordCloud.setFontScalar(this.buildFontScalar(this.wcKit.getFontScalarType()));
            wordCloud.setPadding(this.wcKit.getPadding());
            wordCloud.setWordStartStrategy(buildWordStart(this.wcKit.getWordStartType()));
            wordCloud.setKumoFont(this.buildKumoFont(this.wcKit.getFontWeight()));
            wordCloud.build(this.loadFrequencies((String)this.wcKit.getInputSources().get(0)), this.loadFrequencies((String)this.wcKit.getInputSources().get(1)));
            return wordCloud;
        }

    }

    private WordCloud buildStandardWordCloud() {
        WordCloud wordCloud = new WordCloud(new Dimension(this.wcKit.getWidth(), this.wcKit.getHeight()), this.wcKit.getCollisionMode());
        if(!this.wcKit.getBackgrounds().isEmpty()) {
            wordCloud.setBackground(buildBackground((String)this.wcKit.getBackgrounds().get(0)));
        }

        wordCloud.setBackgroundColor(this.wcKit.getBackgroundColor());
        if(!this.wcKit.getColors().isEmpty()) {
            wordCloud.setColorPalette(new ColorPalette(this.wcKit.getColors()));
        }

        wordCloud.setFontScalar(this.buildFontScalar(this.wcKit.getFontScalarType()));
        wordCloud.setPadding(this.wcKit.getPadding());
        wordCloud.setWordStartStrategy(buildWordStart(this.wcKit.getWordStartType()));
        wordCloud.setKumoFont(this.buildKumoFont(this.wcKit.getFontWeight()));
        wordCloud.build(this.loadFrequencies((String)this.wcKit.getInputSources().get(0)));
        return wordCloud;
    }


    private List<WordFrequency> loadFrequencies(final String input) {
        try {
            final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
            frequencyAnalyzer.setWordFrequenciesToReturn(this.wcKit.getWordCount());
            frequencyAnalyzer.setMinWordLength(this.wcKit.getMinWordLength());
//            frequencyAnalyzer.setStopWords(this.wcKit.getStopWords());
            frequencyAnalyzer.setCharacterEncoding(this.wcKit.getCharacterEncoding());

            if (this.wcKit.getNormalizers().isEmpty()) {
                this.wcKit.getNormalizers().addAll(Arrays.asList(CliParameters.NormalizerType.TRIM, CliParameters.NormalizerType.CHARACTER_STRIPPING, LOWERCASE));
            }
            for (final CliParameters.NormalizerType normalizer : this.wcKit.getNormalizers()) {
                frequencyAnalyzer.addNormalizer(buildNormalizer(normalizer));
            }

//            for (final FilterType filter : this.wcKit.getFilters()) {
//                frequencyAnalyzer.addFilter(buildFilter(filter));
//            }

            frequencyAnalyzer.setWordTokenizer(buildTokenizer());

            return frequencyAnalyzer.load(toInputStream(input));

        } catch (final IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private WordTokenizer buildTokenizer() {
        switch(this.wcKit.getTokenizer()) {
        case WHITE_SPACE:
            return new WhiteSpaceWordTokenizer();
        case ENGLISH:
            return new EnglishWordTokenizer();
        case CHINESE:
            return new ChineseWordTokenizer();
        default:
            throw new IllegalStateException("Unknown tokenizer: " + this.wcKit.getTokenizer());
        }
    }

    private Normalizer buildNormalizer(CliParameters.NormalizerType normalizer) {
        switch(normalizer) {
        case LOWERCASE:
            return new LowerCaseNormalizer();
        case UPPERCASE:
            return new UpperCaseNormalizer();
        case BUBBLE:
            return new BubbleTextNormalizer();
        case CHARACTER_STRIPPING:
            return new CharacterStrippingNormalizer();
        case UPSIDE_DOWN:
            return new UpsideDownNormalizer();
        case TRIM:
            return new TrimToEmptyNormalizer();
        default:
            throw new IllegalStateException("Unknown normalizer: " + normalizer);
        }
    }

//    private Filter buildFilter(FilterType filter) {
//        switch(filter) {
//            case TOP_ENGLISH:
//                return new TopEnglishWordsFilter();
//            default:
//                throw new IllegalStateException("Unknown filter: " + filter);
//        }
//    }

    private KumoFont buildKumoFont(FontWeight fontWeight) {
        return new KumoFont(this.wcKit.getFontType(), fontWeight);
    }

    private static WordStartStrategy buildWordStart(CliParameters.WordStartType wordStartType) {
//        switch(null.$SwitchMap$com$kennycason$kumo$cli$CliParameters$WordStartType[wordStartType.ordinal()]) {
        switch(wordStartType.ordinal() + 1) {
        case 1:
            return new CenterWordStart();
        case 2:
            return new RandomWordStart();
        default:
            throw new IllegalStateException("Unknown word start: " + wordStartType);
        }
    }

    private FontScalar buildFontScalar(CliParameters.FontScalarType fontScalarType) {
        switch(fontScalarType.ordinal() + 1) {
        case 1:
            return new LinearFontScalar(this.wcKit.getFontSizeMin(), this.wcKit.getFontSizeMax());
        case 2:
            return new SqrtFontScalar(this.wcKit.getFontSizeMin(), this.wcKit.getFontSizeMax());
        case 3:
            return new LogFontScalar(this.wcKit.getFontSizeMin(), this.wcKit.getFontSizeMax());
        default:
            throw new IllegalStateException("Unknown font scalar type: " + fontScalarType);
        }
    }

    private static Background buildBackground(String background) {
        try {
            return new PixelBoundryBackground(toInputStream(background));
        } catch (IOException var2) {
            throw new RuntimeException(var2.getMessage(), var2);
        }
    }

    private static InputStream toInputStream(String path) {
        File file = new File(path);
        if(file.exists() && !file.isDirectory()) {
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException var3) {
                throw new RuntimeException(var3.getMessage(), var3);
            }
        } else {
            try {
                return (new URL(path)).openStream();
            } catch (IOException var4) {
                throw new RuntimeException("Input path [" + path + "] not a file or url.");
            }
        }
    }

}
