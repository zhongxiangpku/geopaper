package cn.edu.hfut.dmic.webcollector.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import cn.edu.hfut.dmic.webcollector.entity.Customer;
import cn.edu.hfut.dmic.webcollector.service.ICustomerService;

@Service
public class CustomerService extends BaseServiceImpl implements ICustomerService
{
	@Override
	public Customer getCustomerById(Long id) {
		return dao.get(Customer.class,id);
	}
	
	@Override
	public void delete(Long id) {
		dao.delete(Customer.class,id);
	}

	@Override
	public long save(Customer entity) {
		return dao.save(entity);
	}

	@Override
	public void modify(Customer entity) {
		dao.update(entity);
	}

	@Override
	public List<Customer> getCustomerList() {
		String hql = "FROM Customer";
		Object [] params = new Object[]{};

		List<Customer> lists = dao.getSQLList(hql, params, -1);
		if(lists != null && lists.size()>0)
			return lists;
		return null;
	}

	@Override
	public Customer getCustomerByCid(Long cid) {
			String hql = "FROM Customer WHERE cid=?";
			Object [] params = new Object[]{cid};
			List<Customer> lists = dao.getSQLList(hql, params, -1);
			if(lists != null && lists.size()>0)
				return lists.get(0);
			return null;
	}
}
