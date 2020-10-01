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
    public void dictionaryAdvanced() {
        DictionaryUtility.insertFromFile();
        DictionaryUtility.printAllWords();
        DictionaryUtility.dictionaryLookup();
    }
    public void dictionaryChangeWord() {
        DictionaryUtility.changeWord();
    }
    public void dictionaryDeleteWord() {
        DictionaryUtility.deleteWord();
    }
    public void dictionarySearcher() {
        DictionaryUtility.Searcher();
    }
}
