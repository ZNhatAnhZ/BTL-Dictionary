package com.company;
import java.util.Scanner;

public class DictionaryManagement {
    private Dictionary Dict = new Dictionary();
    Scanner sc = new Scanner(System.in);

    public void insertFromCommandline() {
        System.out.print("Nhap so luong tu vung muon them vao: ");
        int n = sc.nextInt();
        for(int i=0; i < n; i++) {
            Word temp = new Word();
            System.out.print("Nhap tu vung tieng anh muon them vao: ");
            temp.setWord_target(sc.next());
            sc.nextLine();
            System.out.print("Giai thich nghia cua tu vua them vao: ");
            temp.setWord_explain(sc.nextLine());
            Dict.addWord(temp);
            System.out.print("\n");
        }
    }
    public void printAllWords() {
        int n = Dict.getWord_count();
        if (n != 0) {
            System.out.println("No  | English           | Vietnamese");
            for(int i=0 ;i<n; i++) {
                Word temp = new Word();
                temp = Dict.getWord(i);
                String out = String.format("%-4d| %-18s| %s", i+1, temp.getWord_target(), temp.getWord_explain());
                System.out.println(out);
            }
        }
    }
}
