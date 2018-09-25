package TMining;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.AbstractMap;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import sun.reflect.generics.tree.Tree;
public class Dokumen implements Comparable {
    private String name;
    private String[] raw_lines;
    private String[] lines;
    private String[] terms;
    public Dokumen(String name, String string) throws FileNotFoundException {
        this(name, new String[]{string});
    }
    public Dokumen(String name, FileReader file) throws FileNotFoundException {
        this(name, TextMining.load_arr(file));
    }
    public Dokumen(String name, String[] lines) {
        this.name = name;
        this.lines = lines.clone();
        this.raw_lines = lines.clone();
        for (int i = 0; i < this.lines.length; i++) {
            this.lines[i] = TextMining.clean(this.lines[i]);
        }
        terms = TextMining.Tokenize(this.lines);
        for (HashMap<String, String> ld : MainClass.lemma_dict) {
            terms = TextMining.lemmatization(terms, ld);
        }
        for (String[] sw : MainClass.stop_word) {
            terms = TextMining.remove_string(terms, sw);
        }
        //  terms = TextMining.reduce(terms);
    }
    public String getName() {
        return name;
    }
//    public String[] getLines() {
//        return lines.clone();
//    }
    public String[] getLines() {
        return lines.clone();
    }
    public String[] getUncleanedLines() {
        return raw_lines.clone();
    }
    public String[] getToken() {
        return TextMining.Tokenize(lines);
    }
    public String[] getLematizedToken() {
        String[] terms = this.terms;
        for (HashMap<String, String> ld : MainClass.lemma_dict) {
            terms = TextMining.lemmatization(terms, ld);
        }
        return terms;
    }
    public String[] getTerms() {
        String[] token = getLematizedToken();
        for (String[] sw : MainClass.stop_word) {
            token = TextMining.remove_string(token, sw);
        }
        return TextMining.reduce(token);
    }
    public HashMap<String, Integer> getMapping() {
        return TextMining.word_count(getTerms());
    }
    public HashMap<String, Double> getLTF() {
        HashMap<String, Integer> map = getMapping();
        HashMap<String, Double> TF = new HashMap<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            TF.put(entry.getKey(), 1 + Math.log10(entry.getValue()));
        }
        return TF;
    }
    @Override
    public int compareTo(Object t) {
        return name.compareToIgnoreCase(((Dokumen) t).getName());
    }
    private static class MapPrinter<K, V> {
        private Map<K, V> map;
        public MapPrinter(Map<K, V> map) {
            this.map = map;
        }
        public String toString() {
            StringBuilder sb = new StringBuilder();
            Iterator<Entry<K, V>> iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Entry<K, V> entry = iter.next();
                sb.append(String.format("%-15s%-5s%-5s", entry.getKey(), "", entry.getValue()));
                sb.append("\n");
            }
            return sb.toString();
        }
    }
    public void print_lines() {
        for (String line : lines) {
            System.out.println(line);
        }
    }
    public void print_Raw_lines() {
        for (String line : raw_lines) {
            System.out.println(line);
        }
    }
    public String get_Raw_Lines() {
        StringBuilder ret = new StringBuilder();
        for (String line : raw_lines) {
            ret.append(line + "\n");
        }
        return ret.toString();
    }
    public void print_terms() {
        String[] terms = getTerms();
        for (String term : terms) {
            System.out.println(term);
        }
    }
    public void print_terms_counts() {
        HashMap<String, Integer> map = getMapping();
        MapPrinter<String, Integer> printer = new MapPrinter<>(map);
        System.out.println(printer.toString());
    }
    public void print_LTF() {
        HashMap<String, Double> map = getLTF();
        MapPrinter<String, Double> printer = new MapPrinter<>(map);
        System.out.println(printer.toString());
    }
//    public HashMap<String, Map.Entry<String, Integer>> getMapperRawToTerms() {
//        return TextMining.Term_mapper(lines, MainClass.lemma_dict.toArray(new HashMap[0]), MainClass.stop_word.toArray(new String[0][0]));
//    }
//    public void printMapperRawToTerms() {
//        MapPrinter<String, String> printer = new MapPrinter<>(getMapperRawToTerms());
//        System.out.println(printer.toString());
//    }
    public void printFormatted() {
        System.out.println(getFormatted());
    }
    public String getFormatted() {
        StringBuilder ret = new StringBuilder();
        ret.append(String.format("Dokumen : %s \n", getName()));
        ret.append(String.format("Lines : "));
        ret.append(get_Raw_Lines() + "\n");
        System.out.println("Raw line");
        ret.append(String.format("%-15s | %-15s | %-15s | %-15s\n", "Token", "Lemmatized", "Filtered", "Terms"));
//        HashMap<String, Integer> Lemmatized = TextMining.getDumpMap(getLematizedToken());
//        HashMap<String, Integer> Filtered = TextMining.getDumpMap(getTerms());
        HashMap<String, Integer> Terms = new HashMap<>();
        HashMap<String, Integer>[] stop = new HashMap[MainClass.stop_word.size()];
        for (int i = 0; i < stop.length; i++) {
            stop[i] = new HashMap<>();
            for (String s : MainClass.stop_word.get(i)) {
                stop[i].put(s, 1);
            }
        }
//        HashMap<String, Map.Entry<String, Integer>> Mapped_Term = getMapperRawToTerms();
//        for (Map.Entry<String, Map.Entry<String, Integer>> entry : Mapped_Term.entrySet()) {
//            for (int i = 0; i < entry.getValue().getValue(); i++) {
//        String Token = "", Lemma = entry.getKey(), Filt = "", Terms = "";
//        Token = entry.getKey();
//                if (!Term.containsKey(entry.getValue().getKey())) {
//                    Terms = entry.getValue().getKey();
//                }
//                if (Lemmatized.containsKey(entry.getValue().getKey())) {
//                    Lemma = entry.getValue().getKey();
//                }
//                if (Filtered.containsKey(entry.getValue().getKey())) {
//                    Filt = entry.getValue().getKey();
//                }
        String[] token = TextMining.Tokenize(TextMining.clean(lines));
        token = TextMining.remove_string(token, new String[]{""});
        for (String token1 : token) {
            String Token = token1;
            String Lemma = token1;
            for (HashMap<String, String> map : MainClass.lemma_dict) {
                Lemma = TextMining.lemmatization(Lemma, map);
            }
            String Filt = Lemma;
            for (HashMap<String, Integer> map : stop) {
                Filt = TextMining.remove_string(Filt, map);
            }
            String Term = Filt;
            if (Terms.containsKey(Term)) {
                Term = "";
            } else {
                Terms.put(Term, 1);
            }
            ret.append(String.format("%-15s | %-15s | %-15s | %-15s\n", Token, Lemma, Filt, Term));
        }
//            }
//    }
        System.out.println(name
                + " Done");
        return ret.toString();
    }
}
