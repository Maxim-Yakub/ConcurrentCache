package org.example.threads;

import org.apache.log4j.Logger;
import org.example.abstracts.Cash;

import java.util.Random;

public class WriteCash implements Runnable {

    private final Cash cash;

    private final Logger log;

    private final int maxCashSize;


    public WriteCash(Cash cash) {

        this.cash = cash;

        log = Logger.getLogger(WriteCash.class);

        maxCashSize = cash.getSize() + 1;

    }

    @Override
    public void run() {

        try {
            while (!Thread.currentThread().isInterrupted()) {

                int keyNumber = new Random().ints(1, (maxCashSize)).findFirst().getAsInt();

                int opNumber = new Random().ints(1, (4)).findFirst().getAsInt();

                String key = "Key № " + keyNumber;

                switch (opNumber) {

                    case 1:
                        log.info("Добавление записи\n" + cash.addValue(key, "v1", 1000L));
                        break;

                    case 2:
                        log.info("Добавление записи\n" + cash.addValue(key, "v2", 1111L));
                        break;

                    case 3:
                        log.info("Удаление записи\n" + cash.deleteValue(key));
                        break;

                }

                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {

            log.info("Thread " + Thread.currentThread().getName() + " was finished");
        }

    }
}
