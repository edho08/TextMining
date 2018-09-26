package TMining;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
/**
 * Text Mining merupakan library yang dapat membantu proses Text Mining
 * (?) COPYLEFT ALL WRONGS RESERVED!!!
 */

/**
 * This Whole code is Uncommented. Please refer to nothing.
 * @author Edho
 */
public class TextMining {
    public static String[] Tokenize(String[] input) {
        List<String> list = new LinkedList<>();
        for (String s : input) {
            s = s.toLowerCase();
            list.addAll(Arrays.asList(s.trim().split(" ")));
        }
        return list.toArray(new String[0]);
    }

    public static String clean(String input) {
        return input.replaceAll("[^A-Za-z]", " ").replaceAll("[0-9]", "");
    }
    public static String[] clean(String[] input) {
        for (int i = 0; i < input.length; i++) {
            input[i] = input[i].replaceAll("[^A-Za-z]", " ").replaceAll("[0-9]", "");
        }
        return input;
    }
     /**
     *
     * @param input[] : Merupakan array String yang akan diproses
     * @param to_remove : Merupakan daftar String yang akan dibuang dari input
     * @return String yang
     */
    public static String[] remove_string(String[] input, String[] to_remove) {
        HashMap remove_map = new HashMap();
        for (String s : to_remove) {
            remove_map.put(s, 1);
        }
        List<String> list = new LinkedList<>();
        for (String s : input) {
            if (!remove_map.containsKey(s) && !s.equalsIgnoreCase("")) {
                list.add(s.replaceAll("[^A-Za-z]", " ").replaceAll("[0-9]", ""));
            }
        }
        return list.toArray(new String[0]);
    }
    public static String remove_string(String input, String[] to_remove) {
        HashMap remove_map = new HashMap();
        for (String s : to_remove) {
            remove_map.put(s, 1);
        }
        return remove_string(input, remove_map);
    }
    public static String remove_string(String input, HashMap<String, Integer> remove_map) {
        if (remove_map.containsKey(input)) {
            return "";
        } else {
            return input.replaceAll("[^A-Za-z]", " ").replaceAll("[0-9]", "");
        }
    }
    public static String[] lemmatization(String[] input, HashMap<String, String> dictionary) {
        List<String> list = new LinkedList<>();
        for (String s : input) {
            if (dictionary.containsKey(s.toLowerCase())) {
                list.add(dictionary.get(s));
            } else {
                list.add(s.toLowerCase());
            }
        }
        return list.toArray(new String[0]);
    }
    public static String lemmatization(String input, HashMap<String, String> dictionary) {
        List<String> list = new LinkedList<>();
        if (dictionary.containsKey(input)) {
            return dictionary.get(input);
        } else {
            return input;
        }
    }
//    public static HashMap<String, Map.Entry<String, Integer>> Term_mapper(String[] input, HashMap<String, String>[] dictionary, String[][] remove) {
//        for (int i = 0; i < input.length; i++) {
//            input[i] = clean(input[i]);
//        }
//        input = Tokenize(input);
//        HashMap<String, Map.Entry<String, Integer>> list = new HashMap<>();
//        HashMap<String, Integer>[] r_map = new HashMap[remove.length];
//        for (int i = 0; i < remove.length; i++) {
//            r_map[i] = new HashMap<>();
//            for (String s : remove[i]) {
//                r_map[i].put(s, 1);
//            }
//        }
//        for (String s : input) {
//            String term = "";
//            for (HashMap<String, String> dict : dictionary) {
//                if (dict.containsKey(s)) {
//                    term = dict.get(s);
//                    break;
//                }
//            }
//            if (list.containsKey(s)) {
//                list.get(s).setValue(list.get(s).getValue()+1);
//            }else{
//                list.put(s, new AbstractMap.SimpleEntry<>(s,1));
//            }
//        }
//        for (Map.Entry<String, Map.Entry<String, Integer>> term : list.entrySet()) {
//            for (HashMap<String, Integer> r_map_loop : r_map) {
//                if (r_map_loop.containsKey(term.getKey()) || r_map_loop.containsKey(term.getValue())) {
//                    term.setValue( new AbstractMap.SimpleEntry<>("", term.getValue().getValue()));
//                }
//            }
//        }
//        return list;
//    }
    public static HashMap<String, String> load_dictionary(String path, String delimiter) throws FileNotFoundException {
        FileReader fr = new FileReader(path);
        Scanner input = new Scanner(fr);
        HashMap<String, String> output = new HashMap<>();
        while (input.hasNextLine()) {
            String[] scan = input.nextLine().split(delimiter);
            output.put(scan[0].toLowerCase(), scan[1].toLowerCase());
        }
        return output;
    }
    public static String[] load_arr(String path) throws FileNotFoundException {
        FileReader fr = new FileReader(path);
        return load_arr(fr);
    }
    public static String[] load_arr(FileReader fr) throws FileNotFoundException {
        Scanner input = new Scanner(fr);
        List<String> output = new LinkedList<>();
        while (input.hasNextLine()) {
            String scan = input.nextLine();
            output.add(scan);
        }
        return output.toArray(new String[0]);
    }
    public static HashMap<String, Integer> word_count(String input) {
        String[] i = input.split(" ");
        return word_count(i);
    }
    public static HashMap<String, Integer> word_count(String[] input) {
        HashMap<String, Integer> count = new HashMap<>();
        for (String s : input) {
            if (s.trim().equalsIgnoreCase("")) {
                continue;
            }
            if (count.containsKey(s)) {
                count.replace(s, count.get(s) + 1);
            } else {
                count.put(s, 1);
            }
        }
        return count;
    }
    public static String[] reduce(String[] input) {
        HashMap<String, Integer> count = word_count(input);
        List<String> output = new LinkedList<>();
        for (Map.Entry<String, Integer> entry : count.entrySet()) {
            output.add(entry.getKey());
        }
        return output.toArray(new String[0]);
    }
    public static void print_map_word_count(HashMap<String, Integer> map) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.printf("%s\t%s\n", entry.getKey(), entry.getValue());
        }
    }
    public static void print_arr(String[] arr) {
        for (String s : arr) {
            System.out.println(s);
        }
    }
    public static HashMap<String, Integer> getDumpMap(String[] input) {
        HashMap<String, Integer> map = new HashMap<>();
        for (String s : input) {
            map.put(s, 1);
        }
        return map;
    }
}
