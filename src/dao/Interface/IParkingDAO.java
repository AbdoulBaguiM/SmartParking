package dao.Interface;

import java.util.List;

import model.ParkingCategoryModel;
import model.ParkingLot;
import model.ParkingModel;


public interface IParkingDAO {
	/**
	 * GetMaxParkingId
	 * @return int
	 */
	public int GetMaxParkingId();
	
	/**
	 * AddCategory
	 * @param Category Category
	 * @return int
	 */
	public int AddCategory(ParkingCategoryModel Category);
	
//	/**
//	 * GetParkingCategory
//	 * @return List of ParkingCategoryModel
//	 */
//	public List<ParkingCategoryModel> GetParkingCategory();
	
        /**
	 * GetParkingCategoryWithFreeSpace
	 * @return List of ParkingCategoryModel
	 */
	public List<ParkingCategoryModel> GetParkingCategoryWithFreeSpace();
        
	/**
	 * GetCategoryId
	 * @param category category
	 * @return int
	 */
	public int GetCategoryId(String category);
	
        /**
	 * GetParkingLotId
	 * @param Int categoryId
	 * @return int
	 */
	public int GetParkingLotId(int categoryId);
        
	/**
	 * AddParking
	 * @param parking parking
	 * @return int
	 */
	public int AddParking(ParkingLot parking);
	
//	/**
//	 * GetParkingLot
//	 * @return List of ParkingLot
//	 */
//	public List<ParkingLot> GetParkingLot();
	
	/**
	 * UpdateParkingLot
	 * @param parkingLot parkingLot
	 * @return boolean
	 */
//	public boolean UpdateParkingLot(ParkingLot parkingLot);
	
	/**
	 * DeleteParkingLot
	 * @param parkingLot parkingLot
	 * @return boolean
	 */
//	public boolean DeleteParkingLot(ParkingLot parkingLot);
	
	/**
	 * GetParkingSpaceDetail
	 * @return List of ParkingModel
	 */
	public List<ParkingModel> GetParkingSpaceDetail();
	
	/**
	 * GetParkingLog
	 * @return List of ParkingModel 
	 */
	public List<ParkingModel> GetParkingLog();
	
	/**
	 * AddParkingBooking
	 * @param parking parking
	 * @return int
	 */
	public int AddParkingBooking(ParkingModel parking);
        
        public boolean UpdateParkingLotStatus(int parkingLotID, int parkingLotCategoryIDs);

        public boolean UpdateParkingCategory(ParkingCategoryModel parkingCategory);
        
        public boolean DeleteParkingLot(int parkingCategoryID, int parkingLotID);
        
        public boolean DeleteParkingLog(int bookID);

        public boolean DeleteParkingCategory(ParkingCategoryModel parkingCategory);
        
        public boolean DeleteParkingLot(int parkingCategoryID);
        
        public boolean CheckParkingLotStatus(ParkingModel parkingModel);
        
        public boolean UpdateParkingLogStatus(int bookID);
}
