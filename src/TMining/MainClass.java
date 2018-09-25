/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TMining;
import static TMining.TextMining.*;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
/**
 *
 * @author Edho
 */
public class MainClass {
    public static List<String[]> stop_word = new LinkedList<>();
    public static List<HashMap<String, String>> lemma_dict = new LinkedList<>();
    public static void main(String[] args) throws FileNotFoundException {
        stop_word.add(load_arr("stopword-en.txt"));
        lemma_dict.add(load_dictionary("lemmatization-en.txt", "\t"));
        Dokumen doc1 = new Dokumen("D1", "Dark Souls is an action role-playing game developed by FromSoftware and published by Namco Bandai Games. A spiritual successor to FromSoftware's Demon's Souls, the game is the second installment in the Souls series. ");
        Dokumen doc2 = new Dokumen("D2", "Dark Souls received critical acclaim upon its release and is considered to be one of the best video games ever released, with critics praising the depth of its combat, intricate level design, and world lore.");
        Dokumen doc3 = new Dokumen("D3", "Dark Souls is a third-person action role-playing game. The core mechanic of the game is exploration. Players are encouraged by the game to proceed with caution, learn from past mistakes, or find alternate areas to explore.");
//        Dokumen doc4 = new Dokumen("D4", "Another aspect of Dark Souls is the \"humanity\" system. There are two \"forms\" the player character can be in during the game, human form and hollow form. Whenever the player dies in human form, they are returned to hollow form and can only have their humanity restored by consuming an item (also called a \"humanity\").");
        Dokumens d = new Dokumens(new Dokumen[]{doc1, doc2, doc3});
        d.printAllDocFormatted();
        d.print_TF_formatted();
        System.out.println("");
        d.print_LTF_formatted();
        System.out.println("");
        d.print_DF();
        System.out.println("");
        d.print_IDF();
        System.out.println("");
//        d.print_DTF_formatted();
        System.out.println("");
        d.print_TFIDF_formatted();
        System.out.println("");
//        TreeMap<Dokumen, HashMap<String, Double>> DTF = new TreeMap(d.getTFIDF());
//        for (Map.Entry<Dokumen, HashMap<String, Double>> entry : DTF.entrySet()) {
//            System.out.printf("%s\n",entry.getKey().getName());
//            for(Map.Entry<String, Double> terms : entry.getValue().entrySet()){
//                System.out.printf("%-15s\t%f\n", terms.getKey(), terms.getValue());
//            }
//            System.out.println("");
//        }
        //d.print_TFIDF_formatted();
//        doc1.print_lines();
//        doc1.print_terms();
//        doc1.print_terms_counts();
//        doc1.print_LTF();
    }
}
