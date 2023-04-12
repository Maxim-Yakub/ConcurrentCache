package org.example.impl;

import org.example.abstracts.CashManager;
import org.example.threads.ReadCash;
import org.example.threads.WriteCash;

import java.util.Scanner;
import java.util.stream.IntStream;

public class ConcurrentCashManager implements CashManager {
    private final ReadCash reader;

    private final WriteCash writer;

    private final int countReaders;

    private final int countWriters;

    public ConcurrentCashManager(ReadCash reader, WriteCash writer, int countReaders, int countWriters) {
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
