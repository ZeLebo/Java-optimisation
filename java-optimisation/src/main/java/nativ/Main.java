package nativ;

public class Main {
    static {
        System.load("/Users/alexandersartakov/Proger/Unsorted/java-optimisation/src/main/java/nativ/libtest.dylib");
    }
    private native void sayHello();

    public static void main(String[] args) {
        new Main().sayHello();
    }
}
