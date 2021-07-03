//
// Created by Administrator on 2021/7/2.
//

#ifndef SELFDICWITHJETPACK_JNICLASS_H
#define SELFDICWITHJETPACK_JNICLASS_H


class JniClass {

public:
    static void doStaticCrash(){
        int *p = 0; //空指针
        *p = 1; //写空指针指向的内存，产生SIGSEGV信号，造成crash
    }

    void doCrash() {
        int *p = 0; //空指针
        *p = 1; //写空指针指向的内存，产生SIGSEGV信号，造成crash
    }

};


#endif //SELFDICWITHJETPACK_JNICLASS_H
