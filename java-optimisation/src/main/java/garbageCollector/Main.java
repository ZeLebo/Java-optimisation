package garbageCollector;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.setOut(new PrintStream(new FileOutputStream("src/main/java/garbageCollector/log_file.csv")));
        System.out.println("iteration,total,free,max,total-free,max-total+free,tmp");

        var data = new ArrayList<A>(20000);
        var i = -1;
        var old = 0.0;
        while(true) {
            i++;
            var A = new A();
            data.add(A);
            var total = Runtime.getRuntime().totalMemory();
            var free = Runtime.getRuntime().freeMemory();
            var max = Runtime.getRuntime().maxMemory();
            var delta = max-total+free;
            System.out.print(i);
            System.out.print(",");
            System.out.print(total);
            System.out.print(",");
            System.out.print(free);
            System.out.print(",");
            System.out.print(max);
            System.out.print(",");
            System.out.print(total-free);
            System.out.print(",");
            System.out.print(delta);
            System.out.print(",");
            System.out.println(old - delta);

            old = delta;
        }
    }
}
