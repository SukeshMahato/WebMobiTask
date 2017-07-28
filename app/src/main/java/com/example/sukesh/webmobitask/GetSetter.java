package com.example.sukesh.webmobitask;

/**
 * Created by Sukesh on 7/27/2017.
 */
public class GetSetter {
    String word;

    public GetSetter(String word, int count) {
        this.word = word;
        this.count = count;
    }

    int count;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
