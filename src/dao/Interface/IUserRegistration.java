package dao.Interface;

import model.AddressModel;
import model.UserProfileModel;



public interface IUserRegistration {

	/**
	 * GetMaxAddressId
	 * @return int
	 */
	public int GetMaxAddressId();
	
	/**
	 * Add Address
	 * @param address address
	 * @return int
	 */
	public int AddAddress(AddressModel address);
	
	/**
	 * Update Address
	 * @param address address
	 * @return int
	 */
	public int UpdateAddress(AddressModel address);
	
	/**
	 * Add User Profile
	 * @param userProfile userProfile
	 * @return int
	 */
	public int AddUserProfile(UserProfileModel userProfile);
	
	/**
	 * Update User Profile
	 * @param userProfile userProfile
	 * @return int
	 */
	public int UpdateUserProfile(UserProfileModel userProfile);
}
