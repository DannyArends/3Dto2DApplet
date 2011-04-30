cmake_minimum_required(VERSION 2.6)
SET(EXECNAME prog)

INCLUDE_DIRECTORIES( "../src/cpp" "C:/Program Files/Java/jdk1.6.0_14/include/win32" "C:/Program Files/Java/jdk1.6.0_14/include")
LINK_DIRECTORIES( "C:/Program Files/Java/jdk1.6.0_14/lib")
ADD_DEFINITIONS(-Wall)

add_executable(${EXECNAME} 
  ../src/cpp/main.cpp
  ../src/cpp/C/basic.c
  ../src/cpp/C/search.c
  ../src/cpp/C/sort.c
  ../src/cpp/includes.cpp
  ../src/cpp/JNI/Database.cpp
  ../src/cpp/JNI/CommandExecutor.cpp
  ../src/cpp/JNI/WebServer.cpp
)

TARGET_LINK_LIBRARIES(${EXECNAME} -ljvm)

#ENABLE_TESTING()
SET(EXECUTABLE "${EXECNAME}.exe")
ADD_TEST(Startup ${EXECUTABLE})
