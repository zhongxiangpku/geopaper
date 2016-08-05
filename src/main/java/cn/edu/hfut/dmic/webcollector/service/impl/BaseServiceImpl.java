package cn.edu.hfut.dmic.webcollector.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.hfut.dmic.webcollector.dao.IMasterCommonDAO;


public class BaseServiceImpl
{
	@Autowired
	protected IMasterCommonDAO dao;
}
