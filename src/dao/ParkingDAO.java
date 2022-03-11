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
                        insertCategory.setTimestamp(4, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
                        insertCategory.setTimestamp(5, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
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
	 * GetParkingCategoryWithFreeSpace
	 * @return List of ParkingCategoryModel
	 */
	public List<ParkingCategoryModel> GetParkingCategoryWithFreeSpace() {
		
		List<ParkingCategoryModel> category = new ArrayList<ParkingCategoryModel>();
		
		
		try {
			CreateConnection();
			PreparedStatement getAllCategory = PrepareStatement(Constants.GET_PARKING_CATEGORY_WITH_FREE_SPACE);
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
			//(PARKINGLOTID,PARKINGCATEGORYID,STATUS,CREATEDDATE,UPDATEDDATE)
                        insertParkingLot.setInt(1, parking.getParkingLotId());
			insertParkingLot.setInt(2, parking.getParkingLotCategoryId());
                        insertParkingLot.setString(3, parking.getStatus());
			insertParkingLot.setTimestamp(4, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
			insertParkingLot.setTimestamp(5, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
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
	 * DeleteParkingLot
	 * @param parkingCategoryModel parkingCategory
	 * @return boolean
	 */
	public boolean DeleteParkingLot(int parkingCategoryID){
		try {
			CreateConnection();
			PreparedStatement deleteParkingLot = PrepareStatement(Constants.DELETE_PARKING_LOTS);
			deleteParkingLot.setInt(1, parkingCategoryID);
			int result = deleteParkingLot.executeUpdate();
			return (result > 0) ? true : false;

		} catch (SQLException e) {
			if (e.getMessage().equalsIgnoreCase("Too many connections"))
				System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		} catch (Exception e) {
			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		}
		return false;
	}
        
        public boolean DeleteParkingLot(int parkingCategoryID, int parkingLotID){
		try {
			CreateConnection();
			PreparedStatement deleteParkingLot = PrepareStatement(Constants.DELETE_PARKING_LOT);
			deleteParkingLot.setInt(1, parkingCategoryID);
                        deleteParkingLot.setInt(2, parkingLotID);

			int result = deleteParkingLot.executeUpdate();
			return (result > 0) ? true : false;

		} catch (SQLException e) {
			if (e.getMessage().equalsIgnoreCase("Too many connections"))
				System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		} catch (Exception e) {
			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		}
		return false;
	}
	
        public boolean DeleteParkingLog(int bookID){
		try {
			CreateConnection();
			PreparedStatement deleteParkingLog = PrepareStatement(Constants.DELETE_PARKING_LOG);
			deleteParkingLog.setInt(1, bookID);

			int result = deleteParkingLog.executeUpdate();
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
	 * GetParkingLotId
	 * @param Int categoryId
	 * @return int
	 */
	public int GetParkingLotId(int categoryId){
		try {
			CreateConnection();
			PreparedStatement getParkigLotId = PrepareStatement(Constants.GET_PARKINGLOT_ID);
			getParkigLotId.setInt(1, categoryId);
			ResultSet result = getParkigLotId.executeQuery();
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
	 * AddParkingBooking
	 * @param parking parking
	 * @return int
	 */
	public int AddParkingBooking(ParkingModel parking){
		try {
			CreateConnection();
			PreparedStatement insertParkingLot = PrepareStatement(Constants.INSERT_PARKING_LOG);
			//PARKINGCATEGORYID,PARKINGLOTID,USERID,FROMDATE,TODATE,STATUS,PRICE
                        insertParkingLot.setInt(1, parking.getParkingLotCategoryId());
			insertParkingLot.setInt(2, parking.getParkingLotId());
			insertParkingLot.setInt(3, parking.getUserId());
			insertParkingLot.setTimestamp(4, parking.getFromDate());
			insertParkingLot.setTimestamp(5, parking.getToDate());
                        insertParkingLot.setString(6, parking.getStatus());
			insertParkingLot.setInt(7, parking.getPrice());
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

        @Override
        public boolean UpdateParkingLotStatus(int parkingLotID,int parkingLotCategoryID) {
                try {
			CreateConnection();
			PreparedStatement updateParkingLotStatus = PrepareStatement(Constants.UPDATE_PARKINGLOT_STATUS);
			updateParkingLotStatus.setInt(1, parkingLotID);
                        updateParkingLotStatus.setInt(2, parkingLotCategoryID);

			int result = updateParkingLotStatus.executeUpdate();
			return (result > 0) ? true : false;

		} catch (SQLException e) {
			if (e.getMessage().equalsIgnoreCase("Too many connections"))
				System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		} catch (Exception e) {
			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		}
		return false;
        }
		
        public boolean CheckParkingLotStatus(ParkingModel parkingModel){
            try {
                    CreateConnection();
                    PreparedStatement getParkingLotStatus = PrepareStatement(Constants.GET_PARKINGLOT_STATUS);
                    getParkingLotStatus.setInt(1, parkingModel.getParkingLotCategoryId());
                    getParkingLotStatus.setInt(2, parkingModel.getParkingLotId());
                    getParkingLotStatus.setTimestamp(3, parkingModel.getFromDate());
                    getParkingLotStatus.setTimestamp(4, parkingModel.getToDate());

                    ResultSet result = getParkingLotStatus.executeQuery();
                    if (result.next())
                        return false;
                    
		} catch (SQLException e) {
			if (e.getMessage().equalsIgnoreCase("Too many connections"))
				System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		} catch (Exception e) {
			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		}
            return true;
        }

    @Override
    public boolean UpdateParkingLogStatus(int bookID) {
            try {
                CreateConnection();
                PreparedStatement updateParkingLogStatus = PrepareStatement(Constants.UPDATE_PARKINGLOG_STATUS);
                updateParkingLogStatus.setInt(1, bookID);

                int result = updateParkingLogStatus.executeUpdate();
                return (result > 0) ? true : false;

            } catch (SQLException e) {
                    if (e.getMessage().equalsIgnoreCase("Too many connections"))
                            System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
            } catch (Exception e) {
                    System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
            }
            return false;
    }
}