package org.example.impl;

import org.example.abstracts.CacheManager;
import org.example.threads.ReadCache;
import org.example.threads.WriteCache;

import java.util.Scanner;
import java.util.stream.IntStream;

public class ConcurrentCacheManager implements CacheManager {
    private final ReadCache reader;

    private final WriteCache writer;

    private final int countReaders;

    private final int countWriters;

    public ConcurrentCacheManager(ReadCache reader, WriteCache writer, int countReaders, int countWriters) {
        this.reader = reader;
        this.writer = writer;
        this.countReaders = countReaders;
        this.countWriters = countWriters;
    }

    public void readCash() {

        IntStream.range(0, countReaders).forEach(index -> {

            Thread thread = new Thread(reader);

            thread.setName("Reader_" + index);

            thread.start();
        });
    }

    public void writeCash() {

        IntStream.range(0, countWriters).forEach(index -> {

            Thread thread = new Thread(writer);

            thread.setName("Writer_" + index);

            thread.start();
        });

    }

    public void terminateAll(String command) {

        Scanner scanner = new Scanner(System.in);

        if (scanner.nextLine().equals(command)) {

            Thread.getAllStackTraces().keySet().forEach(Thread::interrupt);
        }
    }
}
