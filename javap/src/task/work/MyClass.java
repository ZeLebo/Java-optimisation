package java.work;

import java.lang.reflect.Field;
import java.util.Random;

public class MyClass {
    public String genearateString() {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

    public int getStringLength() {
        String str = this.genearateString();
        return str.length();
    }
    public int getStringLength(String str) {
        return str.length();
    }

    public String callMethod(Object obj) {
        return obj.toString();
    }

    public void changeFieldValue(Object obj, String fieldName, Object newValue)
            throws IllegalAccessException, NoSuchFieldException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, newValue);
    }
}
