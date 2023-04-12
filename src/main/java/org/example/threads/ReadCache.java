package org.example.threads;

import org.apache.log4j.Logger;
import org.example.abstracts.Cache;

public class ReadCache implements Runnable {

    private final Cache cache;

    private final Logger log;

    public ReadCache(Cache cache) {

        this.cache = cache;

        log = Logger.getLogger(ReadCache.class);
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {

                log.info("Данные кэша\n" + cache.readValues());

                Thread.sleep(1000);

            }

        } catch (InterruptedException e) {

            log.info("Thread " + Thread.currentThread().getName() + " was finished");

        }
    }

}

