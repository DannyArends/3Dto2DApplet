/* \file CommandExecutor.h
 * \brief Header file for CPP JNI interface
 * Copyright:   GBIC 2010-2011, all rights reserved
 * Date:        April 5, 2011
 */

#ifndef COMMANDEXECUTOR_H_
  #define COMMANDEXECUTOR_H_

  #include "includes.h"

  /**
   * \brief CommandExecutor<br>
   * This class contains the implementation of CommandExecutor
   * bugs: none found<br>
   */
  class CommandExecutor{
  public:
	CommandExecutor(JNIEnv* env);
	CommandExecutor(JNIEnv* env, jobject obj);
	~CommandExecutor();

  	jobject getJava();
  	void 	addCommand(string cmd);
  	void 	run();
  	string 	getResult();
  protected:
  	JNIEnv*     env;
  	jclass      clsC;
  	jobject 	obj;
  	jmethodID   coID;
  	jmethodID   addCommandID;
  	jmethodID   runID;
  	jmethodID	getResultID;
  private:
  	void init(JNIEnv* env, jobject obj);
  };

#endif
