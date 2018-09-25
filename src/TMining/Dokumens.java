package TMining;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
public class Dokumens {
    private List<Dokumen> docs = new LinkedList<>();
    public Dokumens(Dokumen[] docs) {
        for (Dokumen m : docs) {
            this.docs.add(m);
        }
        Collections.sort(this.docs, new Comparator<Dokumen>() {
            @Override
            public int compare(Dokumen t, Dokumen t1) {
                return t.getName().compareTo(t1.getName());
            }
        });
    }
    public Dokumens(){}
    public int getDocCount(){
        return docs.size();
    }
    public Dokumen getDoc(String name) {
        for (Dokumen doc : docs) {
            if (doc.getName().equalsIgnoreCase(name)) {
                return doc;
            }
        }
        System.err.println("Dokumen does not exists");
        System.exit(1);
        return null;
    }
    public void addDoc(Dokumen d){
        docs.add(d);
        Collections.sort(this.docs, new Comparator<Dokumen>() {
            @Override
            public int compare(Dokumen t, Dokumen t1) {
                return t.getName().compareTo(t1.getName());
            }
        });
    }
    public HashMap<String, Double> getIDF() {
        HashMap<String, Double> IDF = getDF();
        for (Map.Entry<String, Double> entry : IDF.entrySet()) {
            IDF.replace(entry.getKey(), Math.log10(docs.size() / entry.getValue()));
        }
        return IDF;
    }
    public HashMap<String, Double> getDF() {
        HashMap<String, Double> DF = new HashMap<>();
        for (Dokumen doc : docs) {
            HashMap<String, Integer> term_count = doc.getMapping();
            for (Map.Entry<String, Integer> entry : term_count.entrySet()) {
                if (DF.containsKey(entry.getKey())) {
                    DF.replace(entry.getKey(), DF.get(entry.getKey()) + 1);
                } else {
                    DF.put(entry.getKey(), 1D);
                }
            }
        }
        return DF;
    }
    public HashMap<Dokumen, HashMap<String, Double>> getLTF() {
        HashMap<Dokumen, HashMap<String, Double>> DTF = new HashMap<>();
        for (Dokumen doc : docs) {
            HashMap<String, Double> TF = doc.getLTF();
            DTF.put(doc, TF);
        }
        return DTF;
    }
    public HashMap<Dokumen, HashMap<String, Double>> getTF() {
        HashMap<Dokumen, HashMap<String, Double>> DTF = new HashMap<>();
        for (Dokumen doc : docs) {
            HashMap<String, Integer> TF_temp = doc.getMapping();
            HashMap<String, Double> TF = new HashMap<>();
            for (Map.Entry<String, Integer> terms : TF_temp.entrySet()) {
                TF.put(terms.getKey(), (double) terms.getValue());
            }
            DTF.put(doc, TF);
        }
        return DTF;
    }
    public HashMap<Dokumen, HashMap<String, Double>> getTFIDF() {
        HashMap<String, Double> IDF = getIDF();
        HashMap<Dokumen, HashMap<String, Double>> TFIDF = getLTF();
        TFIDF.entrySet().stream().parallel().forEach((entry) -> {
            entry.getValue().entrySet().stream().forEach((terms) -> {
                entry.getValue().replace(terms.getKey(), terms.getValue() * IDF.get(terms.getKey()));
            });
        });
        return TFIDF;
    }
//    public HashMap<Dokumen, HashMap<String, Double>> getLTF() {
//        HashMap<String, Double> IDF = getIDF();
//        HashMap<Dokumen, HashMap<String, Double>> LTF = getDTF();
//        for (Map.Entry<Dokumen, HashMap<String, Double>> entry : LTF.entrySet()) {
//            for (Map.Entry<String, Double> terms : entry.getValue().entrySet()) {
//                entry.getValue().replace(terms.getKey(), terms.getValue());
//            }
//        }
//        return LTF;
//    }
    public String[] getAllTerms() {
        Set<String> terms = new HashSet<>();
        for (Dokumen doc : docs) {
            terms.addAll(Arrays.asList(doc.getTerms()));
        }
        return terms.toArray(new String[0]);
    }
    public void print_TFIDF_formatted() {
        printWeightFormatted(getTFIDF(), "TF-IDF");
    }
//    public void print_DTF_formatted() {
//        printWeightFormatted(getDTF(), "DTF");
//    }
    public void print_LTF_formatted() {
        printWeightFormatted(getLTF(), "LTF");
    }
    public void print_TF_formatted() {
        printWeightFormatted(getTF(), "TF");
    }
    public void print_IDF() {
        System.out.println("IDF");
        HashMap<String, Double> map = getIDF();
        MapPrinter<String, Double> printer = new MapPrinter<>(map);
        System.out.println(printer.toString());
    }
    public void print_DF() {
        System.out.println("DF");
        HashMap<String, Double> map = getDF();
        MapPrinter<String, Double> printer = new MapPrinter<>(map);
        System.out.println(printer.toString());
    }
    public String get_TFIDF_formatted() {
        return getWeightFormatted(getTFIDF(), "TF-IDF");
    }
//    public void print_DTF_formatted() {
//        printWeightFormatted(getDTF(), "DTF");
//    }

    public String get_LTF_formatted() {
        return getWeightFormatted(getLTF(), "LTF");
    }
    public String get_TF_formatted() {
        return getWeightFormatted(getTF(), "TF");
    }
    public String get_IDF() {
        System.out.println("IDF");
        HashMap<String, Double> map = getIDF();
        MapPrinter<String, Double> printer = new MapPrinter<>(map);
        return printer.toString();
    }
    public String get_DF() {
        System.out.println("DF");
        HashMap<String, Double> map = getDF();
        MapPrinter<String, Double> printer = new MapPrinter<>(map);
        return printer.toString();
    }
    private String getWeightFormatted(HashMap<Dokumen, HashMap<String, Double>> map, String p) {
       StringBuilder ret = new StringBuilder();
        List<String> t = new LinkedList<>();
        t.addAll(Arrays.asList(getAllTerms()));
        Collections.sort(t);
        String[] terms = t.toArray(new String[0]);
        TreeMap<Dokumen, HashMap<String, Double>> TFIDF = new TreeMap<>(map);
        ret.append(String.format("%-15s", p));
        for (Dokumen doc : docs) {
           ret.append(String.format("\t%-15s", doc.getName()));
        }
       ret.append(String.format("\n"));
        for (String s : terms) {
           ret.append(String.format("%-15s", s));
            for (Map.Entry<Dokumen, HashMap<String, Double>> entry : TFIDF.entrySet()) {
                if (entry.getValue().containsKey(s)) {
                    ret.append(String.format("\t%.12f", entry.getValue().get(s)));
                } else {
                    ret.append(String.format("\t%.12f", 0F));
                }
            }
            ret.append(String.format("\n"));
        }
        System.out.println("Done");
        return ret.toString();
    }
    private void printWeightFormatted(HashMap<Dokumen, HashMap<String, Double>> map, String p) {
        List<String> t = new LinkedList<>();
        t.addAll(Arrays.asList(getAllTerms()));
        Collections.sort(t);
        String[] terms = t.toArray(new String[0]);
        TreeMap<Dokumen, HashMap<String, Double>> TFIDF = new TreeMap<>(map);
        System.out.printf("%-15s", p);
        for (Dokumen doc : docs) {
            System.out.printf("\t%-12s", doc.getName());
        }
        System.out.println("");
        for (String s : terms) {
            System.out.printf("%-15s", s);
            for (Map.Entry<Dokumen, HashMap<String, Double>> entry : TFIDF.entrySet()) {
                if (entry.getValue().containsKey(s)) {
                    System.out.printf("\t%.12f", entry.getValue().get(s));
                } else {
                    System.out.printf("\t%.12f", 0F);
                }
            }
            System.out.println("");
        }
    }
    
    private static class MapPrinter<K, V> {
        private Map<K, V> map;
        public MapPrinter(Map<K, V> map) {
            this.map = map;
        }
        public String toString() {
            StringBuilder sb = new StringBuilder();
            Iterator<Map.Entry<K, V>> iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<K, V> entry = iter.next();
                sb.append(String.format("%-15s%-5s%-5s", entry.getKey(), "", entry.getValue()));
                sb.append("\n");
            }
            return sb.toString();
        }
    }
    public void print_Doc_info() {
        for (Dokumen doc : docs) {
            System.out.println(doc.getName());
        }
    }
    public void printAllDocFormatted() {
        for (Dokumen doc : docs) {
            doc.printFormatted();
            System.out.println("");
        }
    }
    
    public String getAllDocFormatted(){
        String ret = "";
        ret = docs.stream().parallel().map((doc) -> doc.getFormatted()+"\n").reduce(ret, String::concat);
        return ret;
    }
}
