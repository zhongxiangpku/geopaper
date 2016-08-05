package cn.edu.hfut.dmic.webcollector.service;

import java.util.List;

import cn.edu.hfut.dmic.webcollector.entity.Tag;


public interface ITagService 
{
	public Tag getTagById(Long id);
	
	public List<Tag> getTagList();
	
	public long save(Tag entity);
	
	public void modify(Tag entity);
	
	public void delete(Long id);
}
