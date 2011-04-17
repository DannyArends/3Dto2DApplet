/* \file Database.cpp
 * \brief Source file for CPP JNI interface
 * Copyright:   GBIC 2010-2011, all rights reserved
 * Date:        April 5, 2011
 */

#include "Database.h"

Database::Database(JNIEnv* env){
	init(env, NULL);
}

Database::Database(JNIEnv* env,jobject obj){
	init(env, obj);
}

Database::~Database(){

}

void Database::init(JNIEnv* env, jobject obj){
	this->env=env;
	this->clsC = env->FindClass("nl/dannyarends/db/Database");
	if(clsC != NULL){
      cout << "CPP: nl.dannyarends.db.Database found" << endl;
      this->coID = env->GetMethodID(clsC, "<init>", "()V");
      check(env,"CPP: nl.dannyarends.db.Database constructor mapped",false);
      if(obj == NULL){
  		this->obj = env->NewObject(this->clsC, this->coID);
      }else{
  		this->obj = obj;
      }
      check(env,"CPP: nl.dannyarends.db.Database created",true);

      this->getDatabaseID = env->GetMethodID(this->clsC, "getDatabase", "()Ljava/sql/Connection;");
      check(env,"CPP: nl.dannyarends.db.Database.getDatabase() mapped",false);

  	}else{
      cout << "CPP_ERROR: No such class: nl.dannyarends.db.Database" << endl;
  	}
}

jobject Database::getJava(){
  return this->obj;
}

jobject Database::getDatabase(){
  jobject temp =  env->CallObjectMethod(this->obj,getDatabaseID, NULL);
  check(env,"CPP: nl.dannyarends.db.Database.getDatabase() called",false);
  return temp;
}
