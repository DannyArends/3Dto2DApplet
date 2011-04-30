/**
 * \file search.h
 *
 *  Created on: Apr 30, 2011
 *      Author: Danny Arends
 */

#ifndef SEARCH_H_
  #define SEARCH_H_
  #include "basic.h"

  int linearsearch(int element,int* a,int length);
  int binarysearch(int element,int* a,int min,int max);
  int binarysearch_rec(int element,int* a,int min,int max);

#endif
