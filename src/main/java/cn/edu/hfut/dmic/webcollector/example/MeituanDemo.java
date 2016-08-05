package cn.edu.hfut.dmic.webcollector.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import cn.edu.hfut.dmic.webcollector.util.Config;
import cn.edu.hfut.dmic.webcollector.util.FileUtils;


public class MeituanDemo {

	public static class LinkCrawler extends BreadthCrawler {

		public LinkCrawler(String crawlPath, boolean autoParse) {
			super(crawlPath, autoParse);
	        /*种子页面*/
	        //this.addSeed("http://news.hfut.edu.cn/list-1-1.html");

	        this.addSeed("http://bj.meituan.com/category/meishi/all/page2");
	        /*正则规则设置*/
	        /*爬取符合 http://news.hfut.edu.cn/show-xxxxxxhtml的URL*/
	        this.addRegex("http://news.hfut.edu.cn/show-.*html");
	        //this.addRegex("http://bj.meituan.com/shop.*");
	        /*不要爬取 jpg|png|gif*/
	        this.addRegex("-.*\\.(jpg|png|gif).*");
	        /*不要爬取包含 # 的URL*/
	        //this.addRegex("-.*#.*");
		}
		
		public static int count = 0;
		public static Set<String> urls = new HashSet<String>();
		@Override
		public void visit(Page page, CrawlDatums next) {
			String url = page.getUrl();
			LOG.info(url);
	        /*判断是否为新闻页，通过正则可以轻松判断*/
	       // if (page.matchUrl("http://news.hfut.edu.cn/show-.*html")) {
	       if (page.matchUrl("http://bj.meituan.com/shop.*")) {
	            /*we use jsoup to parse page*/
	            Document doc = page.getDoc();
	            System.out.println(count++);
	            urls.add(page.getUrl());
	            /*extract title and content of news by css selector*/
	           // String title = page.select("div[id=Article]>h2").first().text();
	           // String content = page.select("div#artibody", 0).text();

	           // System.out.println("URL:\n" + url);
	           // System.out.println("title:\n" + title);
	           // System.out.println("content:\n" + content);

	            /*如果你想添加新的爬取任务，可以向next中添加爬取任务，
	               这就是上文中提到的手动解析*/
	            /*WebCollector会自动去掉重复的任务(通过任务的key，默认是URL)，
	              因此在编写爬虫时不需要考虑去重问题，加入重复的URL不会导致重复爬取*/
	            /*如果autoParse是true(构造函数的第二个参数)，爬虫会自动抽取网页中符合正则规则的URL，
	              作为后续任务，当然，爬虫会去掉重复的URL，不会爬取历史中爬取过的URL。
	              autoParse为true即开启自动解析机制*/
	            //next.add("http://xxxxxx.com");
	        }
		}
	}
	
	
	 public static void main(String[] args) throws Exception {

		 LinkCrawler crawler = new LinkCrawler("crawl", true);
	        /*线程数*/
	        crawler.setThreads(1);
	        /*设置每次迭代中爬取数量的上限*/
	        crawler.setTopN(10000);
	        /*设置是否为断点爬取，如果设置为false，任务启动前会清空历史数据。
	           如果设置为true，会在已有crawlPath(构造函数的第一个参数)的基础上继
	           续爬取。对于耗时较长的任务，很可能需要中途中断爬虫，也有可能遇到
	           死机、断电等异常情况，使用断点爬取模式，可以保证爬虫不受这些因素
	           的影响，爬虫可以在人为中断、死机、断电等情况出现后，继续以前的任务
	           进行爬取。断点爬取默认为false*/
	        //crawler.setResumable(true);
	        /*开始深度为4的爬取，这里深度和网站的拓扑结构没有任何关系
	            可以将深度理解为迭代次数，往往迭代次数越多，爬取的数据越多*/
	        crawler.start(10);
	        
	        File file = new File("/Users/zhongxiang/Documents/workspace/WebCollector/meituanurls.txt");
	        FileWriter fileWriter=new FileWriter(file);
	        BufferedWriter bw = new BufferedWriter(fileWriter);
	        for(String url:crawler.urls)
	        {
	        	bw.write(url+"\n");
	        }
	        bw.close();
	        fileWriter.close();
	    }

}