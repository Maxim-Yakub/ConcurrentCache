package org.example.impl;

import org.example.abstracts.Cash;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentCash implements Cash {

    private final AtomicInteger size;

    private final Map<String, CashValue> cashTable;

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

    public ConcurrentCash(int size) {

        this.size = new AtomicInteger(size);

        cashTable = new ConcurrentHashMap<>();
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
            }
            else {

                cashTable.put(key, cashValue);

                result = String.format("Обновлена запись:\n %s:\n\t value: %s \n\t price: %d \n", key, value, price);
            }
        } else {

            cashTable.put(key, cashValue);



            result = String.format("Добавлена запись:\n %s:\n\t value: %s \n\t price: %d \n", key, value, price);

            size.decrementAndGet();
        }

        return result;
    }

    public synchronized String deleteValue(String key) {

        Optional<CashValue> removedValue = Optional.ofNullable(cashTable.get(key));

        if (removedValue.isPresent()) {

            cashTable.remove(key);

            size.incrementAndGet();

            return String.format("Удалена запись: \n %s:\n\t value: %s \n\t price: %d \n",
                    key,
                    removedValue.get().value,
                    removedValue.get().price);
        }

        return "Значения для удаления не найдено";

    }

    public String readValues() {

        StringBuilder result = new StringBuilder();

        if (cashTable.entrySet().isEmpty()) {
            return "Кэш пуст";
        }

        for (Map.Entry<String, CashValue> pair : cashTable.entrySet()) {

            String temp = String.format("%s:\n\tvalue: %s\n\tprice: %d \n",
                    pair.getKey(),
                    pair.getValue().value,
                    pair.getValue().price);

            result.append(temp);
        }

        return result.toString();
    }

    public int getSize() {

        return size.get();
    }
}
