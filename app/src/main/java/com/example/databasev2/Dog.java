package com.example.databasev2;

import io.realm.RealmObject;

public class Dog extends RealmObject {

    public String name;
    public int age;
    public String color;

    public Dog() {
    }

    public Dog(String name, int age, String color) {
        this.name=name;
        this.age=age;
        this.color=color;


    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", color='" + color + '\'' +
                '}';
    }
}
