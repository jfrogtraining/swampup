#!/bin/bash

curdir=$(pwd)
RED='\033[0;51;30m'
STD='\033[0;0;39m'
APIKEY='AKCp2WWshJKjZjguhB3vD2u3RMwHA7gmxWUohWVhs1FqacHBAzKaiL2pp24NNUEhWHm5Dd4JY'

consumer() {
   echo "performing Excercise 2 (consumer, with CMake)"
   cd consumer
   rm -rf build
   mkdir -p build
   cd build
   conan install ../ --build missing
   cmake ..
   cmake --build .
   cd bin
   ./timer
}


consumer_debug() {
   echo "performing Exercise 3 (consumer, with build_type Debug)"
   cd consumer
   rm -rf build
   mkdir -p build
   cd build
   conan install .. -s build_type=Debug
   cmake ..
   cmake --build .
   conan search
   conan search zlib/1.2.8@lasote/stable
}

consumer_gcc() {
   echo "performing Exercise 4 (consumer, with GCC)"
   cd consumer_gcc
   sed -i 's/cmake/gcc/g' conanfile.txt
   conan install . --build missing
   g++ timer.cpp @conanbuildinfo.gcc -o timer --std=c++11
   ./timer
   sed -i 's/gcc/cmake/g' conanfile.txt
}

create() {
   echo "performing Exercise 5 (Create a Conan Package)"
   cd create
   conan new Hello/1.0@myuser/testing -t
   conan test_package
}

create_sources() {
   echo "performing Exercise 6 (Create Package with sources)"
   cd create_sources
   conan new Hello/1.0@myuser/testing -t --source
   conan test_package
}

upload_artifactory() {
   echo "performing Exercise 7 (Upload packages to artifactory)"
   conan upload Hello/1.0@myuser/testing -r artifactory --all
}


profile_arm_compiler() {
   cd profile_arm
   rm -rf build
   mkdir -p build
   cd build
   conan install .. --profile ../arm_gcc_debug.profile --build missing
   conan build ..
   ls ../bin/example && echo "Example built ok!"
}


package_header_only(){
    cd header_only
    conan new picojson/1.3.0@lasote/testing -i -t
    cp example.cpp test_package

    echo 'from conans import ConanFile, tools
import os

class PicojsonConan(ConanFile):
    name = "picojson"
    version = "1.3.0"
    license = "The 2-Clause BSD License"
    url = "https://github.com/kazuho/picojson"
    # No settings/options are necessary, this is header only

    def source(self):
        self.run("git clone https://github.com/kazuho/picojson.git")

    def package(self):
        self.copy("*.h", "include")' > conanfile.py

    conan test_package
}

gtest() {
    cd gtest
    conan remove hello/1.0@lasote/testing -f
    cd gtest
    cd package
    conan export lasote/testing
    cd ..
    cd consumer
    conan install -e TEST_ENABLED=1 --build missing
    conan remove "gtest*" -f
    conan install
}


gtest_build_require() {
    conan remove hello/1.0@lasote/testing -f
    cd gtest_build_requires
    cd package
    conan export lasote/testing
    cd ..
    cd consumer
    conan install --profile ./test_prfile.txt
    conan remove "gtest*" -f
    conan install

}


read_options(){
    local choice
    cd ${curdir}
    read -p "Enter choice: " choice
    case $choice in
            2) consumer ;;
            3) consumer_debug ;;
            4) consumer_gcc ;;
            5) create ;;
            6) create_sources ;;
            7) upload_artifactory ;;
            8) profile_arm_compiler ;;
            10) gtest ;;
            11) gtest_build_require ;;
            12) package_header_only ;;
            -1) exit 0 ;;
            *) echo -e "${RED}Not valid option! ${STD}" && sleep 2
    esac
}



# function to display menus
show_menus() {
        echo "~~~~~~~~~~~~~~~~~~~~~~~~~~"
        echo " Automation Catch Up Menu "
        echo "~~~~~~~~~~~~~~~~~~~~~~~~~~"
        echo "2. Exercise 2 (Consume with CMake)"
        echo "3. Exercise 3 (Consume with CMake, with different build_type, Debug)"
        echo "4. Exercise 4 (Consume with GCC)"
        echo "5. Exercise 5 (Create a conan package)"
        echo "6. Exercise 6 (Create package with sources)"
        echo "7. Exercise 7 (Upload packages to artifactory)"
        echo "8. Exercise 8 (Create a profile for RPI toolchain)"
        echo "10. Exercise 10 (Use Gtest as a require)"
        echo "11. Exercise 11 (Use Gtest as a build_require)"
        echo "12. Exercise 12 (Create a package for a header only library)"
        echo "-1. Exit"
}

while true
do
        show_menus
        read_options
done
