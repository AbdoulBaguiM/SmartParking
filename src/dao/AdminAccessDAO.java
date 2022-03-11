package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import common.Constants;
import dao.Interface.IAdminAccessDAO;
import model.UserProfileModel;


public class AdminAccessDAO extends BaseDAO implements IAdminAccessDAO {

	/**
	 * Loads the user details
	 * @return list of user profile model
	 */
	public List<UserProfileModel> getUserDetails(){
		List<UserProfileModel> userList = new ArrayList<UserProfileModel>();
		try {
			CreateConnection();
			PreparedStatement getAllUser = PrepareStatement(Constants.GET_ALL_USER);
			ResultSet rs = getAllUser.executeQuery();
			while (rs.next())
				userList.add(Map_ResultSet_To_UserProfileModel(rs));
		} catch (SQLException e) {
			if (e.getMessage().equalsIgnoreCase("Too many connections"))
				System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		} catch (Exception e) {
			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		}
		return userList;
	}
	
	/**
	 * Updates the user details
	 * @param userId userId
	 * @param role role
	 * @return true/false
	 */
	public boolean UpdateUserDetail(int userId, String role){
		try {
			CreateConnection();
			PreparedStatement updateUser = PrepareStatement(Constants.UPDATE_USER_ROLE);
			updateUser.setString(1, role);
			updateUser.setInt(2, userId);
			int result = updateUser.executeUpdate();
			if(result>0)
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			if (e.getMessage().equalsIgnoreCase("Too many connections"))
				System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		} catch (Exception e) {
			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		}
		return false;
		
	}
        
        public boolean DeleteUser(int userId){
		try {
			CreateConnection();
			PreparedStatement deleteUser = PrepareStatement(Constants.DELETE_USER);
			deleteUser.setInt(1, userId);
			int result = deleteUser.executeUpdate();
			if(result>0)
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			if (e.getMessage().equalsIgnoreCase("Too many connections"))
				System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		} catch (Exception e) {
			System.out.println("Une erreur s'est produite, Veuillez contacter l'administrateur");
		}
		return false;
		
	}
 
}
