package org.javapro.collections;

public class Employee {
    private final String name;
    private final int age;
    private final Position position;

    enum Position {ENGINEER, DIRECTOR, MANAGER}

    public Employee(String name, int age, Position position) {
        this.name = name;
        this.age = age;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Position getPosition() {
        return position;
    }

}

