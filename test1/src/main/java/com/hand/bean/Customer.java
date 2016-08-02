package com.hand.bean;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
public class Customer implements Serializable{
	private short customer_id;
	private byte store_id;
	private String first_name;
	private String last_name;
	private String email;
	private Address address_id;
	private boolean active;
	private Timestamp create_date;
	private Timestamp last_update;

	@Override
	public String toString() {
		return "Customer [customer_id=" + customer_id + ", store_id="
				+ store_id + ", first_name=" + first_name + ", last_name="
				+ last_name + ", email=" + email + ", address_id=" + address_id
				+ ", active=" + active + ", create_date=" + create_date
				+ ", last_update=" + last_update + "]";
	}
	
	
	public Customer(String first_name, String last_name) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
	}

	public short getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(short customer_id) {
		this.customer_id = customer_id;
	}

	public byte getStore_id() {
		return store_id;
	}

	public void setStore_id(byte store_id) {
		this.store_id = store_id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Address getAddress_id() {
		return address_id;
	}

	public void setAddress_id(Address address_id) {
		this.address_id = address_id;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}


	public Timestamp getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Timestamp create_date) {
		this.create_date = create_date;
	}

	public Timestamp getLast_update() {
		return last_update;
	}

	public void setLast_update(Timestamp last_update) {
		this.last_update = last_update;
	}

	public Customer() {

	}

}
