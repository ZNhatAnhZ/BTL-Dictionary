package sample;

import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class DictionaryCommandline {
    Scanner sc = new Scanner(System.in);
    private DictionaryManagement DictionaryUtility = new DictionaryManagement();

    public void showAllWords() {
        DictionaryUtility.printAllWords();
    }
    public void dictionaryBasic() {
        DictionaryUtility.insertFromCommandline();
        showAllWords();
    }
    public void dictionaryAdvanced() {
        DictionaryUtility.insertFromFile();
        DictionaryUtility.printAllWords();
        DictionaryUtility.dictionaryLookup(sc.nextLine());
    }
    public void dictionaryChangeWord() {
        DictionaryUtility.changeWord();
    }
    public void dictionaryDeleteWord() {
        DictionaryUtility.deleteWord();
    }
    public void dictionarySearcher() {
        System.out.println("Nhập từ cần tìm");
        String s = new String(sc.nextLine());
        ArrayList<String> temp = DictionaryUtility.Searcher(s);
        for(int i=0; i<temp.size(); i++) {
            System.out.print(temp.get(i) + ", ");
        }
    }
}
