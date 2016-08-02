package com.hand.dao;

import java.util.List;

import com.hand.bean.Address;

public interface AddressMapper {
	public List<Address> selectAddress();
	
	public Address selectAddressByID(Short id);
}
