package cn.edu.hfut.dmic.webcollector.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jetty.util.log.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import cn.edu.hfut.dmic.webcollector.entity.Comment;
import cn.edu.hfut.dmic.webcollector.entity.Crawlshop;
import cn.edu.hfut.dmic.webcollector.entity.Customer;
import cn.edu.hfut.dmic.webcollector.entity.Location;
import cn.edu.hfut.dmic.webcollector.entity.Shop;
import cn.edu.hfut.dmic.webcollector.service.ICommentService;
import cn.edu.hfut.dmic.webcollector.service.ICrawlshopService;
import cn.edu.hfut.dmic.webcollector.service.ICustomerService;
import cn.edu.hfut.dmic.webcollector.service.ILocationService;
import cn.edu.hfut.dmic.webcollector.service.IShopService;

public class MeituanUtils {

	public static final Logger LOG = LoggerFactory.getLogger(MeituanUtils.class);
	// 朝阳区,海淀区,丰台区,西城区,东城区,昌平区,石景山区,通州区,大兴区,顺义区,房山区,密云县,怀柔区,延庆县,平谷区,门头沟
	static String[] districts = { "chaoyangqu", "haidianqu", "fengtaiqu", "xichengqu", "dongchengqu", "changpingqu",
			"shijingshanqu", "tongzhouqu", "daxingqu", "shunyiqu", "fangshanqu", "miyunxian", "huairouqu",
			"yanqingxian", "pingguqu", "mentougou" };

	public static String SHOP_SERVER_URL = "http://bj.meituan.com/shop/";
	// 保存商家URL信息
	//static String meituanshopurls = "/Users/zhongxiang/Desktop/meituanshopurls.txt";

	static ILocationService locationService = (ILocationService) SpringUtil.getBean("locationService");
	static IShopService shopService = (IShopService) SpringUtil.getBean("shopService");
	static ICustomerService customerService = (ICustomerService) SpringUtil.getBean("customerService");
	static ICommentService commentService = (ICommentService) SpringUtil.getBean("commentService");
	static ICrawlshopService crawlshopService = (ICrawlshopService) SpringUtil.getBean("crawlshopService");
	
	static Shop shop = null;

	public static void main(String[] arg) throws IOException {
		// 1.
		//extractShopUrls();
		// 2.
		List<Crawlshop> shops = crawlshopService.getCrawlshopList(1);
		
		LOG.info("开始获取所有商家评论信息");
		long start = System.currentTimeMillis();
		batchShops(shops);
		//batchShops(meituanshopurls);
		long end = System.currentTimeMillis();
		
		long seconds = (end - start + 999) / 1000;
		long minutes = seconds/60;
		long hours = minutes/60;
		long days = hours/24;
		LOG.info("完成所有商家评论信息获取，花费" + days + "天"+ hours%24 + "小时"+ minutes%60 + "分"+ seconds%60 + "秒");
	}

	// 1.抽取所有商家URL
	public static void extractShopUrls() {
		try {
			int count = 1;
//			File file = new File(meituanshopurls);
//			FileWriter fw;
//			fw = new FileWriter(file);
//			BufferedWriter bw = new BufferedWriter(fw);
			int pages = 10;

			LOG.info("开始获取商家URL信息");
			long start = System.currentTimeMillis();
			for (String district : districts) {
				System.out.println(district);
				for (int i = 1; i <= pages; i++) {
					HtmlParser a = new HtmlParser("http://bj.meituan.com/category/meishi/" + district + "/page" + i);
					Set<String> hrefSet = a.getHrefSet();
					for (String url : hrefSet) {
						LOG.info(count++ +"-"+district+ "\t" + url);
						url = url.substring(url.lastIndexOf("/") + 1);
//						bw.write(url + "\n");
						if(DateFormatTools.isNumeric(url))
						{
							Crawlshop shop = new Crawlshop();
							shop.setShopid(Long.valueOf(url));
							shop.setStatus(1);
							shop.setCreatetime(new Date());
							shop.setUpdatetime(new Date());
							crawlshopService.save(shop);
						}						
					}
				}
			}
			//int currentDistrict = 0;
//			for (int k=districts.length-1;k<districts.length;k++) {
//				System.out.println(districts[k]);
//				for (int i = 1; i <= pages; i++) {
//					HtmlParser a = new HtmlParser("http://bj.meituan.com/category/meishi/" + districts[k] + "/page" + i);
//					Set<String> hrefSet = a.getHrefSet();
//					for (String url : hrefSet) {
//						LOG.info(count++ + "\t" + url);
//						url = url.substring(url.lastIndexOf("/") + 1);
//						bw.write(url + "\n");
//					}
//				}
//			}
			
//			bw.close();
//			fw.close();
			long end = System.currentTimeMillis();
			LOG.info("完成商家URL信息获取，花费" + (end - start + 999) / 1000 + "秒");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 2.批量抽取所有商家所有评论
	static int commentPages = 1;
	static int shopCommentPages = 1;

	@SuppressWarnings("resource")
	public static void batchShops(String filepath) {
//		File file = new File(meituanshopurls);
//		try {
//			FileReader fr = new FileReader(file);
//			BufferedReader br = new BufferedReader(fr);
//			String line = null;
//			while ((line = br.readLine()) != null) {
//				int shopId = Integer.valueOf(line);
//				shop = null;
//				// 1.解析店家页面，得到店家信息
//				getShopDetails(shopId);
//				// 2.解析店家页面，得到评论信息
//				if(shop != null)
//				{
//					getComments(shopId);
//					shopCount++;
//				}	
//			}			
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public static void batchShops(List<Crawlshop> shops) {
		try {
			for(Crawlshop crawlshop:shops)
			{
				shop = null;
				// 1.解析店家页面，得到店家信息
				getShopDetails(crawlshop.getShopid());
				// 2.解析店家页面，得到评论信息
//				if(shop != null)
//				{
//					getComments(crawlshop.getShopid());
//					shopCount++;
//				}	
			}
//			while ((line = br.readLine()) != null) {
//				int shopId = Integer.valueOf(line);
//				shop = null;
//				// 1.解析店家页面，得到店家信息
//				getShopDetails(shopId);
//				// 2.解析店家页面，得到评论信息
//				if(shop != null)
//				{
//					getComments(shopId);
//					shopCount++;
//				}	
//			}			
//		} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// 获取一家商家的总评论数
	@SuppressWarnings("unchecked")
	public static int getCommentCount(String url, String param) {
		String res = RequestUtil.sendGet(url, param);
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		map = gson.fromJson(res, map.getClass());
		int total = 0;
		if (map != null && map.get("status") != null) {
			double dstatus = (Double.valueOf(map.get("status").toString()));
			int status = (int) dstatus;
			if (status == 1) {
				LinkedTreeMap<String, Object> ltm = (LinkedTreeMap<String, Object>) map.get("data");
				if (ltm.containsKey("total")) {
					String totalStr = ltm.get("total").toString();
					double dtotal = (Double.valueOf(totalStr));
					total = (int) dtotal;
				}
			}
		}
//		if (total == 0) {
//			shop.setStatus(0);
//			shopService.modify(shop);
//		}
		return total;
	}

	// 获取某个商家所有评论
	public static void getComments(long shopid) {
		LOG.info("开始获取商家"+shopid+"评论信息");
		long start = System.currentTimeMillis();
		String url = "http://bj.meituan.com/deal/feedbacklist/0/" + shopid + "/all/1/default/1";
		//String url = "http://bj.meituan.com/deal/feedbacklist/0/" + 281704 + "/all/1/default/1";
		String param = "limit=1&showpoititle=0&offset=0";
		long totals = getCommentCount(url, param);
		//totals = shop.getComments();

		int limit = 10;
		int offset = 0;

		totals = (totals + limit - 1) / limit;
		for (int i = 0; i < totals; i += 1) {

			param = "limit=" + limit + "&showpoititle=0&offset=" + offset;
			offset += limit;
			getComment(url, param);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		

		long end = System.currentTimeMillis();
		
		long seconds = (end - start + 999) / 1000;
		long minutes = seconds/60;
		long hours = minutes/60;
		LOG.info("完成商家"+shopid+"评论信息获取，花费" + hours%24 + "小时"+ minutes%60 + "分"+ seconds%60 + "秒");
		if(shop!=null)
		{
			Crawlshop cshop = crawlshopService.getCrawlshopBySid(shop.getSid());
			cshop.setStatus(0);
			cshop.setUpdatetime(new Date());
			crawlshopService.modify(cshop);
		}		
	}

	// 获取一页评论limit＝10
	@SuppressWarnings("unchecked")
	public static void getComment(String url, String param) {
		// LOG.info("历史总评论页数"+commentPages+++":"+param);
		// LOG.info("当前商家总评论页数"+shopCommentPages+++":"+param);
		String res = RequestUtil.sendGet(url, param);
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		map = gson.fromJson(res, map.getClass());
		if (map != null && map.get("status") != null) {
			double dstatus = (Double.valueOf(map.get("status").toString()));
			int status = (int) dstatus;
			if (status == 1) {
				LinkedTreeMap<String, Object> ltm = (LinkedTreeMap<String, Object>) map.get("data");
				if (ltm.containsKey("ratelistHtml")) {
					String ratelistHtml = ltm.get("ratelistHtml").toString();
					parserCommentHtml(ratelistHtml);
				}
			}
		}
	}

	// 获取商家详细信息
	static int shopCount = 1;

	public static void getShopDetails(long shopid) {
		shopCommentPages = 1;
		int shopFlags = 0;
		Location location = new Location();
		
		String shopName = "", shopAddress = "", shopScore = "", shopPhone = "", shopType = "", shopConsumerNum = "",
				shopCommentNum = "";
		String url = SHOP_SERVER_URL + shopid;
		String html = RequestUtil.sendGet(url);
		Document doc = Jsoup.parseBodyFragment(html);
		Element body = doc.body();

		Elements summaryEles = body.getElementsByAttributeValue("class", "summary biz-box fs-section cf");
		if (summaryEles != null && summaryEles.size() > 0) {
			Element sumaryEle = summaryEles.get(0);
			//  左半部分，包含名称，地址，电话信息
			Elements leftEles = sumaryEle.getElementsByAttributeValue("class", "fs-section__left");
			if (leftEles != null && leftEles.size() > 0) {
				Elements titleEles = leftEles.get(0).getElementsByAttributeValue("class", "title");
				if (titleEles != null && titleEles.size() > 0) {
					shopName = titleEles.get(0).text();
				}

				Elements addressEles = leftEles.get(0).getElementsByAttributeValue("class", "under-title");
				if (addressEles != null && addressEles.size() > 0) {
					if (addressEles.size() > 0)
						shopAddress = addressEles.get(0).text();
					if (addressEles.size() > 1)
						shopPhone = addressEles.get(1).text();

					Map<String, Object> locationMap = GeoCodingUtils.geoDecodingFromBaidu(shopAddress.split(" ")[0]);
					if (locationMap != null && locationMap.get("status") != null && locationMap.get("status").toString().equals("1")) {
						location.setAddress(shopAddress);
						double lat = Double.valueOf(locationMap.get("lat").toString());
						location.setLat(lat);
						double lng = Double.valueOf(locationMap.get("lng").toString());
						location.setLng(lng);
						location.setCreatetime(new Date());
						location.setUpdatetime(new Date());
						locationService.save(location);
					}
				}
			}
			//  右半部分，包含名称，地址，电话信息
			Elements rightEles = sumaryEle.getElementsByAttributeValue("class", "fs-section__right");
			if (rightEles != null && rightEles.size() > 0) {
				// info部分
				Elements infoEles = rightEles.get(0).getElementsByAttributeValue("class", "info");
				if (infoEles != null && infoEles.size() > 0) {
					Elements scoreEles = infoEles.get(0).getElementsByAttributeValue("class", "biz-level");
					if (scoreEles != null && scoreEles.size() > 0) {
						shopScore = scoreEles.get(0).text();
						shopFlags++;
					}
					Elements typeEles = infoEles.get(0).getElementsByAttributeValue("class", "tag");
					if (typeEles != null && typeEles.size() > 0) {
						shopType = typeEles.get(0).text();
						shopFlags++;
					}
				}
				// counts部分
				Elements countEles = rightEles.get(0).getElementsByAttributeValue("class", "counts");
				if (countEles != null && countEles.size() > 0) {
					Elements consumerNumEle = countEles.get(0).getElementsByAttributeValue("class", "num");
					if (consumerNumEle != null && consumerNumEle.size() > 0) {
						{
							shopConsumerNum = consumerNumEle.get(0).text();
							if(DateFormatTools.isNumeric(shopConsumerNum))
								shopFlags++;
						}
					}
					Elements CommentNumEles = countEles.get(0).getElementsByAttributeValue("class", "num rate-count");
					if (CommentNumEles != null && CommentNumEles.size() > 0) {
						{
							shopCommentNum = CommentNumEles.get(0).text();
							if(DateFormatTools.isNumeric(shopCommentNum))
								shopFlags++;
						}
					}
				}
			}
		}
		// Elements rateDetailEles =
		// body.getElementsByAttributeValue("class","rate-detail");//
		// "rate-labels-wrapper J-labels-wrapper");
		// if (rateDetailEles != null && rateDetailEles.size() > 0) {
		// Elements labelsEles =
		// rateDetailEles.get(0).getElementsByAttributeValue("class", "labels");
		// if (labelsEles != null && labelsEles.size() > 0) {
		// Elements tagsELes = labelsEles.get(0).getElementsByTag("a");
		// for (Element ele : tagsELes) {
		// String tag = ele.text();
		// }
		// }
		// }
		LOG.info(shopCount+++ ":" + shopid + "\t" + shopName + "\t" + shopAddress + "\t" + shopScore + "\t" + shopPhone
				+ "\t" + shopType + "\t" + shopConsumerNum + "\t" + shopCommentNum);
//		if (shopFlags >= 4) 
//		{
			shop = new Shop();
			shop.setLid(location.getId());
			shop.setSid(shopid);
			shop.setName(shopName);
			shop.setType(shopType);
			shop.setSys_score(shopScore);
			shop.setCreatetime(new Date());
			shop.setUpdatetime(new Date());
			shop.setComments(shopCommentNum.trim().equals("")?0:Integer.valueOf(shopCommentNum));
			shop.setConsumers(shopConsumerNum.trim().equals("")?0:Integer.valueOf(shopConsumerNum));
			shop.setPhone(shopPhone);
			if(shop.getComments()>0)
				shop.setStatus(1);
			else
				shop.setStatus(0);
			shopService.save(shop);
//		}
//		else {
//			Crawlshop cshop = crawlshopService.getCrawlshopBySid(shopid);
//			if (cshop != null) {
//				cshop.setStatus(0);
//				cshop.setUpdatetime(new Date());
//				crawlshopService.modify(cshop);
//			}
//		}
		
	}

	// 解析评论html
	public static void parserCommentHtml(String html) {
		Document doc = Jsoup.parseBodyFragment(html);
		Element body = doc.body();

		int customerFlags = 0;
		int commentFlags = 0;
		// name 与 等级
		String cid = "", portrait = "", name = "", level = "", star = "", comment_time = "", content = "";
		Elements eles = body.getElementsByAttributeValue("class", "J-ratelist-item rate-list__item cf");
		for (Element ele : eles) {
			Customer customer = null;
			Comment comment = null;
			if (ele.hasAttr("data-rateid")) {
				cid = ele.attr("data-rateid");
				customerFlags++;
			}
			// 评价人头像
			Elements portraitEles = ele.getElementsByAttributeValue("class", "avatar");
			if (portraitEles != null && portraitEles.size() > 0) {
				portrait = portraitEles.get(0).attr("src");
				customerFlags++;
			}
			// 评价人名字与用户等级
			Elements nameEles = ele.getElementsByAttributeValue("class", "name-wrapper");
			for (Element nameEle : nameEles) {
				if (nameEle.attr("class").equals("name-wrapper")) {
					Elements nameAndLevelEles = nameEle.getElementsByTag("span");
					if (nameAndLevelEles.size() >= 1) {
						name = nameAndLevelEles.get(0).text();
						customerFlags++;
					}
					if (nameAndLevelEles.size() >= 2) {
						Element levelEle = nameAndLevelEles.get(1).getElementsByTag("i").get(0);
						level = levelEle.attr("title");
						customerFlags++;
					}
				}
			}
			if (cid != null && !cid.trim().equals("") && customerFlags >= 4) {
				if (customerService.getCustomerByCid(Long.valueOf(cid)) == null) {
					customer = new Customer();
					customer.setCid(Long.valueOf(cid));
					if (portrait != null)
						customer.setPortrait(portrait);
					if (name != null)
						customer.setName(name);
					if (level != null)
						customer.setLevel(level);
					customer.setCreatetime(new Date());
					customer.setUpdatetime(new Date());
					customerService.save(customer);
				}
			} else {
				return;
			}
			// 评价内容
			Elements contentEles = ele.getElementsByAttributeValue("class", "content");
			if (contentEles != null && contentEles.size() > 0) {
				content = contentEles.get(0).text();
				commentFlags++;
			}
			// 评价等级与时间review-content-wrapper
			Elements reviewEles = ele.getElementsByAttributeValue("class", "info cf");
			if (reviewEles != null && reviewEles.size() > 0) {
				Elements starEles = reviewEles.get(0).getElementsByAttributeValue("class", "rate-stars");
				if (starEles != null && starEles.size() > 0) {
					Element starEle = starEles.get(0);
					if (starEle.hasAttr("style")) {
						star = starEle.attr("style");
						star = star.substring(star.indexOf(":") + 1, star.lastIndexOf("%"));
						star = Integer.valueOf(star) / 20 + "";
						commentFlags++;
					}
				}

				Elements commentTimeEles = reviewEles.get(0).getElementsByAttributeValue("class", "time");
				if (commentTimeEles != null && commentTimeEles.size() > 0) {
					Element commentTimeEle = commentTimeEles.get(0);
					comment_time = commentTimeEle.text();
					commentFlags++;
				}
			}
			if (commentFlags >= 3) {
				comment = new Comment();
				comment.setCid(Long.valueOf(cid));
				if (shop != null) {
					comment.setSid(shop.getSid());
					comment.setLid(shop.getLid());
				}
				if (content != null)
					comment.setContent(content);
				if (star != null)
					comment.setScore(star);
				if (comment_time != null)
					comment.setComment_time(
							DateFormatTools.getDateFromString(comment_time, DateFormatTools.WX_FORMATE_9));
				comment.setCreatetime(new Date());
				comment.setUpdatetime(new Date());
				commentService.save(comment);
				//LOG.info("历史商家总评论数" + commentPages++ + ":" + comment.getContent());
				LOG.info("当前商家数：" + shopCount + ",当前商家:"+shop.getSid()+",当前商家总评论数" + shopCommentPages++ + ":" + comment.getContent());
			}
		}
	}
}
