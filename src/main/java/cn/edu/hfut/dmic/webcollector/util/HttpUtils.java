package cn.edu.hfut.dmic.webcollector.util;

import java.io.IOException;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import cn.edu.hfut.dmic.webcollector.net.HttpRequest;

public class HttpUtils
{
	public static String getServerPath(HttpRequest request)
	{
		System.out.println(); 
		
		String schema ="",serverName="";
		int serverPort=8080;
		if(request != null)
		{
//			schema = request.getScheme();
//			serverName = request.getServerName();
//			serverPort = request.getServerPort();
		}
		return schema+"://"+serverName+":"+serverPort;
	}
	
	public static String do_get(String url) throws ClientProtocolException, IOException {
        String body = "{}";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            body = EntityUtils.toString(entity);
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return body;
    }
}
