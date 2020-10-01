package com.company;
import java.util.*;

public class Dictionary {
    private Word[] Word_Storage = new Word[300];
    private int Word_count = 0;

    public void addWord(Word newWord) {
        Word_Storage[Word_count] = newWord;
        Word_count++;
    }
    public int getWord_count() {
        return Word_count;
    }
    public Word getWord(int i) {
        return Word_Storage[i];
    }
    public String word_explainLookup (String word_target) {
        for(int i=0; i < Word_count; i++) {
            if (Word_Storage[i].getWord_target().equals(word_target)) return Word_Storage[i].getWord_explain();
        }
        return null;
    }
    public void changeWord(Word word_old, Word word_new) {
        for(int i=0; i < Word_count ; i++) {
            if (Word_Storage[i].equals(word_old)) {
                Word_Storage[i].setWord_target(word_new.getWord_target());
                Word_Storage[i].setWord_explain(word_new.getWord_explain());
                break;
            }
        }
    }
    public void deleteWord(Word delete_word) {
        for(int i=0; i < Word_count ; i++) {
            if (Word_Storage[i].equals(delete_word)) {
                Word_count--;
                    for(int j=i; j < Word_count; j++) {
                        Word_Storage[j] = Word_Storage[j+1];
                    }
                break;
            }
        }
    }
    public ArrayList<String> searcherWord(String s) {
        ArrayList<String> temp = new ArrayList<>();
        for(int i=0; i < Word_count; i++) {
            if (Word_Storage[i].getWord_target().startsWith(s)) {
                temp.add(Word_Storage[i].getWord_target());
            }
        }
        return temp;
    }
}
