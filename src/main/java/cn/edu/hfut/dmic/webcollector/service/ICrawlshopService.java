package cn.edu.hfut.dmic.webcollector.service;

import java.util.List;

import cn.edu.hfut.dmic.webcollector.entity.Crawlshop;


public interface ICrawlshopService 
{
	public Crawlshop getCrawlshopById(Long id);
	
	public Crawlshop getCrawlshopBySid(Long sid);
	
	public List<Crawlshop> getCrawlshopList(int status);
	
	public long save(Crawlshop entity);
	
	public void modify(Crawlshop entity);
	
	public void delete(Long id);
}
