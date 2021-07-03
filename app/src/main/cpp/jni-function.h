//
// Created by Administrator on 2021/7/1.
//

#ifndef SELFDICWITHJETPACK_JNI_FUNCTION_H
#define SELFDICWITHJETPACK_JNI_FUNCTION_H

#include <jni.h>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_selfdicwithjetpack_ndk_NdkMediator_getNativeString(JNIEnv *env, jobject thiz);

#endif //SELFDICWITHJETPACK_JNI_FUNCTION_H
extern "C"
JNIEXPORT void JNICALL
Java_com_example_selfdicwithjetpack_ndk_NdkMediator_createNativeCrash(JNIEnv *env, jobject thiz);