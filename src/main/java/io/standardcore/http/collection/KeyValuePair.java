package io.standardcore.http.collection;

public class KeyValuePair<TName, TValue> {
    public KeyValuePair(TName name, TValue value) {
        if(name == null) throw new IllegalArgumentException("name");
        if(value == null) throw new IllegalArgumentException("value");
        this.name = name;
        this.value = value;
    }

    private TName name;
    private TValue value;

    public TName getName() {
        return name;
    }
    public TValue getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof KeyValuePair)) return false;
        KeyValuePair<TName, TValue> right = (KeyValuePair<TName, TValue>)obj;
        return name.equals(right.getName()) && value.equals(right.getValue());
    }
}
