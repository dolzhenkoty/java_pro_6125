package org.javapro.oop.annotations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestRunner {
    public static void runTests(Class c) throws InvocationTargetException, IllegalAccessException {

        boolean bIsBeforeSuite = false;
        boolean bIsAfterSuite = false;

        Method beforeSuite = null;
        Method afterSuite = null;

        HashMap<Integer, List<Method>> methodsToInvoke = new HashMap<Integer, List<Method>>();

        Method[] cMethods = c.getDeclaredMethods();

        for (Method m : cMethods) {
            System.out.println(m.getName());
            if (m.isAnnotationPresent(BeforeSuite.class)){
                System.out.println("Method " + m.getName() + " is annotated by @BeforeSuite");
                if (bIsBeforeSuite){
                    throw new RuntimeException("More than one method annotated by @BeforeSuite");
                }
                beforeSuite = m;
                bIsBeforeSuite = true;
            }

            // get test methods
            if (m.isAnnotationPresent(Test.class)) {
                int mPriority = m.getAnnotation(Test.class).priority();
                List<Method> cList = new ArrayList<Method>();

                System.out.println("Method " + m.getName() + " must be invoked. It's priority is " + mPriority);

                if (methodsToInvoke.containsKey(mPriority)) {
                    cList = methodsToInvoke.get(mPriority);
                }
                cList.add(m);
                methodsToInvoke.put(mPriority, cList);
            }

            if (m.isAnnotationPresent(AfterSuite.class)){
                System.out.println("Method " + m.getName() + " is annotated by @AfterSuite");
                if (bIsAfterSuite){
                    throw new RuntimeException("More than one method annotated by @AfterSuite");
                }
                afterSuite = m;
                bIsAfterSuite = true;
            }

        }

        // run BeforeSuite
        if (beforeSuite != null) {
            System.out.println("Invoke BeforeSuite method: " + beforeSuite.getName());
            beforeSuite.setAccessible(true);
            beforeSuite.invoke(null, null);
        }

        // run tests
        List<Integer> testListSorted = new ArrayList<Integer>(methodsToInvoke.keySet());
        Collections.sort(testListSorted);
        Collections.reverse(testListSorted);
        for(Integer p : testListSorted) {
            List<Method> toInvoke = methodsToInvoke.get(p);
            for (Method mInvoke : toInvoke) {
                System.out.println("Invoking " + mInvoke.getName() + " priority: " + mInvoke.getAnnotation(Test.class).priority());
                c cInst = new c("Just a string", 0);
                mInvoke.setAccessible(true);
                mInvoke.invoke(cInst);
            }
        }

        // run AfterSuite
        if (afterSuite != null)  {
            System.out.println("Invoke AfterSuite method: " + afterSuite.getName());
            afterSuite.setAccessible(true);
            afterSuite.invoke(null, null);
        }

    }
}
