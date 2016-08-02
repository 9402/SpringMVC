package com.hand.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.bean.Customer;
import com.hand.dao.CustomerMapper;
import com.hand.web.service.ICustomerService;

//注意使用注解方式
@Service
@Transactional  //此处不再进行创建SqlSession和提交事务，都已交由spring去管理了。
public class CustomerService implements ICustomerService{
	@Resource
	private CustomerMapper customerMapper;
	
	public Customer SelectCustomer(Customer customer) {
		//由于在mybatis中，没有具体dao层实现类，具体的逻辑就写在service中
		Customer cus;
		if(customerMapper.SelectCustomer(customer)==null){
			cus=null;
		}else {
			cus=customerMapper.SelectCustomer(customer);
		}
		return cus;
	}

	public Customer SelectCustomerByID(Short id) {
		return customerMapper.SelectCustomerByID(id);
	}

	public int SelectAllnumber() {
		return customerMapper.SelectAllnumber();
	}

	public List<Customer> selectCustomerPage(int page, int j) {
		int size = (page-1)*j;
		return customerMapper.selectCustomerPage(size, j);
	}

	public void addCustomer(Customer cus) {
		customerMapper.addCustomer(cus);
		
	}

	public int delete(Short id) {
		return customerMapper.delete(id);
	}

	public int updateCustomer(Customer cus) {
		return customerMapper.updateCustomer(cus);
	}

	

}
