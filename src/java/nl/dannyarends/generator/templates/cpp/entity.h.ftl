<#--#####################################################################-->
<#--                                                                   ##-->
<#--         START OF THE OUTPUT                                       ##-->
<#--                                                                   ##-->
<#--#####################################################################-->
/* \file ${file}
 * Copyright:   Danny Arends 2011-${year?c}, all rights reserved
 * \date ${date}
 * \brief Generated header file for CPP JNI interface
 */

#ifndef ${entity.name}_H_
  #define ${entity.name}_H_
	
	#include <cstdlib>
	#include <cstdio>
	#include <string>
	#include <cstring>
	#include <iostream>
	#include <vector>
	#include <jni.h>

	using namespace std;
	
  
  /**
   * \brief ${entity.name}<br>
   * This class contains the implementation of ${entity.name}
   * It provides 2 constructors both call init to have the java class mapped in the JVM environment
   * For each field getters and setters are provided, setting the CPP state of the object
   * Call the save() function to save the object in the data
   * bugs: none found<br>
   */  
  class ${entity.name}{
  public:
  	//Constructors
  	${entity.name}(JNIEnv* env);
  	${entity.name}(JNIEnv* env, jobject obj);
  	${entity.name}(JNIEnv* env<#foreach attribute in entity.getAttributes()>, ${attribute.cpptype} ${attribute.name}</#foreach>);
  	
  	
  	jobject getJava();
  	~${entity.name}();
  	
  	//Getters and Setters wrapping the JNI
  	<#foreach attribute in entity.getAttributes()>
  	${attribute.cpptype} get${attribute.name}(void);
  	void set${attribute.name}(${attribute.cpptype} in);
  	</#foreach>
  	
  protected:
  	JNIEnv*     env;
  	jclass      clsC;
  	jobject 	obj;
  	jmethodID   coID;
  	
  	<#foreach attribute in entity.getAttributes()>
  	jmethodID   get${attribute.name}ID;
  	jmethodID   set${attribute.name}ID;
  	</#foreach>
  private:
  	void 		init(JNIEnv* env,jobject obj);
  	bool 		verbose;
  };
#endif