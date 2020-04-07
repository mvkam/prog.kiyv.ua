package hw12.hw1.hw3;

import java.io.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Save {
}

public class HW3{
    @Save
    private int x;
    private char y;
    @Save
    private byte z;
    private double c;
    @Save
    private long v;
    private float b;
    @Save
    private boolean n;
    private String m;

    public HW3(int x, char y, byte z, double c, long v, float b, boolean n, String m) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.c = c;
        this.v = v;
        this.b = b;
        this.n = n;
        this.m = m;
    }

    public static void main(String[] args) throws IllegalAccessException, IOException, ClassNotFoundException, NoSuchFieldException, InstantiationException {
        HW3 hw = new HW3(7, 'q', (byte) 1, 789654123, 987456321, 3.4F, true, "dfghj");
        String path = "/home/maxvkamkin/ttest/serial1.txt";

        saveObj(hw, path);

        HW3 hw2 = reconstructionClass(path);

        System.out.println(hw2.x);
        System.out.println(hw2.y);
        System.out.println(hw2.z);
        System.out.println(hw2.c);
        System.out.println(hw2.v);
        System.out.println(hw2.b);
        System.out.println(hw2.n);
        System.out.println(hw2.m);
    }
    private static void saveObj(Object obj, String path) throws IOException, IllegalAccessException {
        Class clazz = obj.getClass();
        ArrayList arrayList = new ArrayList();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field: fields) {
            if (field.isAnnotationPresent(Save.class))
                arrayList.add(field.get(obj));
        }
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
        oos.writeObject(arrayList);
        oos.close();
    }

    private static HW3 reconstructionClass(String path) throws IOException, ClassNotFoundException, IllegalAccessException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
        ArrayList arrayList = (ArrayList) ois.readObject();
        ois.close();
        HW3 hw3 = new HW3(0, (char) 0, (byte) 0, 0, 0, 0F, false, null);
        int index = 0;
        Field[] fields = hw3.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].isAnnotationPresent(Save.class)) {
                fields[i].setAccessible(true);
                fields[i].set(hw3, arrayList.get(index));
                index++;
            }
        }
        return hw3;
    }
}

/* Output:
7

1
0.0
987456321
0.0
true
null
 */
