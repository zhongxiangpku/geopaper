package cn.edu.hfut.dmic.webcollector.service;

import java.util.List;

import cn.edu.hfut.dmic.webcollector.entity.ShopTag;



public interface IShopTagService 
{
	public ShopTag getShopTagById(Long id);

	public List<ShopTag> getShopTagList();
	
	public long save(ShopTag entity);
	
	public void modify(ShopTag entity);
	
	public void delete(Long id);
}
