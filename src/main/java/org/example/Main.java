package org.example;

import org.example.abstracts.Cash;
import org.example.abstracts.CashManager;
import org.example.threads.ReadCash;
import org.example.threads.WriteCash;
import org.example.impl.ConcurrentCash;
import org.example.impl.ConcurrentCashManager;

public class Main {
    static int cashSize = 2;
    static int countReaders = 1;
    static int countWriters = 10;
    static String terminateCommand = "kill";


    public static void main(String[] args) {

        Cash cash = new ConcurrentCash(cashSize);

        CashManager cashManager = new ConcurrentCashManager(
                new ReadCash(cash),
                new WriteCash(cash),
                countReaders,
                countWriters);

        cashManager.readCash();

        cashManager.writeCash();

        Thread.getAllStackTraces().keySet().forEach(x-> System.out.println(x.getName()));

        cashManager.terminateAll(terminateCommand);

    }
}