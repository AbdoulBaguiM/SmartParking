package common;


public class Common {
	/**
	 * Used to check whether input is null or empty
	 * @param str string input
	 * @return true if satisfied
	 */
	public static boolean IsNullOrEmpty(String str){
		return (str==null || str.trim().isEmpty()) ? true : false;
	}
}
