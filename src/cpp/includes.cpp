#include "includes.h"

void check(JNIEnv* env, string message, bool verbose){
  if (env->ExceptionOccurred()) {
	env->ExceptionDescribe();
  }else{
  	if(verbose)	cout << message << endl;
  }
}
