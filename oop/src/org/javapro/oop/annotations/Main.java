package org.javapro.oop.annotations;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Class<c> cClass = c.class;
        TestRunner.runTests(cClass);
    }
}