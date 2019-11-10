package com.nutcunofficial.nutcapps.Home.Modules;

public class KeyValueModule<T> {
    private T Key;
    private T Value;

    public KeyValueModule(T key, T value) {
        Key = key;
        Value = value;
    }

    public T getKey() {
        return Key;
    }

    public void setKey(T key) {
        Key = key;
    }

    public T getValue() {
        return Value;
    }

    public void setValue(T value) {
        Value = value;
    }
}
