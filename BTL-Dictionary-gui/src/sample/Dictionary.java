package sample;

import java.util.ArrayList;

public class Dictionary {
    private ArrayList<Word> Word_Storage = new ArrayList<>();
    private ArrayList<String> Word_Pronun = new ArrayList<>();

    public void addWord(Word newWord) {
        Word_Storage.add(newWord);
    }
    public int getWord_count() {
        return Word_Storage.size();
    }
    public Word getWord(int i) {
        return Word_Storage.get(i);
    }
    public String word_explainLookup (String word_target) {
        for (int i=0; i < Word_Storage.size(); i++) {
            if (Word_Storage.get(i).getWord_target().equals(word_target)) return Word_Storage.get(i).getWord_explain();
        }
        return null;
    }
    public void changeWordAndPronoun(Word word_new, String pronoun, int index) {
        if (index > -1) {
            Word_Storage.set(index, word_new);
            Word_Pronun.set(index, pronoun);
        }

    }
    public void deleteWordAndPronoun(Word delete_word) {
        for(int i=0; i < Word_Storage.size() ; i++) {
            if (Word_Storage.get(i).equals(delete_word)) {
                Word_Storage.remove(i);
                Word_Pronun.remove(i);
            }
        }
    }
    public ArrayList<String> searcherWord(String s) {
        ArrayList<String> temp = new ArrayList<>();
        for(int i=0; i < Word_Storage.size(); i++) {
            if (Word_Storage.get(i).getWord_target().startsWith(s)) {
                temp.add(Word_Storage.get(i).getWord_target());
            }
        }
        return temp;
    }
    public void addPronun(String pronun) {
        Word_Pronun.add(pronun);
    }
    public String getPronun(String word_target) {
        for(int i=0; i < Word_Storage.size(); i++) {
            if (Word_Storage.get(i).getWord_target().equals(word_target)) return Word_Pronun.get(i);
        }
        return "";
    }
    public ArrayList<String> getAllWord_Target() {
        ArrayList<String> temp = new ArrayList<>();
        for(int i=0; i < Word_Storage.size(); i++) {
            temp.add(Word_Storage.get(i).getWord_target());
        }
        return temp;
    }
    public ArrayList<Word> getAllWord() {
        ArrayList<Word> temp = new ArrayList<>();
        for(int i=0; i < Word_Storage.size(); i++) {
            temp.add(Word_Storage.get(i));
        }
        return temp;
    }
    public int getWordId(String word_target) {
        for(int i=0; i < Word_Storage.size(); i++) {
            if (Word_Storage.get(i).getWord_target().equals(word_target)) return i;
        }
        return -1;
    }
}
