<#--#####################################################################-->
<#--                                                                   ##-->
<#--         START OF THE OUTPUT                                       ##-->
<#--                                                                   ##-->
<#--#####################################################################-->
/* \file ${file}
 * Copyright:   Danny Arends 2011-${year?c}, all rights reserved
 * \date ${date}
 * \brief Generated source file for CPP JNI interface
 */
 
#include "${entity.name}.h"

${entity.name}::${entity.name}(JNIEnv* env){
	init(env,NULL);
}

${entity.name}::${entity.name}(JNIEnv* env, jobject obj){
	init(env,obj);
}

${entity.name}::${entity.name}(JNIEnv* env<#foreach attribute in entity.getAttributes()>, ${attribute.cpptype} ${attribute.name}</#foreach>){
	init(env,NULL);
	<#foreach attribute in entity.getAttributes()>
	set${attribute.name}(${attribute.name});
	</#foreach>
}

void ${entity.name}::init(JNIEnv* env,jobject obj){
	this->verbose = false;
	this->env=env;
	this->clsC = env->FindClass("<#if entity.namespace?exists>${entity.namespace?replace(".","/")}/</#if>${entity.name}");
	if(clsC != NULL){
    	cout << "Found: ${entity.namespace}.${entity.name}" << endl;
    	this->coID = env->GetMethodID(this->clsC, "<init>", "()V");
    	check(env,"Mapped: ${entity.namespace}.${entity.name} Constructor",verbose);
    	this->obj = env->NewObject(this->clsC, this->coID);
      	check(env,"Created: ${entity.namespace}.${entity.name}",verbose);
      	
    	<#foreach attribute in entity.getAttributes()>
    	this->get${attribute.name}ID = env->GetMethodID(this->clsC, "get${attribute.name}", "()${attribute.jnitype}");
    	check(env,"Mapped: ${attribute.jnitype} ${entity.namespace}.${entity.name}.get${attribute.name}()",verbose);
    	this->set${attribute.name}ID = env->GetMethodID(this->clsC, "set${attribute.name}", "(${attribute.jnitype})V");
    	check(env,"Mapped: void ${entity.namespace}.${entity.name}.set${attribute.name}(${attribute.jnitype})",verbose);
    	</#foreach>
    	if(obj == NULL){
    		this->obj = env->NewObject(this->clsC, this->coID);
    	}else{
    		this->obj = obj;
    	}
    	check(env,"Constructed object: ${entity.namespace}.${entity.name}",verbose);
  	}else{
  	  cout << "No such class: ${entity.namespace}.${entity.name} class" << endl;
  	}
}

jobject ${entity.name}::getJava(){
  return this->obj;
}

${entity.name}::~${entity.name}(){
	//Compiler TODO: Figure out if I need manually to call the entity.hasAncestor
}


<#foreach field in entity.getAttributes()>

</#foreach>