package cn.edu.hfut.dmic.webcollector.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import cn.edu.hfut.dmic.webcollector.entity.Comment;
import cn.edu.hfut.dmic.webcollector.entity.Customer;
import cn.edu.hfut.dmic.webcollector.entity.Location;
import cn.edu.hfut.dmic.webcollector.entity.Shop;
import cn.edu.hfut.dmic.webcollector.service.ICommentService;
import cn.edu.hfut.dmic.webcollector.service.ICustomerService;
import cn.edu.hfut.dmic.webcollector.service.ILocationService;
import cn.edu.hfut.dmic.webcollector.service.IShopService;

public class HtmlParser {
	// 要分析的网页
	String htmlUrl;
	//分析结果
	Set<String> hrefSet = new HashSet<String>();
	//网页编码方式
	String charSet;
	
	public HtmlParser(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}
	
	//获取分析结果
	public Set<String> getHrefSet() throws IOException {
		parser();
		return hrefSet;
	}

	// 解析网页链接
	private void parser() throws IOException {
		URL url = new URL(htmlUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		String contenttype = connection.getContentType();
		charSet = getCharset(contenttype);
		InputStreamReader isr = new InputStreamReader(connection.getInputStream(), charSet);
		BufferedReader br = new BufferedReader(isr);
		String str = null, rs = null;
		File input = new File(htmlUrl);
		while ((str = br.readLine()) != null) {
			if (str.contains("http://bj.meituan.com/shop"))// matchUrl("http://bj.meituan.com/shop.*",
															// str))
			{
				int index = str.indexOf("href");
				str = str.substring(index);
				int end = str.indexOf(" ");
				str = str.substring(0, end);
				str = str.split("href=")[1];
				end = str.indexOf("#");
				if (end > 0)
					str = str.substring(0, end);
				str = str.replace("\"", "");
				hrefSet.add(str);
				//hrefList.add(str);
			}
		}
	}

	//获取网页编码方式
	private String getCharset(String str) {
		Pattern pattern = Pattern.compile("charset=.*");
		Matcher matcher = pattern.matcher(str);
		if (matcher.find())
			return matcher.group(0).split("charset=")[1];
		return null;
	}

	//从一行字符串中读取链接
	private String getHref(String str) {
		Pattern pattern = Pattern.compile("<a href=.*</a>");
		Matcher matcher = pattern.matcher(str);
		if (matcher.find())
			return matcher.group(0);
		return null;
	}

	public static void main(String[] arg) throws IOException {
		
	}
}