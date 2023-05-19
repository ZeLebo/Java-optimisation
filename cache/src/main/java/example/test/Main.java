package example.test;

import java.lang.ref.PhantomReference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class Main {
    public static void main(String[] args) {
        System.out.println("Phantom Test");
        PhantomTest<String, String> test = new PhantomTest<>();
        test.put( "test");
        PhantomReference<String> obj = test.get("test");
        if (obj == null) {
            System.out.println("null");
        } else {
            System.out.println(obj.get());
        }

        System.out.println("Soft Test");
        SoftTest<String, String> test1 = new SoftTest<>();
        test1.put("test");
        SoftReference<String> obj1 = test1.get("test");
        if (obj1 == null) {
            System.out.println("null");
        } else {
            System.out.println(obj1.get());
        }

        System.out.println("Weak Test");
        WeakTest<String, String> test2 = new WeakTest<>();
        test2.put("test");
        WeakReference<String> obj2 = test2.get("test");
        if (obj2 == null) {
            System.out.println("null");
        } else {
            System.out.println(obj2.get());
        }
    }
}
