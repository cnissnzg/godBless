cmake_minimum_required(VERSION 3.19)
project(judger)

set(CMAKE_CXX_STANDARD 14)

add_executable(judger main.cpp properties.h problem.cpp problem.h)