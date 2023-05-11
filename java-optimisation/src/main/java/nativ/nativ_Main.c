#include <jni.h>        // JNI header provided by JDK
#include <stdio.h>      // C Standard IO Header
#include "nativ_Main.h"   // Generated

// Implementation of the native method sayHello()
JNIEXPORT void JNICALL Java_nativ_Main_sayHello(JNIEnv *env, jobject thisObj) {
   printf("Hello World!\n");
   return;
}