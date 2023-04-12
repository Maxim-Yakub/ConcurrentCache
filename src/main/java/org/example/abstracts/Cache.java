package org.example.abstracts;


public interface Cache {

    String addValue(String key, String value, long price);

    String deleteValue(String key);

    String readValues();

    int getSize();
}