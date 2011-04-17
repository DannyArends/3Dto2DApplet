/* \file CommandExecutor.cpp
 * \brief source file for CPP JNI interface
 * Copyright:   GBIC 2010-2011, all rights reserved
 * Date:        April 5, 2011
 */

#include "CommandExecutor.h"

CommandExecutor::CommandExecutor(JNIEnv* env){
	init(env, NULL);
}

CommandExecutor::CommandExecutor(JNIEnv* env,jobject obj){
	init(env, obj);
}

CommandExecutor::~CommandExecutor(){

}

void CommandExecutor::init(JNIEnv* env, jobject obj){
	this->env=env;
	this->clsC = env->FindClass("nl/dannyarends/generic/CommandExecutor");
	if(clsC != NULL){
      cout << "CPP: nl.dannyarends.CommandExecutor found" << endl;
      this->coID = env->GetMethodID(clsC, "<init>", "()V");
      check(env,"CPP: nl.dannyarends.CommandExecutor constructor mapped",false);
      if(obj == NULL){
  		this->obj = env->NewObject(this->clsC, this->coID);
      }else{
  		this->obj = obj;
      }
      check(env,"CPP: nl.dannyarends.CommandExecutor created",false);
      this->addCommandID = env->GetMethodID(this->clsC, "addCommand", "(Ljava/lang/String;)V");
      check(env,"CPP: nl.dannyarends.CommandExecutor.addCommand() mapped",false);
      this->runID = env->GetMethodID(this->clsC, "run", "()V");
      check(env,"CPP: nl.dannyarends.CommandExecutor.run() mapped",true);
      this->getResultID = env->GetMethodID(this->clsC, "getResult", "()Ljava/lang/String;");
      check(env,"CPP: nl.dannyarends.CommandExecutor.getResult() mapped",false);
  	}else{
      cout << "CPP_ERROR: No such class: nl.dannyarends.CommandExecutor" << endl;
  	}
}

jobject CommandExecutor::getJava(){
  return this->obj;
}

void CommandExecutor::addCommand(string cmd){
  env->CallVoidMethod(this->obj,addCommandID, env->NewStringUTF(cmd.c_str()));
  check(env,"CPP: nl.dannyarends.CommandExecutor.getDatabase() called",false);
}

void CommandExecutor::run(){
  env->CallVoidMethod(this->obj,runID, NULL);
  check(env,"CPP: nl.dannyarends.CommandExecutor.run() called",false);
}

string CommandExecutor::getResult(){
  jboolean blnIsCopy;
  jstring res = (jstring)env->CallObjectMethod(this->obj,getResultID, NULL);
  string strCIn = env->GetStringUTFChars(res , &blnIsCopy);
  check(env,"CPP: nl.dannyarends.CommandExecutor.getResult() called",false);
  return strCIn;
}
