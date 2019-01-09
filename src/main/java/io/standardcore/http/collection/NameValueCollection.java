package io.standardcore.http.collection;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class NameValueCollection<TName, TValue> implements Iterable<KeyValuePair<TName, TValue>> {
    private final List<KeyValuePair<TName, TValue>> values;
    private final Set<TName> names;

    public NameValueCollection(){
        values = new LinkedList<>();
        names = new LinkedHashSet<>();
    }

    public Set<TName> getNames(){
        return Collections.unmodifiableSet(names);
    }

    public boolean containsName(TName name){
        return names.stream().anyMatch(x->x.equals(normalizeName(name)));
    }

    public boolean contains(KeyValuePair<TName, TValue> item){
        return values.stream().anyMatch(x-> x.equals(item));
    }

    public void add(KeyValuePair<TName, TValue> item){
        if(item == null) throw new IllegalArgumentException("item");
        if(!containsName(item.getName())){
            names.add(normalizeName(item.getName()));
        }
        if(!contains(item)){
            values.add(item);
        }
    }

    public void add(TName name, TValue value){
        this.add(new KeyValuePair<>(name, value));
    }

    public void removeAll(TName name){
        if(!containsName(name)) return;
        names.removeAll(names.stream().filter(x-> x.equals(normalizeName(name))).collect(Collectors.toList()));
        values.removeAll(values.stream().filter(x-> normalizeName(x.getName()).equals(normalizeName(name))).collect(Collectors.toList()));
    }

    public List<TValue> getValues(TName name){
        if(!containsName(name)) return Collections.EMPTY_LIST;
        return values.stream().filter(x-> normalizeName(x.getName()).equals(name)).map(KeyValuePair::getValue).collect(Collectors.toList());
    }

    public TValue firstValueOrDefault(TName name, TValue defaultValue){
        if(!containsName(name)) return defaultValue;
        return values.stream().filter(x-> normalizeName(x.getName()).equals(name)).map(KeyValuePair::getValue).findFirst()
                .orElse(defaultValue);
    }

    @Override
    public Iterator<KeyValuePair<TName, TValue>> iterator() {
        return values.iterator();
    }

    @Override
    public void forEach(Consumer<? super KeyValuePair<TName, TValue>> action) {
        values.forEach(action);
    }

    @Override
    public Spliterator<KeyValuePair<TName, TValue>> spliterator() {
        return values.spliterator();
    }

    private TName normalizeName(TName name){
        if(name instanceof String) {
             return (TName)StringUtils.lowerCase((String) name);
        }
        return name;
    }
}
