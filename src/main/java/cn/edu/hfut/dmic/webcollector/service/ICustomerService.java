package cn.edu.hfut.dmic.webcollector.service;

import java.util.List;

import cn.edu.hfut.dmic.webcollector.entity.Customer;

public interface ICustomerService 
{
	public Customer getCustomerById(Long id);
	
	public Customer getCustomerByCid(Long cid);
	
	public List<Customer> getCustomerList();
	
	public long save(Customer entity);
	
	public void modify(Customer entity);
	
	public void delete(Long id);
}
