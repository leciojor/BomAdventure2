package server.json;

import java.util.HashSet;
import java.util.List;

public class Scripture {

    private String scripture_name;
    private String[] scripture_blancks;
    private List<Object[]> indexes_arrays_remove;
    private String scripture_full;
    private int level;
    private HashSet<String> words_set;

    public Scripture(String scripture_name, String[] scripture_blancks, String scripture_full, List<Object[]> indexes_arrays_remove, int level, HashSet<String> words_set){
        this.scripture_name = scripture_name;
        this.scripture_full = scripture_full;
        this.scripture_blancks = scripture_blancks;
        this.indexes_arrays_remove = indexes_arrays_remove;
        this.level = level;
        this.words_set = words_set;
    }

    List<Object[]> getIndexesArraysRemove(){
        return indexes_arrays_remove;
    }

    int getLevel(){
        return level;
    }

    HashSet<String> getWordsSet(){
        return words_set;
    }

    String getScriptureName(){
        return scripture_name;
    }

    String[] getScriptureBlancks(){
        return scripture_blancks;
    }

    String getScriptureFull(){
        return scripture_full;
    }
}


