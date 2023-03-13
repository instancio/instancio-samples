package org.example.domain.impl;

import org.example.domain.Person;

public class PersonImpl implements Person {

    private String name;
    private int age;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getAge() {
        return age;
    }
}
