package com.ishanitech.ipasal.ipasalwebservice.utilities;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HostDetailsUtil {
	@Value("${server.port}")
	int port;
	
	private static final String HTTP_PREFIX = "http://";
    
    public String getHostUrl() {
    	String hostAddress = null;
    	try {
//    		hostAddress = InetAddress.getLocalHost().getHostAddress();
			hostAddress = "103.233.58.121";
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
        return HTTP_PREFIX + hostAddress + ":" + port + "/";
    }
}