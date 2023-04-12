package org.example.abstracts;

public interface CacheManager {
    void readCash();

    void writeCash();

    void terminateAll(String command);
}
