package com.bride.client.language.coffee;

import androidx.annotation.NonNull;

import com.bride.client.language.Generator;

import java.util.Iterator;
import java.util.Random;

/**
 * <p>Created by shixin on 2019-05-05.
 */
public class CoffeeGenerator implements Generator<Coffee>, Iterable<Coffee> {

    int size;
    private Class[] values = new Class[]{Latte.class, Mocha.class, Cappuccino.class,
            Americano.class, CaramelMacchiato.class, SingleBreveLatte.class};
    private Random random = new Random(47L);

    public CoffeeGenerator(int size) {
        this.size = size;
    }

    @Override
    public Coffee next() {
        try {
            return (Coffee) values[random.nextInt(values.length)].newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException();
        }
    }

    @NonNull
    @Override
    public Iterator<Coffee> iterator() {
        return new CoffeeIterator();
    }

    class CoffeeIterator implements Iterator<Coffee> {

        int count = size;

        @Override
        public boolean hasNext() {
            return count > 0;
        }

        @Override
        public Coffee next() {
            count--;
            return CoffeeGenerator.this.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        CoffeeGenerator coffeeGenerator = new CoffeeGenerator(10);
        for (Coffee c : coffeeGenerator) {
            System.out.println(c);
        }
    }
}
