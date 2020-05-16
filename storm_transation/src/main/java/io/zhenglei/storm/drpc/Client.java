package io.zhenglei.storm.drpc;

import java.util.Map;

import org.apache.storm.utils.DRPCClient;
import org.apache.storm.utils.Utils;

public class Client {
	public static void main(String[] args) {
		try {
			Map config = Utils.readDefaultConfig();
			DRPCClient client = new DRPCClient(config,"192.168.6.122", 3772);
			String result = client.execute("fun", "2018-10-10 2018-06-10 2018-10-11"); 
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
