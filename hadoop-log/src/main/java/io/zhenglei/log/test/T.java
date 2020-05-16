package io.zhenglei.log.test;

public class T {

	public static String str2 = null;
	
	public static void aaa(String s){
		s = "welcome";
	}
	
	public static void main(String[] args) {
		String str = "1234";
		str2 = "4567";
		aaa(str);
		aaa(str2);
		System.out.println(str);
		System.out.println(str2);
	}
}
