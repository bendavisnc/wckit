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

import java.awt.*;
import java.awt.List;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.jar.Manifest;

/**
 * HIGHLY based (copy n paste) on com.kennycason.kumo.cli.KumoCli.
 *   Basically the same thing, but uses an wckit.java.core.IWCKit for input.
 */
public class WCBuilder {

    private IWCKit wcKit;

    public WCBuilder(IWCKit wcK) {
        this.wcKit = wcK;
    }

    public WordCloud buildWordCloud() {
        switch(this.wcKit.getType().ordinal()) {
            case 1:
                return this.buildStandardWordCloud();
            case 2:
                return this.buildPolarWordCloud();
            case 3:
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

    private java.util.List<WordFrequency> loadFrequencies(String input) {
        try {
            FrequencyAnalyzer e = new FrequencyAnalyzer();
            e.setWordFrequenciesToReturn(this.wcKit.getWordCount());
            e.setMinWordLength(this.wcKit.getMinWordLength());
            e.setStopWords(this.wcKit.getStopWords());
            e.setCharacterEncoding(this.wcKit.getCharacterEncoding());
            if(this.wcKit.getNormalizers().isEmpty()) {
                this.wcKit.getNormalizers().addAll(Arrays.asList(new CliParameters.NormalizerType[]{CliParameters.NormalizerType.TRIM, CliParameters.NormalizerType.CHARACTER_STRIPPING, CliParameters.NormalizerType.LOWERCASE}));
            }

            Iterator var3 = this.wcKit.getNormalizers().iterator();

            while(var3.hasNext()) {
                CliParameters.NormalizerType normalizer = (CliParameters.NormalizerType)var3.next();
                e.addNormalizer(this.buildNormalizer(normalizer));
            }

            e.setWordTokenizer(this.buildTokenizer());
            return e.load(toInputStream(input));
        } catch (IOException var5) {
            throw new RuntimeException(var5.getMessage(), var5);
        }
    }

    private WordTokenizer buildTokenizer() {
        switch(this.wcKit.getTokenizer().ordinal()) {
        case 1:
            return new WhiteSpaceWordTokenizer();
        case 2:
            return new EnglishWordTokenizer();
        case 3:
            return new ChineseWordTokenizer();
        default:
            throw new IllegalStateException("Unknown tokenizer: " + this.wcKit.getTokenizer());
        }
    }

    private Normalizer buildNormalizer(CliParameters.NormalizerType normalizer) {
        switch(normalizer.ordinal()) {
        case 1:
            return new LowerCaseNormalizer();
        case 2:
            return new UpperCaseNormalizer();
        case 3:
            return new BubbleTextNormalizer();
        case 4:
            return new CharacterStrippingNormalizer();
        case 5:
            return new UpsideDownNormalizer();
        case 6:
            return new TrimToEmptyNormalizer();
        default:
            throw new IllegalStateException("Unknown normalizer: " + normalizer);
        }
    }

    private KumoFont buildKumoFont(FontWeight fontWeight) {
        return new KumoFont(this.wcKit.getFontType(), fontWeight);
    }

    private static WordStartStrategy buildWordStart(CliParameters.WordStartType wordStartType) {
//        switch(null.$SwitchMap$com$kennycason$kumo$cli$CliParameters$WordStartType[wordStartType.ordinal()]) {
        switch(wordStartType.ordinal()) {
        case 1:
            return new CenterWordStart();
        case 2:
            return new RandomWordStart();
        default:
            throw new IllegalStateException("Unknown word start: " + wordStartType);
        }
    }

    private FontScalar buildFontScalar(CliParameters.FontScalarType fontScalarType) {
        switch(fontScalarType.ordinal()) {
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
