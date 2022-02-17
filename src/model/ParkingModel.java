package model;

import java.util.Date;

//Model de Réservations

public class ParkingModel {
	
	/* Private variables */
	private int parkingLotId;
	private int userId;
	private String userName;
	private Date fromDate;
	private Date toDate;
	private int price;
	private String description;
	private String categoryDescription;
	private int noOfSpace;
        private int currentPlace;
	
	/**
	 * gets the user Name
	 * @return String
	 */
	public String getUserName(){
		return this.userName;
	}
	
	/**
	 * sets the user name
	 * @param name name
	 */
	public void setUserName(String name){
		this.userName = name;
	}
	
	/**
	 * gets the price 
	 * @return int
	 */
	public int getPrice() {
		return price;
	}
	
	/**
	 * Sets the price
	 * @param price price
	 */
	public void setPrice(int price) {
		this.price = price;
	}
	
	/**
	 * gets the parking lot id
	 * @return int
	 */
	public int getParkingLotId() {
		return parkingLotId;
	}
	
	/**
	 * set the parking lot id
	 * @param parkingLotId parkingLotId
	 */
	public void setParkingLotId(int parkingLotId) {
		this.parkingLotId = parkingLotId;
	}
	
	/**
	 * gets the user id
	 * @return int
	 */
	public int getUserId() {
		return userId;
	}
	
	/**
	 * sets the user id
	 * @param userId userid
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	/**
	 * gets from date
	 * @return Date
	 */
	public Date getFromDate() {
		return fromDate;
	}
	
	/**
	 * Sets the from date
	 * @param fromDate fromDate
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	
	/**
	 * gets to date
	 * @return date
	 */
	public Date getToDate() {
		return toDate;
	}
	
	/**
	 * Sets to date
	 * @param toDate toDate
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	
	/**
	 * gets description
	 * @return String
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * sets the description
	 * @param description description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/** 
	 * gets the category description
	 * @return String
	 */
	public String getCategoryDescription() {
		return categoryDescription;
	}
	
	/**
	 * Sets category description
	 * @param categoryDescription categoryDescription
	 */
	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}
	
	/**
	 * gets the no of space
	 * @return int
	 */
	public int getNoOfSpace() {
		return noOfSpace;
	}
	
	/**
	 * sets the no of space 
	 * @param noOfSpace noOfSpace
	 */
	public void setNoOfSpace(int noOfSpace) {
		this.noOfSpace = noOfSpace;
	}
	
        public int getCurrentPlace(){
            return currentPlace;
        }
        
        public void setCurrentPlace(int currentPlace){
            this.currentPlace = currentPlace;
        }
	
}