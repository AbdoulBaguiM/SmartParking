package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.Common;
import common.Constants;
import dao.Interface.IUserRegistration;
import model.AddressModel;
import model.UserProfileModel;



public class UserRegistrationDAO extends BaseDAO implements IUserRegistration{

	/**
	 * GetMaxAddressId
	 * @return int
	 */
	public int GetMaxAddressId() {
		try {
			CreateConnection();
			CreateStatement();
			ResultSet rs = ExecuteQuery(Constants.MAX_ADDRESS_ID);
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
	 * Add Address
	 * @param address address
	 * @return int
	 */
	public int AddAddress(AddressModel address) {
		try {
			CreateConnection();
			PreparedStatement insertAddress = PrepareStatement(Constants.INSERT_ADDRESS);
			// "(LINE1,CITY,ZIP,CREATEDDATE,UPDATEDDATE)"
			insertAddress.setString(1, address.getLine1());
			insertAddress.setString(2, address.getCity());
			insertAddress.setInt(3, address.getZip());
			insertAddress.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));
			insertAddress.setDate(5, java.sql.Date.valueOf(java.time.LocalDate.now()));

			int result = insertAddress.executeUpdate();
			if (result > 0)
				return GetMaxAddressId();

		} catch (SQLException e) {
			if (e.getMessage().equalsIgnoreCase("Too many connections"))
				System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		} catch (Exception e) {
			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		}
		return -1;
	}

	/**
	 * Update Address
	 * @param address address
	 * @return int
	 */
	public int UpdateAddress(AddressModel address) {
		try {
			CreateConnection();
			PreparedStatement updateAddress = PrepareStatement(Constants.UPDATE_ADDRESS);
                        //LINE1, CITY, ZIP, UPDATEDDATE
			updateAddress.setString(1, address.getLine1());
			updateAddress.setString(2, address.getCity());
			updateAddress.setInt(3, address.getZip());
			updateAddress.setDate(4, java.sql.Date.valueOf(java.time.LocalDate.now()));
			updateAddress.setInt(5, address.getAddressId());

			return updateAddress.executeUpdate();

		} catch (SQLException e) {
			if (e.getMessage().equalsIgnoreCase("Too many connections"))
				System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		} catch (Exception e) {
			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		}
		return -1;
	}

	/**
	 * Add User Profile
	 * @param userProfile userProfile
	 * @return int
	 */
	public int AddUserProfile(UserProfileModel userProfile) {
		try {
			int addressId = AddAddress(userProfile.getAddress());
			if (addressId != -1) {
				CreateConnection();
				PreparedStatement insertUser = PrepareStatement(Constants.INSERT_USER);
				// (FIRSTNAME,LASTNAME,ADDRESSID,USERNAME,PASSWORD,ROLE,
				// PHONENUMBER,EMAILID,AUTHORIZED,CREATEDDATE,UPDATEDDATE)
				insertUser.setString(1, userProfile.getFirstName());
				insertUser.setString(2, userProfile.getLastName());
				insertUser.setInt(3, addressId);
				insertUser.setString(4, userProfile.getUserName());
				insertUser.setString(5, userProfile.getPassword());
                                insertUser.setString(6, Character.toString(userProfile.getRole()));
				insertUser.setString(7, userProfile.getPhoneNumber());
				insertUser.setString(8, userProfile.getEmailId());
				insertUser.setString(9, Character.toString(userProfile.getAuthorized()));
				insertUser.setDate(10, java.sql.Date.valueOf(java.time.LocalDate.now()));
				insertUser.setDate(11, java.sql.Date.valueOf(java.time.LocalDate.now()));

				return insertUser.executeUpdate();
			}
		} catch (SQLException e) {
			if (e.getMessage().equalsIgnoreCase("Too many connections"))
				System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		} catch (Exception e) {
			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		}
		return -1;
	}

	/**
	 * Update User Profile
	 * @param userProfile userProfile
	 * @return int
	 */
	public int UpdateUserProfile(UserProfileModel userProfile) {
		try {
			int addressId = UpdateAddress(userProfile.getAddress());
			if (addressId != -1) {
				CreateConnection();
				PreparedStatement insertUser = PrepareStatement(Common.IsNullOrEmpty(userProfile.getPassword())
						? Constants.UPDATE_USER_WITHOUT_PASSWORD : Constants.UPDATE_USER_WITH_PASSWORD);
				if (Common.IsNullOrEmpty(userProfile.getPassword()))
					SetParameter_WithoutPassword(insertUser, userProfile);
				else
					SetParameter_WithPassword(insertUser, userProfile);
				return insertUser.executeUpdate();
			}
		} catch (SQLException e) {
			if (e.getMessage().equalsIgnoreCase("Too many connections"))
				System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		} catch (Exception e) {
			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		}
		return -1;
	}

	/**
	 * SetParameter_WithPassword
	 * @param insertUser insertUser
	 * @param userProfile userProfile
	 * @throws SQLException SQLException
	 */
	private void SetParameter_WithPassword(PreparedStatement insertUser, UserProfileModel userProfile)
			throws SQLException {
		insertUser.setString(1, userProfile.getFirstName());
		insertUser.setString(2, userProfile.getLastName());
		insertUser.setString(3, userProfile.getPassword());
		insertUser.setString(4, userProfile.getPhoneNumber());
		insertUser.setString(5, userProfile.getEmailId());
		insertUser.setDate(6, java.sql.Date.valueOf(java.time.LocalDate.now()));
		insertUser.setInt(7, userProfile.getUserId());
	}

	/**
	 * SetParameter_WithoutPassword
	 * @param insertUser insertUser
	 * @param userProfile userProfile
	 * @throws SQLException SQLException
	 */
	private void SetParameter_WithoutPassword(PreparedStatement insertUser, UserProfileModel userProfile)
			throws SQLException {
		insertUser.setString(1, userProfile.getFirstName());
		insertUser.setString(2, userProfile.getLastName());
		insertUser.setString(3, userProfile.getPhoneNumber());
		insertUser.setString(4, userProfile.getEmailId());
		insertUser.setDate(5, java.sql.Date.valueOf(java.time.LocalDate.now()));
		insertUser.setInt(6, userProfile.getUserId());
	}

}