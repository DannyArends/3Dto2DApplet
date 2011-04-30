#include "sort.h"

void bubble_sort(int a[],int n){
  int t,i,j;
  for(i=n-2;i>=0;i--){
    for(j=0;j<=i;j++){
    if(a[j]>a[j+1]){
        t=a[j];
        a[j]=a[j+1];
        a[j+1]=t;
      }
    }
  }
}

void selection_sort(int list[], int n){
  int i, j, min;
 
  for (i = 0; i < n - 1; i++){
    min = i;
    for (j = i+1; j < n; j++){
      if (list[j] < list[min]){          
        min = j;
      }
    }
    swap(&list[i], &list[min]);
  }
}
