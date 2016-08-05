package cn.edu.hfut.dmic.webcollector.service;

import java.util.List;

import cn.edu.hfut.dmic.webcollector.entity.Comment;


public interface ICommentService 
{
	public Comment getCommentById(Long id);
	
	public List<Comment> getCommentList();
	
	public long save(Comment entity);
	
	public void modify(Comment entity);
	
	public void delete(Long id);
}
