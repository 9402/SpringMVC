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
	
	//��֤��¼��Ϣ��ģ��
	@RequestMapping("/tologin")
	public String toLogin(Customer customer,HttpServletRequest request){
		String name = request.getParameter("first_name");
		String pwd = request.getParameter("last_name");
		System.out.println("name=="+name+ " ,"+"pwd=="+pwd);
		if(customerService.SelectCustomer(customer)!=null){
			System.out.println("login1");
			//���õ�¼�û���ŵ�session�����У���ǰ̨ʹ��EL���ʽȡ��
			request.getSession().setAttribute("first_name", customer.getFirst_name());
			return "redirect:/hand/toall";
		}else {
			return "/login2";
		}
	}
	
	//��ȡ�������ݵ�ģ��
	@RequestMapping("/toall")
	public String toAll(Customer customer,HttpServletRequest request,
			Map<String,Object> map){
		map.put("customer", customer);
		return "/customer";
	}
	
	//����û���Ϣ��ģ��
	@RequestMapping("/toadd")
	public String addCustomer(Customer customer){
//		//����һ��ʱ���������¼��ǰʱ��
		customer.setCreate_date(new Timestamp(System.currentTimeMillis()));
		customer.setLast_update(new Timestamp(System.currentTimeMillis()));
		customer.setStore_id((byte)1);
		customerService.addCustomer(customer);
		return "redirect:/hand/toall";
	}
	
	//ɾ���û���Ϣ��ģ�飬����@ResponseBody��ʾ�÷����ķ��ؽ��ֱ��д��HTTP response body��
	@RequestMapping("/todel")
	@ResponseBody
	public void deletec(short customer_id){
		Customer customer = new Customer();
		customer.setCustomer_id(customer_id);
		customerService.delete(customer.getCustomer_id());
		
	}

	//�޸��û���Ϣ��ģ��
	@RequestMapping("/toupdate")
	public String updateCustomer(Customer customer,HttpServletRequest request){
		//���ݵ�ǰcustomer��ȡid����ȡ����֮ǰ������
		Customer cus = customerService.SelectCustomerByID(customer.getCustomer_id());
		cus.setFirst_name(customer.getFirst_name());
		cus.setLast_name(customer.getLast_name());
		cus.setEmail(customer.getEmail());
		cus.setAddress_id(addressService.selectAddressByID(customer.getAddress_id().getAddress_id()));
		//���ø���ʱ���ʱ������ɼ�ʱ��ʾ��ǰ���µ�ʱ��
		cus.setLast_update(new Timestamp(System.currentTimeMillis()));
		customerService.updateCustomer(cus);
		return "redirect:/hand/toall";
	}
	
	//��ѯ���е�ַ��Ϣģ��
	@RequestMapping("/toAllAddress")
	public String selectAllAddress(ArrayList<Address> addressList,HttpServletRequest request){
		addressList = (ArrayList<Address>) addressService.selectAddress();
		
		request.setAttribute("list", addressList);		
		return "/newcustomer";	
	}
	
	//ʵ�ֵ�ַģ���ǰ̨ajax����ģ��
	@RequestMapping("/toselectAllAddressAjax")
	@ResponseBody
	public ArrayList<Address> selectAllAddress2(ArrayList<Address> addressList){
		addressList = (ArrayList<Address>) addressService.selectAddress();
		return addressList;	
	}
	
	//������ʵ�ַ�ҳģ��
	@RequestMapping("/toajaxPaget")
	@ResponseBody
	public Map<String, Object> ajaxPaget(HttpServletRequest request) {
		Map<String, Object> map=new HashMap<String, Object>();
		ArrayList<Customer> customerList = new ArrayList<Customer>(); 
		//��ǰ̨��ȡ��ǰ���ڵ�ҳ��
		int page = Integer.parseInt(request.getParameter("page"));
		 customerList = (ArrayList<Customer>) customerService.selectCustomerPage(page, 20);
		 //�O��ÿҳ�ж��������ݣ������Ҫ��̬ʵ�֣���Ҫ��ǰ̨дһ���ı���
		 //��̨ʹ��һ���������棬ͨ���ı�����������ǰÿҳ������
		 int size = 20;
		 //��ȡ���ݿ��ܹ�������
		 int num = customerService.SelectAllnumber();
		 //��������ж���ҳ
		 int totalPage;
		 if((num%size)==0){
			totalPage=num/size;
		 }else{
			totalPage=(num/size)+1;
		 }
		 map.put("customerList", customerList);
		 map.put("page", page);
		 map.put("totalPage", totalPage);
		 /*//���Դ���
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
