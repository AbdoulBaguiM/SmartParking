package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import common.Constants;
import dao.Interface.ILoginDAO;

import java.util.List;

import model.UserProfileModel;


public class LoginDAO extends BaseDAO implements ILoginDAO {

	/**
	 * Gets the user list
	 * @return list  of string
	 */
	
	public List<String> getUserNameList() {
		List<String> userName = new ArrayList<String>();
		try {
			CreateConnection();
			CreateStatement();
			ResultSet rs = ExecuteQuery(Constants.USERNAME_LIST);
			while (rs.next())
				userName.add(rs.getString(1));
		} catch (SQLException e) {
			if (e.getMessage().equalsIgnoreCase("Too many connections"))
				System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		} catch (Exception e) {
			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		}
		return userName;
	}

	/**
	 * getUserDetails deteails
	 * @param userName userName
	 * @return UserProfileModel
	 */
	public UserProfileModel getUserDetails(String userName) {
		try {
			UserProfileModel userProfileModel = null;
			CreateConnection();
			
			PreparedStatement getPassword = PrepareStatement(Constants.GET_USER_DETAIL);
			getPassword.setString(1, userName);

			ResultSet rs = getPassword.executeQuery();
			while (rs.next())
				userProfileModel = Map_ResultSet_To_UserProfileModel(rs);

			getPassword = PrepareStatement(Constants.GET_USER_ADDRESS);
			getPassword.setInt(1, userProfileModel.getAddress().getAddressId());

			rs = getPassword.executeQuery();
			while (rs.next())
				userProfileModel.setAddress(Map_ResultSet_To_Address(rs));

			return userProfileModel;

		} catch (SQLException e) {
			if (e.getMessage().equalsIgnoreCase("Too many connections"))
				System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		} catch (Exception e) {
			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		}
		return null;
	}

}