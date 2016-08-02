package com.hand.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hand.bean.Customer;

public interface CustomerMapper {
	//userMapper只能传入一个参数,多个的话需要注解
	//value 必须与Bean 属性一致！
//	public Customer SelectCustomer(@Param(value = "first_name") String name,
//			@Param(value = "last_name") String pwd);
	public Customer SelectCustomer(Customer customer);
	
	public Customer SelectCustomerByID(Short id);
	
	public int SelectAllnumber();
	//@Param中的值要与mapper文件对应的参数相一致，后面定义的size接收service传过来的参数
	public List<Customer> selectCustomerPage(@Param("num")int size,@Param("size")int j);
	
	public void addCustomer(Customer cus);
	
	public int delete(Short id);
	
	public int updateCustomer(Customer cus);
	
	
}
