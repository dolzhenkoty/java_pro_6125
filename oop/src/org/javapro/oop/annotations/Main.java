package org.javapro.oop.annotations;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Class<TestClass> cClass = TestClass.class;
        TestRunner.runTests(cClass);
    }
}