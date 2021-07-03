cmake_minimum_required(VERSION 3.4.1)

# 自己写的代码
add_library(
        jni-function
        SHARED
        jni-function.cpp
)

if(NOT CMAKE_BUILD_TYPE)
    set(CMAKE_BUILD_TYPE Debug)
endif()


# 优化参数，-O0/-Os optimize ； -s strip
#set(CMAKE_C_FLAGS_DEBUG "${CMAKE_C_FLAGS_DEBUG} -g")
#set(CMAKE_CXX_FLAGS_DEBUG "${CMAKE_CXX_FLAGS_DEBUG} -ffunction-sections -fdata-sections")
#set(LOCAL_LDFLAGS "${LOCAL_LDFLAGS} -Wl,--gc-sections")
#set(CMAKE_CXX_FLAGS_DEBUG "${CMAKE_CXX_FLAGS_DEBUG}")
#set(CMAKE_CXX_FLAGS_RELEASE "${CMAKE_CXX_FLAGS_RELEASE} -Os -s -fvisibility=hidden")
#set(CMAKE_CXX_FLAGS_DEBUG "${CMAKE_CXX_FLAGS_DEBUG} -Os -s -fvisibility=hidden")
#
# 引入某个已打包好的
#add_library( imported-lib
#        SHARED
#        IMPORTED )
#
#set_target_properties( # Specifies the target library.
#        imported-lib
#
#        # Specifies the parameter you want to define.
#        PROPERTIES IMPORTED_LOCATION
#
#        # Provides the path to the library you want to import.
#        imported-lib/src/${ANDROID_ABI}/libimported-lib.so )
#
#include_directories( imported-lib/include/ )

# 使用NDK 库，源码形式，需要打包进apk（自动建了个app的module...
# ANDROID_NDK 是AS 定义的
#add_library(
#        app-glue
#        STATIC
#        ${ANDROID_NDK}/sources/android/native_app_glue/android_native_app_glue.c )

# 使用NDK 库，是平台通用的，不需要打包进apk
# 有点像重命名？ 这样在target 中使用时用 ${log-lib}
find_library( # Defines the name of the path variable that stores the
        # location of the NDK library.
        log-lib

        # Specifies the name of the NDK library that
        # CMake needs to locate.
        log)



# Include libraries needed for hello-jni lib
target_link_libraries(
        jni-function
#        imported-lib
#        app-glue
        ${log-lib} # 或者log
        android
)

#add_executable()

# 设置CMakeList嵌套，没测试
# 估计就是 lib_src_DIR 是子项目源码目录，lib_build_DIR 是子项目输出结构目录
# 然后设置lib_gmath 通过子项目

## Sets lib_src_DIR to the path of the target CMake project.
#set( lib_src_DIR ../gmath )
#
## Sets lib_build_DIR to the path of the desired output directory.
#set( lib_build_DIR ../gmath/outputs )
#file(MAKE_DIRECTORY ${lib_build_DIR})
#
## Adds the CMakeLists.txt file located in the specified directory
## as a build dependency.
#add_subdirectory( # Specifies the directory of the CMakeLists.txt file.
#        ${lib_src_DIR}
#
#        # Specifies the directory for the build outputs.
#        ${lib_build_DIR} )
#
## Adds the output of the additional CMake build as a prebuilt static
## library and names it lib_gmath.
#add_library( lib_gmath STATIC IMPORTED )
#set_target_properties( lib_gmath PROPERTIES IMPORTED_LOCATION
#        ${lib_build_DIR}/${ANDROID_ABI}/lib_gmath.a )
#include_directories( ${lib_src_DIR}/include )
#
## Links the top-level CMake build output against lib_gmath.
#target_link_libraries( native-lib ... lib_gmath )