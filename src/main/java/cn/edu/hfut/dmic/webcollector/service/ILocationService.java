package cn.edu.hfut.dmic.webcollector.service;

import java.util.List;

import cn.edu.hfut.dmic.webcollector.entity.Location;


public interface ILocationService 
{
	public Location getLocationById(Long id);
	
	public List<Location> getLocationList();
	
	public long save(Location entity);
	
	public void modify(Location entity);
	
	public void delete(Long id);
}
