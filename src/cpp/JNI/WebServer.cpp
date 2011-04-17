/* \file WebServer.cpp
 * \brief Source file for CPP JNI interface
 * Copyright:   GBIC 2010-2011, all rights reserved
 * Date:        April 5, 2011
 */

#include "WebServer.h"

WebServer::WebServer(JNIEnv* env){
	init(env, NULL);
}

WebServer::WebServer(JNIEnv* env,jobject obj){
	init(env, obj);
}

WebServer::~WebServer(){

}

void WebServer::init(JNIEnv* env, jobject obj){
	this->env=env;
	this->clsC = env->FindClass("nl/dannyarends/www/WebServer");
	if(clsC != NULL){
      cout << "CPP: nl.dannyarends.www.WebServer found" << endl;
      this->coID = env->GetMethodID(clsC, "<init>", "()V");
      check(env,"CPP: nl.dannyarends.www.WebServer constructor mapped",false);
      if(obj == NULL){
  		this->obj = env->NewObject(this->clsC, this->coID);
      }else{
  		this->obj = obj;
      }
      check(env,"CPP: nl.dannyarends.www.WebServer created",true);

      this->mainID = env->GetStaticMethodID(this->clsC, "main", "([Ljava/lang/String;)V");
      check(env,"CPP: nl.dannyarends.www.WebServer.main() mapped",false);

  	}else{
      cout << "CPP_ERROR: No such class: nl.dannyarends.www.WebServer" << endl;
  	}
}

jobject WebServer::getJava(){
  return this->obj;
}

void WebServer::main(){
  env->CallStaticVoidMethod(this->clsC,mainID, NULL);
  check(env,"CPP: nl.dannyarends.WebServer.main() called",false);
}
