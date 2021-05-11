cmake_minimum_required(VERSION 3.19)
project(judger)

set(CMAKE_CXX_STANDARD 14)
aux_source_directory(./code DIR_SRCS)
add_executable(judger compileMain.cpp ${DIR_SRCS} )