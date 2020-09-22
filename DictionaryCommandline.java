package com.company;

public class DictionaryCommandline {
    private DictionaryManagement DictionaryUtility = new DictionaryManagement();

    public void showAllWords() {
        DictionaryUtility.printAllWords();
    }
    public void dictionaryBasic() {
        DictionaryUtility.insertFromCommandline();
        showAllWords();
    }
}
