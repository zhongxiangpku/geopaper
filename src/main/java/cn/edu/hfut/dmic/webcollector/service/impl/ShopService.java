package cn.edu.hfut.dmic.webcollector.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import cn.edu.hfut.dmic.webcollector.entity.Shop;
import cn.edu.hfut.dmic.webcollector.service.IShopService;


@Service
public class ShopService extends BaseServiceImpl implements IShopService
{
	@Override
	public Shop getShopById(Long id) {
		return dao.get(Shop.class,id);
	}
	
	@Override
	public void delete(Long id) {
		dao.delete(Shop.class,id);
	}

	@Override
	public long save(Shop entity) {
		return dao.save(entity);
	}

	@Override
	public void modify(Shop entity) {
		dao.update(entity);
	}

	@Override
	public List<Shop> getShopList() {
		String hql = "FROM Shop";
		Object [] params = new Object[]{};

		List<Shop> lists = dao.getSQLList(hql, params, -1);
		if(lists != null && lists.size()>0)
			return lists;
		return null;
	}
}
