package task.work;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        MyClass a = new MyClass();

        MyClassComparable changeTmp = new MyClassComparable("aaa", 3);
        a.getStringLength();
        System.out.println("Name of the class is " + changeTmp.name);
        try {
            a.changeFieldValue(changeTmp, "value", 5);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

        System.out.println(changeTmp.value);


        List<MyClassComparable> list = new ArrayList<>();
        list.add(new MyClassComparable("a", 5));
        list.add(new MyClassComparable("b", 2));
        list.add(new MyClassComparable("c", 9));
        list.add(new MyClassComparable("d", 0));
        list.add(new MyClassComparable("f", 3));
        list.add(new MyClassComparable("e", 10));

        // bubble sort on this array
        for (int i = 0; i < list.size(); i++) {
            for (int j = i; j < list.size(); j++) {
                if (list.get(i).value > list.get(j).value) {
                    MyClassComparable tmp = list.get(j);
                    list.set(j, list.get(i));
                    list.set(i, tmp);
                }
            }
        }

        for (MyClassComparable tmp: list) {
            System.out.println(tmp.value + tmp.name);
        }


    }
}