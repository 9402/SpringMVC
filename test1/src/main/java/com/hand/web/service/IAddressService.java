package com.hand.web.service;

import java.util.List;

import com.hand.bean.Address;

public interface IAddressService {
	public List<Address> selectAddress();
	
	public Address selectAddressByID(Short id);
}
