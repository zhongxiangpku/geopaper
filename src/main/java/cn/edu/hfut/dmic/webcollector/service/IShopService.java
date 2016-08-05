package cn.edu.hfut.dmic.webcollector.service;

import java.util.List;

import cn.edu.hfut.dmic.webcollector.entity.Shop;


public interface IShopService 
{
	public Shop getShopById(Long id);
	
	public List<Shop> getShopList();
	
	public long save(Shop entity);
	
	public void modify(Shop entity);
	
	public void delete(Long id);
}
