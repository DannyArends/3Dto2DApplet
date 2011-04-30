/*
 * main.cpp
 *
 *  Created on: Apr 9, 2011
 *      Author: Danny Arends
 */

#include "includes.h"

#include "C/Sort.h"
#include "C/Search.h"
#include "JNI/Database.h"
#include "JNI/CommandExecutor.h"
#include "JNI/WebServer.h"

using namespace std;

JNIEnv* create_vm(JavaVM** jvm) {
  JNIEnv* env;
  JavaVMInitArgs vm_args;
  JavaVMOption options;
  options.optionString = (char*) "-Djava.class.path=./bin/;./libs/hsqldb.jar;./libs/freemarker.jar;./libs/servlet-2-3.jar"; //Path to the java source code
  vm_args.version = JNI_VERSION_1_6; //JDK version. This indicates version 1.6
  vm_args.nOptions = 1;
  vm_args.options = &options;
  vm_args.ignoreUnrecognized = 0;

  int ret = JNI_CreateJavaVM(jvm, (void**)&env, &vm_args);
  if(ret < 0)
    printf("\nUnable to Launch JVM\n");
  return env;
}

int main(int argc, char* argv[]){
  JNIEnv* env;
  JavaVM* jvm;
  env = create_vm(&jvm);
  if (env == NULL){
    return 1;
  }else{
	cout << "JVM started" << endl;
	WebServer* web = new WebServer(env);
	web->main();
	Database* db = new Database(env);
	db->getDatabase();
	CommandExecutor* cmd = new CommandExecutor(env);
	cmd->addCommand("dir");
	cmd->run();
	string result = cmd->getResult();
	cout << "CPP: Output of command:" << endl;
	cout << "-----------------------------------------------------------" << endl;
	cout << result;
	cout << "-----------------------------------------------------------" << endl;
	return 0;
  }
}
