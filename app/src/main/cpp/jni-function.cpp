/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
#include <string.h>
#include <android/log.h>
#include "jni-function.h"
#include "JniClass.h"

/* This is a trivial JNI example where we use a native method
 * to return a new VM String. See the corresponding Java source
 * file located at:
 *
 *   hello-jni/app/src/main/java/com/example/hellojni/HelloJni.java
 */

void doSomething1();

void doSomething2();

extern "C" jstring
Java_com_example_selfdicwithjetpack_ndk_NdkMediator_getNativeString(JNIEnv *env, jobject thiz) {
    JniClass::doStaticCrash();
    JniClass jniClass;
    jniClass.doCrash();
    return env->NewStringUTF("this is native string");
}

extern "C" void
Java_com_example_selfdicwithjetpack_ndk_NdkMediator_createNativeCrash(JNIEnv *env, jobject thiz) {
    doSomething1();
}

void doSomething1() {
    doSomething2();
}

void doSomething2() {
    for (int i = 0; i < 16; i++) {

    }
    __android_log_print(ANDROID_LOG_ERROR, "屠龙宝刀", "doSomething2 before memcpy");
    throw "error";
    __android_log_print(ANDROID_LOG_ERROR, "屠龙宝刀", "doSomething2 after memcpy");
}