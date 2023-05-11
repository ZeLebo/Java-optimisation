package io.github.sartakov1;

@SuppressWarnings("SpellCheckingInspection")
public class JniMethods {
  public native void allocate();

  public native int divide(int a, int b);

  public native int stringLength(String string);

  public native String invokeMethod(JniObject jniObject, String methodName);

  public native void setField(JniObject jniObject, String fieldName, String newValue);

  public native long getPtr();

  public native String getVal(long ptr);

  public native void destroyPtr(long ptr);

  static {
    System.loadLibrary("libjni");
  }
}
