package dao;

import model.ParkingCategoryModel;
import model.UserProfileModel;
import model.ParkingLot;
import model.AddressModel;
import model.ParkingModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import dao.Connection;


public class BaseDAO {
	/* Private Variable */
	private Statement statement;
	private java.sql.Connection connection;

	/**
	 * Prepared Statement
	 * @param query query
	 * @return query
	 * @throws SQLException SQLException
	 */
	protected java.sql.PreparedStatement PrepareStatement(String query) throws SQLException {
		return this.connection.prepareStatement(query);
	}

	/**
	 * Create Statement
	 * @throws SQLException SQLException
	 */
	protected void CreateStatement() throws SQLException {
		this.statement = this.connection.createStatement();
	}

	/**
	 * Used to Create a connection and statement
	 */
	protected void CreateConnection() {
		try {
			this.connection = Connection.getInstance().getConnection();
		} catch (SQLException e) {
			if (e.getMessage().equalsIgnoreCase("Too many connections"))
				System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		} catch (ClassNotFoundException e) {
			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		} catch (Exception e) {
			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		}
	}

	/**
	 * closes the connection and the statement
	 */
	protected void CloseConnectionStatement() {
		try {
			this.statement.close();
			this.connection.close();
		} catch (Exception e) {
			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		}
	}

	
	/**
	 * ExecuteQuery
	 * @param query query
	 * @return ResultSet
	 * @throws SQLException SQLException
	 */
	protected ResultSet ExecuteQuery(String query) throws SQLException {
		return this.statement.executeQuery(query);
	}

	/**
	 * Map_ResultSet_To_UserProfileModel
	 * @param rs rs
	 * @return UserProfileModel
	 * @throws SQLException SQLException
	 */
	protected UserProfileModel Map_ResultSet_To_UserProfileModel(ResultSet rs) throws SQLException {
		UserProfileModel returnValue = new UserProfileModel();
		returnValue.setUserId(Integer.parseInt(rs.getString("USERID")));
		returnValue.setPassword(rs.getString("PASSWORD"));
		returnValue.setRole(rs.getString("ROLE").charAt(0));
		returnValue.setFirstName(rs.getString("FIRSTNAME"));
		returnValue.setLastName(rs.getString("LASTNAME"));
		returnValue.setUserName(rs.getString("USERNAME"));
		returnValue.setRole(rs.getString("ROLE").charAt(0));
		returnValue.setPhoneNumber(rs.getString("PHONENUMBER"));
		returnValue.setEmailId(rs.getString("EMAILID"));
		AddressModel add = new AddressModel();
		add.setAddressId(rs.getInt("ADDRESSID"));
		returnValue.setAddress(add);
		return returnValue;
	}
	
	/**
	 * Map_ResultSet_To_Address
	 * @param rs rs
	 * @return AddressModel
	 * @throws SQLException SQLException
	 */
	public AddressModel Map_ResultSet_To_Address(ResultSet rs) throws SQLException{
		AddressModel returnValue = new AddressModel();
		returnValue.setAddressId(rs.getInt(1));
		returnValue.setLine1(rs.getString(2));
		returnValue.setCity(rs.getString(3));
		returnValue.setZip(rs.getInt(4));
		return returnValue;
	}

	/**
	 * Map_ResultSet_To_ParkingLot
	 * @param rs rs
	 * @return ParkingLot
	 * @throws SQLException SQLException
	 */
	public ParkingLot Map_ResultSet_To_ParkingLot(ResultSet rs) throws SQLException {
		ParkingLot returnValue = new ParkingLot();
		returnValue.setParkingLotId(rs.getInt(1));
		returnValue.setParkingCategoryDesc(rs.getString(2));
                returnValue.setDescription(rs.getString(3));
		returnValue.setNoOfSpace(rs.getInt(4));
		return returnValue;
	}
	
	/**
	 * Map_ResultSet_To_ParkingModel
	 * @param rs rs
	 * @return ParkingModel
	 * @throws SQLException SQLException
	 */
	public ParkingModel Map_ResultSet_To_ParkingModel(ResultSet rs) throws SQLException {
		ParkingModel returnValue = new ParkingModel();
		returnValue.setParkingLotId(rs.getInt(1));
                returnValue.setCategoryDescription(rs.getString(2));
                returnValue.setDescription(rs.getString(3));
		returnValue.setNoOfSpace(rs.getInt(4));

		return returnValue;
	}

	/**
	 * Map_ResultSet_To_ParkingCategory
	 * @param rs rs
	 * @return ParkingCategoryModel
	 * @throws SQLException SQLException
	 */
	protected ParkingCategoryModel Map_ResultSet_To_ParkingCategory(ResultSet rs) throws SQLException {
		ParkingCategoryModel returnValue = new ParkingCategoryModel();
                returnValue.setDetails(rs.getString("DETAILS"));
		returnValue.setDescription(rs.getString("DESCRIPTION"));
		returnValue.setParkingCategoryId(rs.getInt("PARKINGCATEGORYID"));
                returnValue.setNoOfSpace(rs.getInt("NOOFSPACE"));
                returnValue.setNoOfFreeSpace(rs.getInt("FREESPACE"));
                returnValue.setCreatedDate(rs.getTimestamp("CREATEDDATE"));
                returnValue.setUpdatedDate(rs.getTimestamp("UPDATEDDATE"));
		return returnValue;
	}
	
	/**
	 * Map_ResultSet_To_ParkingModel_Log
	 * @param  rs rs
	 * @return ParkingModel
	 * @throws SQLException SQLException
	 */
	public ParkingModel Map_ResultSet_To_ParkingModel_Log(ResultSet rs) throws SQLException {
		ParkingModel returnValue = new ParkingModel();
                returnValue.setBookid(rs.getInt(1));
                returnValue.setUserId(rs.getInt(2));
		returnValue.setUserName(rs.getString(3));
		returnValue.setDescription(rs.getString(4));
		returnValue.setFromDate(rs.getTimestamp(5));
		returnValue.setToDate(rs.getTimestamp(6));
                returnValue.setStatus(rs.getString(7));
		return returnValue;
	}
}