PROJECT(MyHello)
cmake_minimum_required(VERSION 3.0)

include(${CMAKE_BINARY_DIR}/conanbuildinfo.cmake)
CONAN_BASIC_SETUP()

ADD_LIBRARY(hellolib hellolib.cpp)

IF(TESTS_ENABLED)
    add_executable( runUnitTests test.cpp )
    target_link_libraries(runUnitTests hellolib ${CONAN_LIBS})
    add_test(runUnitTests runUnitTests)
ENDIF()
