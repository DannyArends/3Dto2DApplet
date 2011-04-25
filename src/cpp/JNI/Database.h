/* \file Database.h
 * \brief Header file for CPP JNI interface
 * Copyright:   GBIC 2010-2011, all rights reserved
 * Date:        April 5, 2011
 */

#ifndef DATABASE_H_
  #define DATABASE_H_

  #include "includes.h"

  /**
   * \brief Database CPP JNI wrapper<br>
   *
   * This class contains the CPP wrapper for the Database JAVA class
   * bugs: none found<br>
   */
  class Database{
  public:
	Database(JNIEnv* env);
	Database(JNIEnv* env, jobject obj);
	~Database();

  	jobject getJava();
  	jobject getDatabase();
  protected:
  	JNIEnv*     env;
  	jclass      clsC;
  	jobject 	obj;
  	jmethodID   coID;
  	jmethodID   getDatabaseID;
  private:
  	void init(JNIEnv* env, jobject obj);
  };

#endif
