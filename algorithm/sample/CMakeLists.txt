cmake_minimum_required(VERSION 3.19)
project(sample)

set(CMAKE_CXX_STANDARD 11)

add_executable(embed main.cpp)
add_executable(ext ext.cpp)