package dao.Interface;

import java.util.List;

import model.UserProfileModel;

public interface ILoginDAO {
	
	/**
	 * Gets the user list
	 * @return list of string
	 */
	public List<String> getUserNameList();
	
	/**
	 * getUserDetails deteails
	 * @param userName userName
	 * @return UserProfileModel
	 */
	public UserProfileModel getUserDetails(String userName);
}
