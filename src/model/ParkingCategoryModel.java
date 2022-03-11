package model;

import java.sql.Timestamp;


public class ParkingCategoryModel {
	
	/* Private variables */
	
	private int parkingCategoryId;
	private String description;
        private String details;
        private int noOfSpace;
        private int noOfFreeSpace;
        private Timestamp createdDate; 
	private Timestamp updatedDate;
        
	
	/**
	 * Gets the parking category
	 * @return int
	 */
	public int getParkingCategoryId() {
		return parkingCategoryId;
	}
	
	/**
	 * Sets the parkingCategoryId
	 * @param parkingCategoryId parkingCategoryId
	 */
	public void setParkingCategoryId(int parkingCategoryId) {
		this.parkingCategoryId = parkingCategoryId;
	}
	
	/**
	 * Gets the description
	 * @return String
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the description 
	 * @param description description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
        /**
	 * gets the number of space
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

        public int getNoOfFreeSpace() {
            return noOfFreeSpace;
        }

        public void setNoOfFreeSpace(int noOfFreeSpace) {
            this.noOfFreeSpace = noOfFreeSpace;
        }
        
        /**
	 * gets the created date
	 * @return Timestamp
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	
	/**
	 * sets the created date
	 * @param createdDate createdDate
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	
	/**
	 * gets updated date
	 * @return Timestamp
	 */
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	
	/**
	 * sets the updated date
	 * @param updatedDate updatedDate
	 */
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
}