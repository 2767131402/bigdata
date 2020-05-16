package io.zhenglei.log.utils.browser;

import java.net.URLDecoder;

import cz.mallat.uasparser.UserAgentInfo;

public class BrowserUtils {
	public static String getBrowser(String browser){
		String agentDecoder=URLDecoder.decode(browser);
		UserAgentInfo userAgentInfo=UserAgentUtils.getUserAgentInfo(agentDecoder);
		return userAgentInfo.getUaFamily();
	}
}
