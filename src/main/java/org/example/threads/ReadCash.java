package org.example.threads;

import org.apache.log4j.Logger;
import org.example.abstracts.Cash;

public class ReadCash implements Runnable {

    private final Cash cash;

    private final Logger log;

    public ReadCash(Cash cash) {

        this.cash = cash;

        log = Logger.getLogger(ReadCash.class);
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {

                log.info("Данные кэша\n" + cash.readValues());

                Thread.sleep(1000);

            }

        } catch (InterruptedException e) {

            log.info("Thread " + Thread.currentThread().getName() + " was finished");

        }
    }

}

