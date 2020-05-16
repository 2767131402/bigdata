package io.zhenglei.log.test;

import io.zhenglei.log.utils.DateFormatUtils;
import io.zhenglei.log.utils.DateTransYMDUtils;
import io.zhenglei.log.utils.browser.BrowserUtils;

public class Test {

	public static void main(String[] args) {
//		String key = "s/logo.jpg?en=e_crt&oid=123456&on=";
//		int i = key.toString().indexOf("en=");
//		String str = key.toString().substring(i, key.toString().indexOf("&"));
//		System.out.println(str);\
//		System.out.println(DateTransYMDUtils.getYear("2017-12-12"));
//		System.out.println(DateFormatUtils.format1(1497929273272L));
		String agent="Mozilla%2F5.0%20(Windows%20NT%206.1%3B%20WOW64%3B%20rv%3A54.0)%20Gecko%2F20100101%20Firefox%2F54.0";
		String string = BrowserUtils.getBrowser(agent);
		System.out.println(string);
	}

}
