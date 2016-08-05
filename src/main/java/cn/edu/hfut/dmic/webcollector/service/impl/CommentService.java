package cn.edu.hfut.dmic.webcollector.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import cn.edu.hfut.dmic.webcollector.entity.Comment;
import cn.edu.hfut.dmic.webcollector.service.ICommentService;

@Service
public class CommentService extends BaseServiceImpl implements ICommentService
{
	@Override
	public Comment getCommentById(Long id) {
		return dao.get(Comment.class,id);
	}
	
	@Override
	public void delete(Long id) {
		dao.delete(Comment.class,id);
	}

	@Override
	public long save(Comment entity) {
		return dao.save(entity);
	}

	@Override
	public void modify(Comment entity) {
		dao.update(entity);
	}

	@Override
	public List<Comment> getCommentList() {
		String hql = "FROM Comment";
		Object [] params = new Object[]{};

		List<Comment> lists = dao.getSQLList(hql, params, -1);
		if(lists != null && lists.size()>0)
			return lists;
		return null;
	}
}
