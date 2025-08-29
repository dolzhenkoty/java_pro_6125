package org.javapro.oop.annotations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class TestRunner {
    public static void runTests(Class TC) throws InvocationTargetException, IllegalAccessException, VerifyError {

        Method beforeSuite = null;
        Method afterSuite = null;

        HashMap<Integer, List<Method>> methodsToInvoke = new HashMap<>();

        Method[] cMethods = TC.getDeclaredMethods();

        for (Method m : cMethods) {
            System.out.println(m.getName());
            if (m.isAnnotationPresent(BeforeSuite.class)){
                System.out.println("Method " + m.getName() + " is annotated by @BeforeSuite");
                if (!Modifier.isStatic(m.getModifiers())) {
                    throw new VerifyError("Method " + m.getName() + " is annotated by @BeforeSuite, but not static!");
                }
                if (beforeSuite != null){
                    throw new RuntimeException("More than one method annotated by @BeforeSuite");
                }
                beforeSuite = m;
            }

            // get test methods
            if (m.isAnnotationPresent(Test.class)) {
                int mPriority = m.getAnnotation(Test.class).priority();
                System.out.println("Method " + m.getName() + " must be invoked. It's priority is " + mPriority);

                if (mPriority < 1 || mPriority > 10) {
                    throw new VerifyError("Illegal priority for method " + m.getName() + " = " + mPriority + ". Should be from 1 no 10.");
                }

                List<Method> cList = new ArrayList<>();
                if (methodsToInvoke.containsKey(mPriority)) {
                    cList = methodsToInvoke.get(mPriority);
                }
                cList.add(m);
                methodsToInvoke.put(mPriority, cList);
            }

            if (m.isAnnotationPresent(AfterSuite.class)){
                System.out.println("Method " + m.getName() + " is annotated by @AfterSuite");
                if (!Modifier.isStatic(m.getModifiers())) {
                    throw new VerifyError("Method " + m.getName() + " is annotated by @AfterSuite, but not static!");
                }
                if (afterSuite != null){
                    throw new RuntimeException("More than one method annotated by @AfterSuite");
                }
                afterSuite = m;
            }
        }

        // run BeforeSuite
        if (beforeSuite != null) {
            System.out.println("Invoke BeforeSuite method: " + beforeSuite.getName());
            beforeSuite.setAccessible(true);
            beforeSuite.invoke(null, (Object[]) null);
        }

        // run tests
        List<Integer> testListSorted = new ArrayList<>(methodsToInvoke.keySet());
        Collections.sort(testListSorted);
        Collections.reverse(testListSorted);
        for(Integer p : testListSorted) {
            List<Method> toInvoke = methodsToInvoke.get(p);
            for (Method mInvoke : toInvoke) {
                System.out.println("Invoking " + mInvoke.getName() + " priority: " + mInvoke.getAnnotation(Test.class).priority());
                TestClass TestClassInst = new TestClass("Just a string", 0);
                mInvoke.setAccessible(true);
                mInvoke.invoke(TestClassInst);
            }
        }

        // run AfterSuite
        if (afterSuite != null)  {
            System.out.println("Invoke AfterSuite method: " + afterSuite.getName());
            afterSuite.setAccessible(true);
            afterSuite.invoke(null, (Object[]) null);
        }

    }
}
