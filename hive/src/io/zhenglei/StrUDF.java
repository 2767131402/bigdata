package io.zhenglei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.hadoop.hive.ql.exec.UDF;

public class StrUDF extends UDF {
	public String evaluate(String str) {
		return str + "!!!";
	}

	public int evaluate(int a,int b) {
		return a+b;
	}
	
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		list.add(3);
		list.add(1);
		list.add(2);
		Collections.sort(list);
		
		for (Integer i : list) {
			System.out.println(i);
		}
	}
}
