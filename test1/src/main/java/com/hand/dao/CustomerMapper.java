package com.hand.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.bean.Customer;

public interface CustomerMapper {
	//userMapperֻ�ܴ���һ������,����Ļ���Ҫע��
	//value ������Bean ����һ�£�
//	public Customer SelectCustomer(@Param(value = "first_name") String name,
//			@Param(value = "last_name") String pwd);
	public Customer SelectCustomer(Customer customer);
	
	public Customer SelectCustomerByID(Short id);
	
	public int SelectAllnumber();
	//@Param�е�ֵҪ��mapper�ļ���Ӧ�Ĳ�����һ�£����涨���size����service�������Ĳ���
	public List<Customer> selectCustomerPage(@Param("num")int size,@Param("size")int j);
	
	public void addCustomer(Customer cus);
	
	public int delete(Short id);
	
	public int updateCustomer(Customer cus);
	
	
}
