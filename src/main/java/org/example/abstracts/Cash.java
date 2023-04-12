package org.example.abstracts;


public interface Cash {

    String addValue(String key, String value, long price);

    String deleteValue(String key);

    String readValues();

    int getSize();
}