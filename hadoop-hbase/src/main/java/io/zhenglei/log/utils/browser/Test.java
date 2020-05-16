package io.zhenglei.log.utils.browser;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import cz.mallat.uasparser.UserAgentInfo;

public class Test {

	public static void main(String[] args) {
		
//		String agent="Mozilla%2F5.0%20(Windows%20NT%206.1%3B%20WOW64%3B%20rv%3A54.0)%20Gecko%2F20100101%20Firefox%2F54.0";
//		String agentDecoder=URLDecoder.decode(agent);
//		UserAgentInfo userAgentInfo=UserAgentUtils.getUserAgentInfo(agentDecoder);
//		System.out.println(userAgentInfo.getUaFamily());
//		
		try {
			System.out.println(URLDecoder.decode("%E6%B5%8B%E8%AF%95%E8%AE%A2%E5%8D%95123456","UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
