package sample;

public class Word {
    private String word_target;
    private String word_explain;

    public Word() {}
    public Word(String word_target, String word_explain) {
        this.word_target = word_target;
        this.word_explain = word_explain;
    }
    public Word(Word word) {
        word_explain = word.getWord_explain();
        word_target = word.getWord_target();
    }
    public void setWord_target(String word_target) {
        this.word_target = word_target;
    }
    public String getWord_target() {
        return word_target;
    }
    public void setWord_explain(String word_explain) {
        this.word_explain = word_explain;
    }
    public String getWord_explain() {
        return word_explain;
    }
    public boolean equals(Word word) {
        return word_target.equals(word.word_target) && word_explain.equals(word.word_explain);
    }
}
