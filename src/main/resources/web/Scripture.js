
class Scripture {
    constructor(scripture_name, scripture_blancks, scripture_full, indexes_arrays_remove, level, words_set) {
        this.scripture_name = scripture_name;
        this.scripture_blancks = scripture_blancks;
        this.scripture_full = scripture_full;
        this.indexes_arrays_remove = indexes_arrays_remove;
        this.level = level;
        this.words_set = words_set;
    }

    getWordsSet(){
        return this.words_set;
    }

    getScriptureName() {
        return this.scripture_name;
    }

    getScriptureBlancks() {
        return this.scripture_blancks;
    }

    getScriptureFull() {
        return this.scripture_full;
    }

    getIndexesArraysRemove() {
        return this.indexes_arrays_remove;
    }

    getLevel(){
        return this.level;
    }
}

export default Scripture;
