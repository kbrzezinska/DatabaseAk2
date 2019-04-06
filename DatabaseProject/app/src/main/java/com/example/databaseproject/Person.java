package com.example.databaseproject;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

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
}
