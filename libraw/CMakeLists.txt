# Sets the minimum version of CMake required to build your native library.
# This ensures that a certain set of CMake features is available to
# your build.

cmake_minimum_required(VERSION 3.4.1)

# Specifies a library name, specifies whether the library is STATIC or
# SHARED, and provides relative paths to the source code. You can
# define multiple libraries by adding multiple add_library() commands,
# and CMake builds them for you. When you build your app, Gradle
# automatically packages shared libraries with your APK.

include_directories(
        src/main/ndk/Libraw_Open/jni/src/
        src/main/ndk/Libraw_Open/jni/libraw/)
add_library(
        # Specifies the name of the library.
        libraw
        # Sets the library as a shared library.
        SHARED
        # Provides a relative path to your source file(s).
        src/main/ndk/Libraw_Open/jni/src/src/libraw_cxx.cpp
        src/main/ndk/Libraw_Open/jni/src/src/libraw_c_api.cpp
        src/main/ndk/Libraw_Open/jni/src/src/libraw_datastream.cpp
        src/main/ndk/Libraw_Open/jni/src/internal/dcraw_common.cpp
        src/main/ndk/Libraw_Open/jni/src/internal/dcraw_fileio.cpp
        src/main/ndk/Libraw_Open/jni/src/internal/demosaic_packs.cpp
        src/main/ndk/Libraw_Open/jni/libraw/libraw.c
        src/main/ndk/Libraw_Open/jni/libraw/swab.c)

target_link_libraries(libraw
        log jnigraphics
        z)
