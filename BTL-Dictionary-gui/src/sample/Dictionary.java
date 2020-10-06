package sample;

import java.util.ArrayList;

public class Dictionary {
    private ArrayList<Word> Word_Storage = new ArrayList<>();
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
        for(int i=0; i < Word_Storage.size(); i++) {
            if (Word_Storage.get(i).getWord_target().equals(word_target)) return Word_Storage.get(i).getWord_explain();
        }
        return null;
    }
    public void changeWord(Word word_old, Word word_new) {
        for(int i=0; i < Word_Storage.size() ; i++) {
            if (Word_Storage.get(i).equals(word_old)) {
                Word_Storage.get(i).setWord_target(word_new.getWord_target());
                Word_Storage.get(i).setWord_explain(word_new.getWord_explain());
                break;
            }
        }
    }
    public void deleteWord(Word delete_word) {
        for(int i=0; i < Word_Storage.size() ; i++) {
            if (Word_Storage.get(i).equals(delete_word)) {
                Word_Storage.remove(i);
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
}
