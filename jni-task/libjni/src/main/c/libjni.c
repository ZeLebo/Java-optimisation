#include <jni.h>
#include <stdlib.h>

#define N 4294967295

typedef struct JniStruct {
  char* strField;
} JniStruct;

// consume all memory
JNIEXPORT void JNICALL Java_io_github_sartakov1_JniMethods_allocate(JNIEnv *env, jobject this) {
  long long* arr = (long long*) malloc(N * sizeof(long long));
  for (int i = 0; i < N; i++) {
    arr[i] = i;
  }
}

// method for division
JNIEXPORT jint JNICALL Java_io_github_sartakov1_JniMethods_divide(JNIEnv *env, jobject this, jint a, jint b) {
//    if (b == 0) {
//        jclass Exception = *env->FindClass("java/land/Exception");
//        *env->ThrowNew(Exception, "can't divide by zero");
//    }
  return a / b;
}

JNIEXPORT jsize JNICALL Java_io_github_sartakov1_JniMethods_stringLength(JNIEnv *env, jobject this, jstring string) {
  return (*env)->GetStringLength(env, string);
}

JNIEXPORT jstring JNICALL Java_io_github_sartakov1_JniMethods_invokeMethod(JNIEnv *env, jobject this, jobject jniObject, jstring methodName) {
  jclass jniObjectClass = (*env)->GetObjectClass(env, jniObject);
  const char* methodNameCharPointer = (*env)->GetStringUTFChars(env, methodName, NULL);
  jmethodID methodId = (*env)->GetMethodID(env, jniObjectClass, methodNameCharPointer, "()Ljava/lang/String;");
  (*env)->ReleaseStringUTFChars(env, methodName, methodNameCharPointer);
  return (jstring) ((*env)->CallObjectMethod(env, jniObject, methodId));
}

JNIEXPORT void JNICALL Java_io_github_sartakov1_JniMethods_setField(JNIEnv *env, jobject this, jobject jniObject, jstring fieldName, jstring newValue) {
  jclass jniObjectClass = (*env)->GetObjectClass(env, jniObject);
  const char* fieldNameCharPointer = (*env)->GetStringUTFChars(env, fieldName, NULL);
  jfieldID stringField = (*env)->GetFieldID(env, jniObjectClass, fieldNameCharPointer, "Ljava/lang/String;");
  (*env)->ReleaseStringUTFChars(env, fieldName, fieldNameCharPointer);
  (*env)->SetObjectField(env, jniObject, stringField, newValue);
}

JNIEXPORT jlong JNICALL Java_io_github_sartakov1_JniMethods_getPtr(JNIEnv *env, jobject this) {
  JniStruct* jniStruct = (JniStruct*) malloc(sizeof(JniStruct));
  jniStruct->strField = (char*) malloc(128 * sizeof(char));
  jniStruct->strField = "Hello from C!";
  return (jlong) jniStruct;
}

JNIEXPORT jstring JNICALL Java_io_github_sartakov1_JniMethods_getVal(JNIEnv *env, jobject this, jlong ptr) {
  return (*env)->NewStringUTF(env, ((JniStruct*) ptr)->strField);
}

JNIEXPORT void JNICALL Java_io_github_sartakov1_JniMethods_destroyPtr(JNIEnv *env, jobject this, jlong ptr) {
  free(((JniStruct*) ptr)->strField);
  free((JniStruct*) ptr);
}
