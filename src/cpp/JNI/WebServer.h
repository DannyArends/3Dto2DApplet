/* \file WebServer.h
 * \brief Header file for CPP JNI interface
 * Copyright:   GBIC 2010-2011, all rights reserved
 * Date:        April 5, 2011
 */

#ifndef WEBSERVER_H_
  #define WEBSERVER_H_

  #include "includes.h"

  /**
   * \brief WebServer CPP JNI wrapper<br>
   *
   * This class contains the CPP wrapper for the WebServer JAVA class
   * bugs: none found<br>
   */
  class WebServer{
  public:
	 WebServer(JNIEnv* env);
	 WebServer(JNIEnv* env, jobject obj);
	~WebServer();

  	jobject getJava();
  	void main();
  protected:
  	JNIEnv*     env;
  	jclass      clsC;
  	jobject 	obj;
  	jmethodID   coID;
  	jmethodID   mainID;
  private:
  	void init(JNIEnv* env, jobject obj);
  };

#endif
