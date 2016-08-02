package com.test;


public class test1 {
	public static void main(String args[]){
		int array[] ={13,6,24,11,55,10};
		getPrint(array);
		Sort(array);
	}
	
	public static void Sort(int[] arry){
		for(int i=0;i<arry.length;i++){
			for(int j=0;j<arry.length-i-1;j++){
				if(arry[j]>arry[j+1]){
					int temp = arry[j];
					arry[j] = arry[j+1];
					arry[j+1] = temp;
				}
			}
		}
		getPrint(arry);
	}
	public static void getPrint(int a[]){
		for(int i=0;i<a.length;i++){
			System.out.print(a[i]+" ");
		}
		System.out.println();
	}
}
