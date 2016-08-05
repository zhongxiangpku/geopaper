package cn.edu.hfut.dmic.webcollector.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import cn.edu.hfut.dmic.webcollector.entity.Tag;
import cn.edu.hfut.dmic.webcollector.service.ITagService;


@Service
public class TagService extends BaseServiceImpl implements ITagService
{
	@Override
	public Tag getTagById(Long id) {
		return dao.get(Tag.class,id);
	}
	
	@Override
	public void delete(Long id) {
		dao.delete(Tag.class,id);
	}

	@Override
	public long save(Tag entity) {
		return dao.save(entity);
	}

	@Override
	public void modify(Tag entity) {
		dao.update(entity);
	}

	@Override
	public List<Tag> getTagList() {
		String hql = "FROM Tag";
		Object [] params = new Object[]{};

		List<Tag> lists = dao.getSQLList(hql, params, -1);
		if(lists != null && lists.size()>0)
			return lists;
		return null;
	}
}
