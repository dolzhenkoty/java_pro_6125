package org.javapro.oop.annotations;

public class TestClass {
    private String field_str;
    private int    field_int;

    public TestClass(String s, int i){
        this.field_str = s;
        this.field_int = i;
    }

    @Test()
    public void method1 () {
        System.out.println("I'm method1");
    }

    @BeforeSuite
    public static void method2 () {
        System.out.println("I'm static method2");
    }

    @Test(priority = 1)
    public void method3 () {
        System.out.println("I'm method3");
    }

    @AfterSuite
    public static void method4 () {
        System.out.println("I'm static method4");
    }

    @Test(priority = 2)
    public void method5 () {
        System.out.println("I'm method5");
    }

    @Test(priority = 10)
    public void method6 () {
        System.out.println("I'm method6");
    }

    @Test(priority = 3)
    public void method7() {
        System.out.println("I'm method7");
    }

}
