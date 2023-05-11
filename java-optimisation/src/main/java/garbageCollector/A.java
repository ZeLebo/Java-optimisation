package garbageCollector;

public class A {
    public int[] a = new int[10000];
    // 8 bytes - link to array
    // 8 bytes - link to class
    // 40000 - bytes for array
}
