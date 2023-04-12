package org.example.impl;

import org.example.abstracts.Cache;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentCache implements Cache {

    private final AtomicInteger size;

    private final Map<String, CashValue> cashTable;

    private final String logValue;

    public static class CashValue {
        private final String value;
        private final long price;

        private CashValue(String value, long price) {
            this.value = value;
            this.price = price;
        }

        @Override
        public boolean equals(Object o) {

            if (this == o) {

                return true;
            }

            if (o == null || getClass() != o.getClass()) {

                return false;
            }

            CashValue cashValue = (CashValue) o;

            return price == cashValue.price && value.equals(cashValue.value);
        }

        @Override
        public int hashCode() {

            return Objects.hash(value, price);
        }
    }

    public ConcurrentCache(int size) {

        this.size = new AtomicInteger(size);

        cashTable = new ConcurrentHashMap<>();

        logValue = "\n %s:\n\t value: %s \n\t price: %d \n\n Cache size: " + size + " / ";

    }

    public synchronized String addValue(String key, String value, long price) {

        String result = "Кэщ переполнен";

        if (size.get() < 1) {

            return result;
        }

        CashValue cashValue = new CashValue(value, price);

        Optional<CashValue> addedValue = Optional.ofNullable(cashTable.get(key));

        if (addedValue.isPresent()) {

            if (addedValue.get().equals(cashValue)) {

                result = "Запись есть в кэше";
            } else {

                cashTable.put(key, cashValue);

                result = String.format("Обновлена запись: " + logValue + "%d \n", key, value, price, getSize());
            }
        } else {

            cashTable.put(key, cashValue);

            result = String.format("Добавлена запись: " + logValue + "%d \n", key, value, price, size.decrementAndGet());
        }

        return result;
    }

    public synchronized String deleteValue(String key) {

        Optional<CashValue> optionalValue = Optional.ofNullable(cashTable.get(key));

        if (optionalValue.isPresent()) {

            cashTable.remove(key);

            return String.format("Удалена запись: " + logValue + "%d \n",
                    key,
                    optionalValue.get().value,
                    optionalValue.get().price,
                    size.incrementAndGet());
        }

        return "Значения для удаления не найдено";

    }

    public String readValues() {

        StringBuilder result = new StringBuilder();

        if (cashTable.entrySet().isEmpty()) {
            return "Кэш пуст";
        }

        cashTable.entrySet().forEach(pair -> result.append(

                String.format(logValue + "%d \n",
                        pair.getKey(),
                        pair.getValue().value,
                        pair.getValue().price,
                        getSize())
        ));

        return result.toString();

    }

    public int getSize() {

        return size.get();
    }
}
