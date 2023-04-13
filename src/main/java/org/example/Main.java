package org.example;

import org.example.abstracts.Cache;
import org.example.abstracts.CacheManager;
import org.example.threads.ReadCache;
import org.example.threads.WriteCache;
import org.example.impl.ConcurrentCache;
import org.example.impl.ConcurrentCacheManager;

public class Main {
    static int cashSize = 5;
    static int countReaders = 10;
    static int countWriters = 1;
    static int readFrequency = 10000;
    static int writeFrequency = 1000;
    static String terminateCommand = "kill";


    public static void main(String[] args) {

        Cache cache = new ConcurrentCache(cashSize);

        CacheManager cacheManager = new ConcurrentCacheManager(
                new ReadCache(cache, readFrequency),
                new WriteCache(cache, writeFrequency),
                countReaders,
                countWriters);

        cacheManager.readCash();

        cacheManager.writeCash();

        cacheManager.terminateAll(terminateCommand);

    }
}