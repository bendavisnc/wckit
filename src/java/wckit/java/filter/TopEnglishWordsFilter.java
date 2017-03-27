//package wckit.java.filter;
//
//import com.kennycason.kumo.nlp.filter.Filter;
//import com.kennycason.kumo.nlp.filter.StopWordFilter;
//import org.apache.commons.io.IOUtils;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.util.List;
//
//public class TopEnglishWordsFilter extends Filter {
//
//    private final StopWordFilter coreFilter;
//
//    public TopEnglishWordsFilter() {
//        this.coreFilter = new StopWordFilter(getTopWords());
//    }
//
//    private List<String> getTopWords() {
//        try {
//            return IOUtils.readLines(
//                new FileInputStream(
//                    new File("resources/freedata/top100EnglishWords.txt")));
////                this.getClass()
////                .getResourceAsStream("resources/freedata/top100EnglishWords.txt"));
//        } catch (IOException e) {
//            throw new RuntimeException("Problem retrieving top 100 english words resource data.");
//        }
//    }
//
//
//    @Override
//    public boolean apply(String s) {
//        return coreFilter.apply(s);
//    }
//}
