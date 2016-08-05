package cn.edu.hfut.dmic.webcollector.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import cn.edu.hfut.dmic.webcollector.entity.Location;
import cn.edu.hfut.dmic.webcollector.service.ILocationService;


@Service
public class LocationService extends BaseServiceImpl implements ILocationService
{
	@Override
	public Location getLocationById(Long id) {
		return dao.get(Location.class,id);
	}
	
	@Override
	public void delete(Long id) {
		dao.delete(Location.class,id);
	}

	@Override
	public long save(Location entity) {
		return dao.save(entity);
	}

	@Override
	public void modify(Location entity) {
		dao.update(entity);
	}

	@Override
	public List<Location> getLocationList() {
		String hql = "FROM Location";
		Object [] params = new Object[]{};

		List<Location> lists = dao.getSQLList(hql, params, -1);
		if(lists != null && lists.size()>0)
			return lists;
		return null;
	}
}
