<#--#####################################################################-->
<#--                                                                   ##-->
<#--         START OF THE OUTPUT                                       ##-->
<#--                                                                   ##-->
<#--#####################################################################-->
# \file ${file}
# Copyright:   Danny Arends 2011-${year?c}, all rights reserved
# \date ${date}
# \brief Generated header file for CPP JNI interface
#
# THIS FILE HAS BEEN GENERATED, PLEASE DO NOT EDIT!
#

cmake_minimum_required(VERSION 2.8)

INCLUDE_DIRECTORIES( "." "../../src/cpp" "C:/Program Files/Java/jdk1.6.0_24/include" "C:/Program Files/Java/jdk1.6.0_24/include/win32" )
LINK_DIRECTORIES( "C:/Program Files/Java/jdk1.6.0_24/lib" )
ADD_DEFINITIONS(-Wall)

add_executable(MyApplication 
  ../../generated/cpp/main.cpp
  ../../src/cpp/includes.cpp
  ../../src/cpp/JNI/Database.cpp
  ../../src/cpp/JNI/CommandExecutor.cpp
  ../../src/cpp/JNI/WebServer.cpp
  <#list model.entities as entity>
  ${entity.namespace?replace(".", "/")}${entity.name}.cpp;
  </#list>
)

TARGET_LINK_LIBRARIES(MyApplication -ljvm)

#ENABLE_TESTING()
SET(MyApplication "MyApplication.exe")
ADD_TEST(Startup MyApplication)
