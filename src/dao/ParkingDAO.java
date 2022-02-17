package dao;

import model.ParkingModel;
import model.ParkingCategoryModel;
import model.ParkingLot;
import dao.Interface.IParkingDAO;
import common.Constants;
import java.sql.*;
import java.util.*;


public class ParkingDAO extends BaseDAO implements IParkingDAO{

	/**
	 * GetMaxParkingId
	 * @return int
	 */
	public int GetMaxParkingId() {
		try {
			CreateConnection();
			CreateStatement();
			ResultSet rs = ExecuteQuery(Constants.MAX_PARKING_CATEGORY_ID);
			while (rs.next())
				return rs.getInt(1);
		} catch (SQLException e) {
			if (e.getMessage().equalsIgnoreCase("Too many connections"))
				System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		} catch (Exception e) {
			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		}
		return -1;
	}

	/**
	 * AddCategory
	 * @param Category Category
	 * @return int
	 */
	public int AddCategory(ParkingCategoryModel Category){
		try {
			CreateConnection();
			PreparedStatement insertCategory = PrepareStatement(Constants.INSERT_PARKING_CATEGORY);
			insertCategory.setString(1, Category.getDescription());
                        insertCategory.setString(2, Category.getDetails());
                        insertCategory.setInt(3, Category.getNoOfSpace());
                        insertCategory.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));
                        insertCategory.setDate(5, java.sql.Date.valueOf(java.time.LocalDate.now()));
			int result = insertCategory.executeUpdate();
			if (result > 0)
				return GetMaxParkingId();

		} catch (SQLException e) {
			if (e.getMessage().equalsIgnoreCase("Too many connections"))
				System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		} catch (Exception e) {
			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		}
		return -1;
	}

	/**
	 * GetParkingCategory
	 * @return List of ParkingCategoryModel
	 */
	public List<ParkingCategoryModel> GetParkingCategory() {
		
		List<ParkingCategoryModel> category = new ArrayList<ParkingCategoryModel>();
		
		
		try {
			CreateConnection();
			PreparedStatement getAllCategory = PrepareStatement(Constants.GET_PARKING_CATEGORY);
			ResultSet rs = getAllCategory.executeQuery();
			while (rs.next())
				category.add(Map_ResultSet_To_ParkingCategory(rs));
			
		} catch (SQLException e) {
			if (e.getMessage().equalsIgnoreCase("Too many connections"))
				System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		} catch (Exception e) {
			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		}
		return category;
	}
	
	/**
	 * GetCategoryId
	 * @param category category
	 * @return int
	 */
	public int GetCategoryId(String category){
		try {
			CreateConnection();
			PreparedStatement getCategory = PrepareStatement(Constants.GET_CATEGORY_ID);
			getCategory.setString(1, category);
			ResultSet result = getCategory.executeQuery();
			while (result.next())
				return result.getInt(1);

		} catch (SQLException e) {
			if (e.getMessage().equalsIgnoreCase("Too many connections"))
				System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		} catch (Exception e) {
			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		}
		return -1;
	}
	
	/**
	 * AddParking
	 * @param parking parking
	 * @return int
	 */
	public int AddParking(ParkingLot parking){
		try {
			CreateConnection();
			PreparedStatement insertParkingLot = PrepareStatement(Constants.INSERT_PARKING_LOT);
			//(PARKINGCATEGORYID,NOOFSPACE,CURRENTPLACE,STATUS,CREATEDDATE,UPDATEDDATE)
			insertParkingLot.setInt(1, parking.getParkingCategoryId());
                        insertParkingLot.setInt(2, parking.getNoOfSpace());
                        insertParkingLot.setInt(3, 1);
			insertParkingLot.setString(4, parking.getStatus());
			insertParkingLot.setDate(5, java.sql.Date.valueOf(java.time.LocalDate.now()));
			insertParkingLot.setDate(6, java.sql.Date.valueOf(java.time.LocalDate.now()));
			int result = insertParkingLot.executeUpdate();
			if (result > 0)
				return result;

		} catch (SQLException e) {
			if (e.getMessage().equalsIgnoreCase("Too many connections"))
				System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		} catch (Exception e) {
			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		}
		return -1;
	}
//	/**
//	 * GetParkingLot
//	 * @return List of ParkingLot
//	 */
//	public List<ParkingLot> GetParkingLot(){
//		List<ParkingLot> parkingLot = new ArrayList<ParkingLot>();
//		try {
//			CreateConnection();
//			PreparedStatement getAllparkingLot = PrepareStatement(Constants.GET_PARKING_LOT);
//			ResultSet rs = getAllparkingLot.executeQuery();
//			while (rs.next())
//				parkingLot.add(Map_ResultSet_To_ParkingLot(rs));
//		} catch (SQLException e) {
//			if (e.getMessage().equalsIgnoreCase("Too many connections"))
//				System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
//		} catch (Exception e) {
//			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
//		}
//		return parkingLot;
//	}
	
//	/**
//	 * UpdateParkingLot
//	 * @param parkingLot parkingLot
//	 * @return boolean
//	 */
//	public boolean UpdateParkingLot(ParkingLot parkingLot){
//		try {
//			CreateConnection();
//			PreparedStatement updateParkingLot = PrepareStatement(Constants.UPDATE_PARKING_LOT);
//			updateParkingLot.setInt(1, parkingLot.getNoOfSpace());
//			updateParkingLot.setInt(2, parkingLot.getParkingLotId());
//			int result = updateParkingLot.executeUpdate();
//			return (result > 0) ? true : false;
//
//		} catch (SQLException e) {
//			if (e.getMessage().equalsIgnoreCase("Too many connections"))
//				System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
//		} catch (Exception e) {
//			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
//		}
//		return false;
//	}
	
        	/**
	 * UpdateParkingCategory
	 * @param parkingCategoryModel parkingCategory
	 * @return boolean
	 */
	public boolean UpdateParkingCategory(ParkingCategoryModel parkingCategory){
		try {
			CreateConnection();
			PreparedStatement updateParkingCategory = PrepareStatement(Constants.UPDATE_PARKING_CATEGORY);
			updateParkingCategory.setInt(1, parkingCategory.getNoOfSpace());
			updateParkingCategory.setInt(2, parkingCategory.getParkingCategoryId());
			int result = updateParkingCategory.executeUpdate();
			return (result > 0) ? true : false;

		} catch (SQLException e) {
			if (e.getMessage().equalsIgnoreCase("Too many connections"))
				System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		} catch (Exception e) {
			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		}
		return false;
	}
	/**
	 * DeleteParkingCategory
	 * @param parkingCategoryModel parkingCategory
	 * @return boolean
	 */
	public boolean DeleteParkingCategory(ParkingCategoryModel parkingCategory){
		try {
			CreateConnection();
			PreparedStatement deleteParkingCategory = PrepareStatement(Constants.DELETE_PARKING_CATEGORY);
			deleteParkingCategory.setInt(1, parkingCategory.getParkingCategoryId());
			int result = deleteParkingCategory.executeUpdate();
			return (result > 0) ? true : false;

		} catch (SQLException e) {
			if (e.getMessage().equalsIgnoreCase("Too many connections"))
				System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		} catch (Exception e) {
			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		}
		return false;
	}
		
	/**
	 * GetParkingSpaceDetail
	 * @return List of ParkingModel
	 */
	public List<ParkingModel> GetParkingSpaceDetail(){
		List<ParkingModel> parkingLot = new ArrayList<ParkingModel>();
		try {
			CreateConnection();
			PreparedStatement getAllparkingLot = PrepareStatement(Constants.GET_PARKING_SPACE_Detail);
			ResultSet rs = getAllparkingLot.executeQuery();
			while (rs.next())
				parkingLot.add(Map_ResultSet_To_ParkingModel(rs));
		} catch (SQLException e) {
			if (e.getMessage().equalsIgnoreCase("Too many connections"))
				System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		} catch (Exception e) {
			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		}
		return parkingLot;
		
	}
	
	/**
	 * GetParkingLog
	 * @return List of ParkingModel 
	 */
	public List<ParkingModel> GetParkingLog() {
		List<ParkingModel> parkingModel = new ArrayList<ParkingModel>();
		try {
			CreateConnection();
			PreparedStatement getAllCategory = PrepareStatement(Constants.GETPARKINGLOG);
			ResultSet rs = getAllCategory.executeQuery();
			while (rs.next())
				parkingModel.add(Map_ResultSet_To_ParkingModel_Log(rs));
		} catch (SQLException e) {
			if (e.getMessage().equalsIgnoreCase("Too many connections"))
				System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		} catch (Exception e) {
			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		}
		return parkingModel;
	}

	/**
	 * AddParkingBooking
	 * @param parking parking
	 * @return int
	 */
	public int AddParkingBooking(ParkingModel parking){
		try {
			CreateConnection();
			PreparedStatement insertParkingLot = PrepareStatement(Constants.INSERT_PARKING_LOG);
			//PID,PARKINGLOTID,USERID,FROMDATE,TODATE,PRICE
                        insertParkingLot.setInt(1,parking.getCurrentPlace());
			insertParkingLot.setInt(2, parking.getParkingLotId());
			insertParkingLot.setInt(3, parking.getUserId());
			insertParkingLot.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));
			insertParkingLot.setDate(5, new java.sql.Date(parking.getToDate().getTime()));
			insertParkingLot.setInt(6, parking.getPrice());
			int result = insertParkingLot.executeUpdate();
			if (result > 0){
                            return result;
                        }

		} catch (SQLException e) {
			if (e.getMessage().equalsIgnoreCase("Too many connections"))
				System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		} catch (Exception e) {
			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		}
		return -1;
	}
		
}