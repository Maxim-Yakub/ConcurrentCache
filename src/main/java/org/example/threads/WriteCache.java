package org.example.threads;

import org.apache.log4j.Logger;
import org.example.abstracts.Cache;

import java.util.Random;

public class WriteCache implements Runnable {

    private final Cache cache;

    private final Logger log;

    private final int maxCashSize;

    private final int frequency;


    public WriteCache(Cache cache, int frequency) {

        this.cache = cache;

        log = Logger.getLogger(WriteCache.class);

        maxCashSize = cache.getSize() + 1;

        this.frequency = frequency;

    }

    @Override
    public void run() {

        try {
            while (!Thread.currentThread().isInterrupted()) {

                int keyNumber = new Random().ints(1, (maxCashSize)).findFirst().orElse(1);

                int opNumber = new Random().ints(1, (4)).findFirst().orElse(1);

                String key = "Key № " + keyNumber;

                switch (opNumber) {

                    case 1:
                        log.info("Добавление записи\n" + cache.addValue(key, "v1", 1000L));
                        break;

                    case 2:
                        log.info("Добавление записи\n" + cache.addValue(key, "v2", 1111L));
                        break;

                    case 3:
                        log.info("Удаление записи\n" + cache.deleteValue(key));
                        break;

                }

                Thread.sleep(frequency);
            }
        } catch (InterruptedException e) {

            log.info("Thread " + Thread.currentThread().getName() + " was finished");
        }

    }
}
