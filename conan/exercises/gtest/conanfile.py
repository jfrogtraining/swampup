from conans.model.conan_file import ConanFile
from conans import CMake
import os


class DefaultNameConan(ConanFile):
    name = "DefaultName"
    version = "0.1"
    settings = "os", "compiler", "arch", "build_type"
    generators = "cmake"

    def configure(self):
        if self.env.get("TESTS_ENABLED", False):
            self.requires.add("gtest/1.8.0@lasote/stable")
            self.options["gtest"].shared = False
        
    def build(self):
        cmake = CMake(self.settings)
        def_tests = "-DTESTS_ENABLED=1" if os.getenv("TESTS_ENABLED", False) else ""
        self.run('cmake %s %s %s' % (self.conanfile_directory, cmake.command_line, def_tests))
        self.run("cmake --build . %s" % cmake.build_config)
