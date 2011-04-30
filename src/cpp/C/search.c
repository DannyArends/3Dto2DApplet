#include "search.h"

//Basic linear search
int linearsearch(int element,int* a,int length){
  int i;
  for(i=0; i<length; i++){
    if(a[i] == element){
      return i;
    }
  }
  return -1;
}

//Basic binary search
int binarysearch(int element,int* a,int min,int max){
  int mid;
  while(min<=max){
    mid=(min+max)/2;
    if(element==a[mid]) return mid;
    if(element>a[mid]){
      min=mid+1;
    }else{
      max=mid-1;
    }
  }
  return -1;
}

//Basic binary search recursive variant
int binarysearch_rec(int element,int* a,int min,int max){
  int mid;
  if(min==max){
    if(a[min]==element){
      return min;
    }else{
      return -1;
    }
  }else{
    mid= min + (max - min) / 2;
    if(element==a[mid]){
      return mid;
	}
    if(element>a[mid]){
	  return binarysearch_rec(element,a,mid+1,max);
    }
	if(element<a[mid]){
	  return binarysearch_rec(element,a,min,mid-1);
	}
  }
  return -1;
}
