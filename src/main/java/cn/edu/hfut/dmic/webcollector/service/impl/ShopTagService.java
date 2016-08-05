package cn.edu.hfut.dmic.webcollector.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import cn.edu.hfut.dmic.webcollector.entity.ShopTag;
import cn.edu.hfut.dmic.webcollector.service.IShopTagService;


@Service
public class ShopTagService extends BaseServiceImpl implements IShopTagService
{
	@Override
	public ShopTag getShopTagById(Long id) {
		return dao.get(ShopTag.class,id);
	}
	
	@Override
	public void delete(Long id) {
		dao.delete(ShopTag.class,id);
	}

	@Override
	public long save(ShopTag entity) {
		return dao.save(entity);
	}

	@Override
	public void modify(ShopTag entity) {
		dao.update(entity);
	}

	@Override
	public List<ShopTag> getShopTagList() {
		String hql = "FROM ShopTag";
		Object [] params = new Object[]{};

		List<ShopTag> lists = dao.getSQLList(hql, params, -1);
		if(lists != null && lists.size()>0)
			return lists;
		return null;
	}
}
