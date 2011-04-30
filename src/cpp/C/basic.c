#include "basic.h"

int reverse_number(int n){
  int a;
  int t=0;
  while(n>=1){
    a=n%10;
    t=t*10+a;
    n=n/10;
  }
  return t;
}

void swap(int *a,int *b){
  int temp;
  temp=*a;
  *a=*b;
  *b=temp;
}

void in_place_swap(int *a,int *b){
  *a = *a + *b;
  *b = *a - *b;
  *a = *a - *b;
}
