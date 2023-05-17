package io.github.sartakov1;

import java.util.ArrayList;
import java.util.*;

public class Main {
  public static void main(String[] args) {
    JniMethods jniMethods = new JniMethods();
    // allocate all memory
//      try {
////        jniMethods.allocate();
//        for (int i = 0; i < 100000000; i++) {
//          byte []bytes = new byte[1000000];
//
//        }
//
//      } catch (Exception e) {
//          System.out.println("a");
//      }
//      try {
//          System.out.println(jniMethods.divide(64, 0));
//      } catch (Throwable exc) {
//          System.out.println(exc.toString());
//      }
//     System.out.println(jniMethods.stringLength("Hello, world!"));

     // change the field of java
//    JniObject jniObject = new JniObject("Hello from Java!");
//    System.out.println(jniMethods.invokeMethod(jniObject, "getStringField"));
//    jniMethods.setField(jniObject, "stringField", "Hello from C!");
//    System.out.println(jniMethods.invokeMethod(jniObject, "getStringField"));

    long jniStructPtr = jniMethods.getPtr();
    System.out.println("Struct allocated at 0X" + Long.toHexString(jniStructPtr));
    System.out.println(jniMethods.getVal(jniStructPtr));
//    jniMethods.destroyPtr(jniStructPtr);
//     here we'll get an error, because we no more have this object
//    System.out.println(jniMethods.getVal(jniStructPtr));
  }
}
