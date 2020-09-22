package com.company;

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
}
