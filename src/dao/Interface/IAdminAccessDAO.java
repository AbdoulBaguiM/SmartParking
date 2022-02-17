package dao.Interface;

import java.util.List;

import model.UserProfileModel;


public interface IAdminAccessDAO {
	/**
	 * Loads the user details
	 * @return List of UserProfile
	 */
	public List<UserProfileModel> getUserDetails();
	
	/**
	 * Updates the user details
	 * @param userId userId
	 * @param role role
	 * @return true/false
	 */
	public boolean UpdateUserDetail(int userId, String role);
}
