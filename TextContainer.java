package hw12.hw2;

import java.io.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TextContainer {
    String text = "qwertyuiop";

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    private @interface SaveTo {
        String path() default "/home/maxvkamkin/text.txt";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    private @interface Saver {}

    @SaveTo (path = "/home/maxvkamkin/ttest/text1.txt")
    static class Container {
        String path;
        String text = new TextContainer().text;

        @Saver
        public void save(String text, String path) throws IOException {
            FileWriter fw = new FileWriter(path);
            fw.write(text);
            fw.close();
        }
    }

    public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Container cont = new Container();
        Method method = cont.getClass().getMethod("save", String.class, String.class);
        if (method.isAnnotationPresent(Saver.class)) {
            method.invoke(cont, cont.getClass().getDeclaredField("text").get(cont), cont.getClass().getAnnotation(SaveTo.class).path());
        }
    }
}
