from conans.model.conan_file import ConanFile
from conans import CMake

class DefaultNameConan(ConanFile):
    settings = "os", "compiler", "arch", "build_type"
    generators = "cmake"
    requires = "zlib/1.2.11@lasote/stable"

    def build(self):
        cmake = CMake(self)
        cmake.configure()
        cmake.build()