/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class io_github_sartakov1_JniMethods */

#ifndef _Included_io_github_sartakov1_JniMethods
#define _Included_io_github_sartakov1_JniMethods
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     io_github_sartakov1_JniMethods
 * Method:    allocate
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_io_github_sartakov1_JniMethods_allocate
  (JNIEnv *, jobject);

/*
 * Class:     io_github_sartakov1_JniMethods
 * Method:    divide
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_io_github_sartakov1_JniMethods_divide
  (JNIEnv *, jobject, jint, jint);

/*
 * Class:     io_github_sartakov1_JniMethods
 * Method:    stringLength
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_io_github_sartakov1_JniMethods_stringLength
  (JNIEnv *, jobject, jstring);

/*
 * Class:     io_github_sartakov1_JniMethods
 * Method:    invokeMethod
 * Signature: (Lio/github/sartakov1/JniObject;Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_io_github_sartakov1_JniMethods_invokeMethod
  (JNIEnv *, jobject, jobject, jstring);

/*
 * Class:     io_github_sartakov1_JniMethods
 * Method:    setField
 * Signature: (Lio/github/sartakov1/JniObject;Ljava/lang/String;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_io_github_sartakov1_JniMethods_setField
  (JNIEnv *, jobject, jobject, jstring, jstring);

/*
 * Class:     io_github_sartakov1_JniMethods
 * Method:    getPtr
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_io_github_sartakov1_JniMethods_getPtr
  (JNIEnv *, jobject);

/*
 * Class:     io_github_sartakov1_JniMethods
 * Method:    getVal
 * Signature: (J)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_io_github_sartakov1_JniMethods_getVal
  (JNIEnv *, jobject, jlong);

/*
 * Class:     io_github_sartakov1_JniMethods
 * Method:    destroyPtr
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_io_github_sartakov1_JniMethods_destroyPtr
  (JNIEnv *, jobject, jlong);

#ifdef __cplusplus
}
#endif
#endif