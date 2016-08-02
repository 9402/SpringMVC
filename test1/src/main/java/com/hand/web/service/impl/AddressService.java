package com.hand.web.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hand.bean.Address;
import com.hand.dao.AddressMapper;
import com.hand.web.service.IAddressService;

@Service
@Transactional  //�˴����ٽ��д���SqlSession���ύ���񣬶��ѽ���springȥ�����ˡ�
public class AddressService implements IAddressService{
	
	@Resource
	private AddressMapper addressMapper;
	
	public List<Address> selectAddress() {
		return addressMapper.selectAddress();
	}

	public Address selectAddressByID(Short id) {
		return addressMapper.selectAddressByID(id);
	}

}
