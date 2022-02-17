package model;

import java.sql.Date;


public class AddressModel {

	/* Variables */
	private int addressId;
	private String line1;
	private String city;
	private int zip;
	private Date createdDate;
	private Date updatedDate;
	
	/**
	 * Gets the address id
	 * @return int
	 */
	public int getAddressId() {
		return addressId;
	}
	
	/**
	 * Sets the address id
	 * @param addressId addressId
	 */
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	
	/**
	 * Gets the Line1 
	 * @return String
	 */
	public String getLine1() {
		return line1;
	}
	
	/**
	 * Sets the Line1
	 * @param line1 line1
	 */
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	
	/**
	 * Gets the city
	 * @return String
	 */
	public String getCity() {
		return city;
	}
	
	/**
	 * Sets the city
	 * @param city city
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * Gets the ziop
	 * @return int
	 */
	public int getZip() {
		return zip;
	}
	
	/**
	 * Sets the zip
	 * @param zip zip
	 */
	public void setZip(int zip) {
		this.zip = zip;
	}
	
	/**
	 * Gets the CreatedDate
	 * @return Date
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	
	/**
	 * Sets the CreatedDate
	 * @param createdDate CreatedDate
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	/**
	 * Gets the UpdatedDate
	 * @return Date
	 */
	public Date getUpdatedDate() {
		return updatedDate;
	}
	
	/**
	 * Sets the UpdatedDate
	 * @param updatedDate UpdatedDate
	 */
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	    
}
