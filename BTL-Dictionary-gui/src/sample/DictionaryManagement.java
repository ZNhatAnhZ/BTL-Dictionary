package sample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.*;
import java.util.*;

public class DictionaryManagement {
    private Dictionary Dict = new Dictionary();
    private Scanner sc = new Scanner(System.in);
    private Set<String> FavWord= new HashSet<>();
    private Set<String> HisWord= new HashSet<>();

    // Singleton implement
    private static DictionaryManagement single_instance = null;
    private DictionaryManagement() {}
    public static DictionaryManagement getInstance() {
        if (single_instance == null) single_instance = new DictionaryManagement();
        return single_instance;
    }
    // end of implement

    public void insertFromCommandline() {
        System.out.print("Nhập số lượng từ vựng muốn thêm vào: ");
        int n = sc.nextInt();
        for(int i=0; i < n; i++) {
            Word temp = new Word();
            System.out.print("Nhập từ vựng tiếng anh muốn thêm vào: ");
            temp.setWord_target(sc.next());
            sc.nextLine();
            System.out.print("Giải thích nghĩa của từ vừa thêm vào: ");
            temp.setWord_explain(sc.nextLine());
            Dict.addWord(temp);
            System.out.print("\n");
        }
    }
    public void printAllWords() {
        int n = Dict.getWord_count();
        if (n != 0) {
            System.out.println("No   | English             | Vietnamese");
            for(int i=0 ;i<n; i++) {
                Word temp = Dict.getWord(i);
                String s = String.format("%-5d| %-20s| %s", i+1, temp.getWord_target(), temp.getWord_explain());
                System.out.println(s);
            }
        }
    }
    public void insertFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("dictionaries.txt"));
            String s;
            while((s = br.readLine()) != null) {
                String temp[] = s.split("\t");
                Word newword = new Word(temp[0], temp[1]);
                Dict.addWord(newword);
            }
            br.close();
        } catch (Exception ex) {
            System.out.println("Can't find the file");
            return;
        }
    }
    public void dictionaryExportToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("dictionaries.txt"));
            for(int i=0; i < Dict.getWord_count(); i++) {
                Word temp = new Word(Dict.getWord(i));
                String s = temp.getWord_target() + "\t" + temp.getWord_explain() + "\n";
                bw.write(s);
            }
            bw.close();
        } catch (Exception ex) {
            System.out.println("Error while writing the file");
            return;
        }
    }
    public void addFavWord(String word_target) {
        FavWord.add(word_target);
    }
    public ArrayList<String> getFavWord() {
        ArrayList<String> temp = new ArrayList<>(FavWord);
        return temp;
    }
    public boolean isFavWord(String word_target) {
        if (FavWord.contains(word_target)) return true;
        return false;
    }
    public void delFavWord(String word_target) {
        FavWord.remove(word_target);
    }
    public void addHisWord(String word_target) {
        HisWord.add(word_target);
    }
    public ArrayList<String> getHisWord() {
        ArrayList<String> temp = new ArrayList<>(HisWord);
        return temp;
    }
    public ArrayList<String> searcherCustom(String s, ArrayList<String> x) {
        ArrayList<String> temp = new ArrayList<>();
        for(int i=0; i < x.size(); i++) {
            if (x.get(i).startsWith(s)) {
                temp.add(x.get(i));
            }
        }
        return temp;
    }
    public String dictionaryLookup(String word_target) {
        if (Dict.word_explainLookup(word_target) != null) {
            return Dict.word_explainLookup(word_target);
        }
        else {
            return null;
        }
    }
    public void changeWordAndPronoun(Word new_word, String pronoun, int index) {
        Dict.changeWordAndPronoun(new_word, pronoun, index);
    }
    public void deleteWordAndPronoun(String delete_word_target) {
        Word deleteWord = new Word(delete_word_target, Dict.word_explainLookup(delete_word_target));
        Dict.deleteWordAndPronoun(deleteWord);
    }
    public ArrayList<String> Searcher(String s) {
        return Dict.searcherWord(s);
    }
    public void insertFromMySQL() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/testing?characterEncoding=UTF-8&&" + "useSSL=false","root","1892001");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("SELECT * FROM dictionary_data");
            while(rs.next()) {
                Word temp = new Word(rs.getString("word"), rs.getString("detail"));
                Dict.addWord(temp);
            }
            ResultSet res=stmt.executeQuery("SELECT * FROM pronunciation");
            while(res.next()) {
                Dict.addPronun(res.getString("pronun"));
            }

        }catch(Exception e){ System.out.println(e);}
    }
    public String getPronun(String s) {
        return Dict.getPronun(s);
    }
    public void addPronun(String pronun) {
        Dict.addPronun(pronun);
    }
    public ArrayList<String> getAllWord_Target() {
        return Dict.getAllWord_Target();
    }
    public void addWord(String Word_target, String Word_explain) {
        Dict.addWord(new Word(Word_target, Word_explain));
    }
    public int getWordId(String word_target) {
        return Dict.getWordId(word_target);
    }
}
