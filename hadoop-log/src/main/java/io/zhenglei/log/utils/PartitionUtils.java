package io.zhenglei.log.utils;

import java.util.HashMap;
import java.util.Map;

public class PartitionUtils {
	private static Map<String, Integer> MAP = new HashMap<>();
	
	public static Map<String, Integer> getInstance(){
		MAP.put("e_pv", 0);
		MAP.put("e_crt", 1);
		MAP.put("e_l", 2);
		MAP.put("e_e", 3);
		MAP.put("e_cs", 4);
		MAP.put("e_cr", 5);
		return MAP;
	}
}
