cmake_minimum_required(VERSION 2.8)
project(judger)

set(CMAKE_CXX_STANDARD 14)
aux_source_directory(./code DIR_SRCS)
add_definitions(-std=c++11)
add_executable(judger compileMain.cpp ${DIR_SRCS} )