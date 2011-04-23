<#--#####################################################################-->
<#--                                                                   ##-->
<#--         START OF THE OUTPUT                                       ##-->
<#--                                                                   ##-->
<#--#####################################################################-->
/* \file ${file}
 * Copyright:   Danny Arends 2011-${year?c}, all rights reserved
 * \date ${date}
 * \brief Generated header file for CPP JNI interface
 *
 * THIS FILE HAS BEEN GENERATED, PLEASE DO NOT EDIT!
 */

#include <cstdlib>
#include <cstdio>
#include <string>
#include <vector>
#include <jni.h>

<#list model.entities as entity>
#include "${entity.namespace?replace(".", "/")}${entity.name}.h"
</#list>

using namespace std;

char* classpath = (char*)"-Djava.class.path=./bin/;./libs/hsqldb.jar;./libs/freemarker.jar;./libs/servlet-2-3.jar";

JNIEnv* create_vm(JavaVM** jvm) {
  JNIEnv* env;
  JavaVMInitArgs vm_args;
  JavaVMOption options;
  options.optionString = classpath;
  
  vm_args.version = JNI_VERSION_1_6; 						//JDK version. This indicates version 1.6
  vm_args.nOptions = 1;
  vm_args.options = &options;
  vm_args.ignoreUnrecognized = 0;

  int ret = JNI_CreateJavaVM(jvm, (void**)&env, &vm_args);
  if(ret < 0)
    printf("\nUnable to Launch JVM\n");       
  return env;
}

void test_mappings(JNIEnv* env){
    <#list model.entities as entity>
    new ${entity.name}(env);
  	</#list>
}

int main(int argc, char* argv[]){
  JNIEnv* env;
  JavaVM* jvm;
  env = create_vm(&jvm);
  if(!(env == NULL)){
    test_mappings(env);
  }
  jvm->DestroyJavaVM();
}   
