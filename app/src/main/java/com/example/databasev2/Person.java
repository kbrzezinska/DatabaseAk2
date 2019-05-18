package com.example.databasev2;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Person extends RealmObject {

    public String FirstName;
    public String LastName;
    public int age;
    public RealmList<Dog> dogs;
    public int id ;

    public Person() {
    }

    public Person(String firstName, String lastName, int age, int id) {
        FirstName = firstName;
        LastName = lastName;
        this.age = age;
        this.dogs = dogs;
        this.id=id;

    }

    @Override
    public String toString() {
        return "Person{" +
                "FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", age=" + age +
                ", dogs=" + dogs.toString() +
                ", id=" + id +
                '}';
    }
}
