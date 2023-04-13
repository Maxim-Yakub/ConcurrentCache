package org.example.threads;

import org.apache.log4j.Logger;
import org.example.abstracts.Cache;

public class ReadCache implements Runnable {

    private final Cache cache;

    private final Logger log;

    private final int frequency;

    public ReadCache(Cache cache, int frequency) {

        this.cache = cache;

        this.frequency = frequency;

        log = Logger.getLogger(ReadCache.class);
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {

                log.info("Данные кэша\n" + cache.readValues());

                Thread.sleep(frequency);

            }

        } catch (InterruptedException e) {

            log.info("Thread " + Thread.currentThread().getName() + " was finished");

        }
    }

}

