package cn.edu.hfut.dmic.webcollector.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlParserTest {
	/**
	 * 要分析的网页
	 */
	String htmlUrl;
	/**
	 * 分析结果
	 */
	Set<String> hrefSet = new HashSet();
	List<String> hrefList = new ArrayList();
	/**
	 * 网页编码方式
	 */
	String charSet;

	public HtmlParserTest(String htmlUrl) {
		// TODO 自动生成的构造函数存根
		this.htmlUrl = htmlUrl;
	}

	/**
	 * 获取分析结果
	 * 
	 * @throws IOException
	 */
	public Set<String> getHrefSet() throws IOException {
		parser();
		return hrefSet;
	}

	public List<String> getHrefList() throws IOException {
		parser();
		return hrefList;
	}

	/**
	 * 解析网页链接
	 * 
	 * @return
	 * @throws IOException
	 */
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
				hrefList.add(str);
			}
		}
	}

	public boolean matchUrl(String urlRegex, String url) {
		return Pattern.matches(urlRegex, url);
	}

	/**
	 * 获取网页编码方式
	 * 
	 * @param str
	 */
	private String getCharset(String str) {
		Pattern pattern = Pattern.compile("charset=.*");
		Matcher matcher = pattern.matcher(str);
		if (matcher.find())
			return matcher.group(0).split("charset=")[1];
		return null;
	}

	/**
	 * 从一行字符串中读取链接
	 * 
	 * @return
	 */
	private String getHref(String str) {
		// Pattern pattern = Pattern.compile("<a href=.*</a>");
		Pattern pattern = Pattern.compile("<a.*?href=\"http://bj.meituan.com/shop(.*?)\">(.*?)</a>");
		// Pattern pattern = Pattern.compile(" http://bj.meituan.com/shop");

		Matcher matcher = pattern.matcher(str);
		if (matcher.find()) {
			System.out.println(matcher.group(0));
			return matcher.group(0);
		}
		return null;
	}

	// 朝阳区,海淀区,丰台区,西城区,东城区,昌平区,石景山区,通州区,大兴区,顺义区,房山区,密云县,怀柔区,延庆县,平谷区,门头沟
	static String[] districts = { "chaoyangqu", "haidianqu", "fengtaiqu", "xichengqu", "dongchengqu", "changpingqu",
			"shijingshanqu", "tongzhouqu", "daxingqu", "shunyiqu", "fangshanqu", "miyunxian", "huairouqu",
			"yanqingxian", "pingguqu", "mentougou" };

	public static void main(String[] arg) throws IOException {
		int count = 1;
		File file = new File("/Users/zhongxiang/Desktop/meituanshopurls.txt");
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		int pages = 10;
		for (String district : districts) {
			System.out.println(district);
			for (int i = 1; i <= pages; i++) {
				HtmlParserTest a = new HtmlParserTest("http://bj.meituan.com/category/meishi/" + district + "/page" + i);
				Set<String> hrefSet = a.getHrefSet();
				for (String url : hrefSet) {
					System.out.println(count++ + "\t" + url);
					url = url.substring(url.lastIndexOf("/")+1);
					bw.write(url+"\n");
				}
				// List<String> hrefList = a.getHrefList();
				// for (String url : hrefList) {
				// System.out.println(count+++"\t"+url);
				// }
			}
		}
		bw.close();
		fw.close();
	}
}