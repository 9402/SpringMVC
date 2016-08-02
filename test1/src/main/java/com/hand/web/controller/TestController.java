package com.hand.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hand.bean.Address;
import com.hand.bean.Customer;
import com.hand.web.service.IAddressService;
import com.hand.web.service.ICustomerService;


@Controller
@RequestMapping("/hand")
public class TestController {
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private IAddressService addressService;
	
	//验证登录信息的模块
	@RequestMapping("/tologin")
	public String toLogin(Customer customer,HttpServletRequest request){
		String name = request.getParameter("first_name");
		String pwd = request.getParameter("last_name");
		System.out.println("name=="+name+ " ,"+"pwd=="+pwd);
		if(customerService.SelectCustomer(customer)!=null){
			System.out.println("login1");
			//设置登录用户存放到session缓存中，在前台使用EL表达式取出
			request.getSession().setAttribute("first_name", customer.getFirst_name());
			return "redirect:/hand/toall";
		}else {
			return "/login2";
		}
	}
	
	//获取所有数据的模块
	@RequestMapping("/toall")
	public String toAll(Customer customer,HttpServletRequest request,
			Map<String,Object> map){
		map.put("customer", customer);
		return "/customer";
	}
	
	//添加用户信息的模块
	@RequestMapping("/toadd")
	public String addCustomer(Customer customer){
//		//定义一个时间戳，来记录当前时间
		customer.setCreate_date(new Timestamp(System.currentTimeMillis()));
		customer.setLast_update(new Timestamp(System.currentTimeMillis()));
		customer.setStore_id((byte)1);
		customerService.addCustomer(customer);
		return "redirect:/hand/toall";
	}
	
	//删除用户信息的模块，其中@ResponseBody表示该方法的返回结果直接写入HTTP response body中
	@RequestMapping("/todel")
	@ResponseBody
	public void deletec(short customer_id){
		Customer customer = new Customer();
		customer.setCustomer_id(customer_id);
		customerService.delete(customer.getCustomer_id());
		
	}

	//修改用户信息的模块
	@RequestMapping("/toupdate")
	public String updateCustomer(Customer customer,HttpServletRequest request){
		//根据当前customer获取id来获取更新之前的数据
		Customer cus = customerService.SelectCustomerByID(customer.getCustomer_id());
		cus.setFirst_name(customer.getFirst_name());
		cus.setLast_name(customer.getLast_name());
		cus.setEmail(customer.getEmail());
		cus.setAddress_id(addressService.selectAddressByID(customer.getAddress_id().getAddress_id()));
		//设置更新时间的时间戳，可及时显示当前更新的时间
		cus.setLast_update(new Timestamp(System.currentTimeMillis()));
		customerService.updateCustomer(cus);
		return "redirect:/hand/toall";
	}
	
	//查询所有地址信息模块
	@RequestMapping("/toAllAddress")
	public String selectAllAddress(ArrayList<Address> addressList,HttpServletRequest request){
		addressList = (ArrayList<Address>) addressService.selectAddress();
		
		request.setAttribute("list", addressList);		
		return "/newcustomer";	
	}
	
	//实现地址模块对前台ajax交互模块
	@RequestMapping("/toselectAllAddressAjax")
	@ResponseBody
	public ArrayList<Address> selectAllAddress2(ArrayList<Address> addressList){
		addressList = (ArrayList<Address>) addressService.selectAddress();
		return addressList;	
	}
	
	//主界面实现分页模块
	@RequestMapping("/toajaxPaget")
	@ResponseBody
	public Map<String, Object> ajaxPaget(HttpServletRequest request) {
		Map<String, Object> map=new HashMap<String, Object>();
		ArrayList<Customer> customerList = new ArrayList<Customer>(); 
		//从前台获取当前所在的页数
		int page = Integer.parseInt(request.getParameter("page"));
		 customerList = (ArrayList<Customer>) customerService.selectCustomerPage(page, 20);
		 //設置每页有多少条数据，如果需要动态实现，需要在前台写一个文本框，
		 //后台使用一个参数代替，通过文本框来操作当前每页的数据
		 int size = 20;
		 //获取数据库总共的数据
		 int num = customerService.SelectAllnumber();
		 //计算出共有多少页
		 int totalPage;
		 if((num%size)==0){
			totalPage=num/size;
		 }else{
			totalPage=(num/size)+1;
		 }
		 map.put("customerList", customerList);
		 map.put("page", page);
		 map.put("totalPage", totalPage);
		 /*//测试代码
		 for(int i=0;i<customerList.size();i++){
	    		System.out.println(customerList.get(i).getCustomer_id());
	    	}
	    	ObjectMapper mapper=new ObjectMapper();
	        try {
	            String jsonString=mapper.writeValueAsString(customerList);
	            System.out.print(jsonString);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } 
	        */
		 return map;
	}
}
