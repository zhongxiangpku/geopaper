package cn.edu.hfut.dmic.webcollector.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import cn.edu.hfut.dmic.webcollector.entity.Crawlshop;
import cn.edu.hfut.dmic.webcollector.service.ICrawlshopService;


@Service
public class CrawlshopService extends BaseServiceImpl implements ICrawlshopService
{
	@Override
	public Crawlshop getCrawlshopById(Long id) {
		return dao.get(Crawlshop.class,id);
	}
	
	@Override
	public void delete(Long id) {
		dao.delete(Crawlshop.class,id);
	}

	@Override
	public long save(Crawlshop entity) {
		return dao.save(entity);
	}

	@Override
	public void modify(Crawlshop entity) {
		dao.update(entity);
	}

	@Override
	public List<Crawlshop> getCrawlshopList(int status) {
		String hql = "FROM Crawlshop  WHERE status=? order by createtime desc";
		Object [] params = new Object[]{status};

		List<Crawlshop> lists = dao.getSQLList(hql, params, -1);
		if(lists != null && lists.size()>0)
			return lists;
		return null;
	}

	@Override
	public Crawlshop getCrawlshopBySid(Long sid) {
		String hql = "FROM Crawlshop WHERE shopid=?";
		Object [] params = new Object[]{sid};
		List<Crawlshop> lists = dao.getSQLList(hql, params, -1);
		if(lists != null && lists.size()>0)
			return lists.get(0);
		return null;
	}
}
