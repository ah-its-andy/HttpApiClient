package io.standardcore.http.collection;

import java.util.ArrayList;

public class NameValueCollection<TName, TValue> extends ArrayList<KeyValuePair<TName, TValue>> {
    public void add(TName name, TValue value){
        this.add(new KeyValuePair<>(name, value));
    }
}
