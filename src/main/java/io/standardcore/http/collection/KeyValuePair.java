package io.standardcore.http.collection;

public class KeyValuePair<TName, TValue> {
    public KeyValuePair(){

    }

    public KeyValuePair(TName name, TValue value) {
        this.name = name;
        this.value = value;
    }

    private TName name;
    private TValue value;

    public TName getName() {
        return name;
    }

    public void setName(TName name) {
        this.name = name;
    }

    public TValue getValue() {
        return value;
    }

    public void setValue(TValue value) {
        this.value = value;
    }
}
