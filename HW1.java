package hw12.hw1;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HW1 {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    private  @interface Test {
        int a();
        int b();
    }

    @Test(a = 2, b = 5)
    public void test(int a, int b) {
        System.out.println("a + b = " + (a + b));
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = HW1.class.getDeclaredMethods();

        for (Method method: methods) {
            if (method.isAnnotationPresent(Test.class)) {
                Test test = method.getAnnotation(Test.class);
                method.invoke(new HW1(), test.a(), test.b());
            }
        }
    }
}
