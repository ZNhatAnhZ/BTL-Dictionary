package com.company;
import java.util.*;
import java.io.*;

public class DictionaryManagement {
    private Dictionary Dict = new Dictionary();
    private Scanner sc = new Scanner(System.in);

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
    public void dictionaryLookup() {
        System.out.print("Nhập từ cần tìm: ");
        String word_target = new String(sc.nextLine());
        if (Dict.word_explainLookup(word_target) != null) {
            System.out.println(Dict.word_explainLookup(word_target));
        }
        else {
            System.out.println("Không có từ đó trong từ điển");
        }
    }
    public void changeWord() {
        System.out.print("Nhập từ muốn thay đổi: ");
        String word_target_old = new String(sc.next());
        sc.nextLine();
        System.out.print("Nhập từ muốn thay đổi thành: ");
        String word_target_new = new String(sc.next());
        sc.nextLine();
        System.out.print("Giải thích nghĩa của từ đó: ");
        String word_explain_new = new String(sc.nextLine());
        Word newWord = new Word(word_target_new, word_explain_new);
        Word oldWord = new Word(word_target_old, Dict.word_explainLookup(word_target_old));
        Dict.changeWord(oldWord,newWord);
        dictionaryExportToFile();
    }
    public void deleteWord() {
        System.out.print("Nhập từ muốn xóa: ");
        String delete_word_target = new String(sc.next());
        sc.nextLine();
        Word deleteWord = new Word(delete_word_target, Dict.word_explainLookup(delete_word_target));
        Dict.deleteWord(deleteWord);
        dictionaryExportToFile();
    }
    public void Searcher() {
        System.out.print("Nhập từ muốn tìm bắt đầu bằng: ");
        String s = new String(sc.next());
        sc.nextLine();
        ArrayList<String> temp = Dict.searcherWord(s);
        for(int i=0; i < temp.size(); i++) {
            System.out.print(temp.get(i));
            if (i+1 < temp.size()) System.out.print(", ");
        }
    }
}