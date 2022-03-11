package common;


public class Constants {
	public final static String String_Empty = "";
	public final static String ICON_URL = "/img/logo.png";
        public final static String CSS_URL = "/styling/application.css";
        public final static String USER_REGISTRATION_URL = "/view/UserRegistration.fxml";
	public final static String ADMINHOME_URL = "/view/AdminHome.fxml";
	public final static String CUSTHOME_URL = "/view/CustomerHome.fxml";
	public final static String LOGIN_URL = "/view/Login.fxml";
	public final static String ADMIN_ACCESS_URL = "/view/AdminAccess.fxml";
	public final static String PARKING_LOT_DETAILS_URL = "/view/ParkingLotDetails.fxml";
	public final static String BOOK_PARKING = "/view/BookParking.fxml";
	public final static String ViewLog = "/view/ViewBooking.fxml";
	public final static String ADMIN_MANAGE_URL = "/view/AdminManagement.fxml";

	public final static String ADMINHOME = "ADMIN";
        public final static String ADMINMANAGE = "ADMINMANAGE";
        public final static String CUSTOMEMR = "CUSTOMER";
        public final static String VISITOR = "VISITOR";
	public final static String LOGIN = "LOGIN";
	public final static String USERREGISTRATION = "USERREGISTRATION";
	public final static String LOGOUT = "LOGOUT";
	public final static String ADMINACCESS = "ADMINACCESS";
	public final static String PARKINGLOTDETAILS = "PARKINGLOTDETAILS";
        

	public final static String errUserName = "Veuillez renseigner un Identifiant";
	public final static String errUserNameNotAvailable = "Nom d'utilisateur déjà utilisé";
	public final static String errUserNameNotFound = "Nom d'utilisateur inconnu";
	public final static String errIncorrectPassword = "Mot de passe incorrect";
	public final static String errFirstName = "Veuillez renseigner un nom";
	public final static String errLastName = "Veuillez renseigner un prénom";
	public final static String errAddressLine1 = "Veuillez renseigner une adresse";
	public final static String errCity = "Veuillez renseigner une ville";
	public final static String errPhoneNumber = "Veuillez renseigner un numéro de téléphone";
        public final static String errPhoneNumber_Invalid = "Veuillez renseigner un numéro valide";
	public final static String errZipCode = "Veuillez renseigner un code postal";
	public final static String errZipCode_Should_Be_Number = "Le code postal doit respecter le format";
	public final static String errPassword = "Veuillez renseigner un mot de passe";
	public final static String errPassword_MisMatch = "Les mots de passe ne correspondent pas";
	public final static String errPassword_Min_Length = "Minimum huit caractères";
	public final static String passwordStrength_Bad = "Niveau de sécurité : Faible";
	public final static String passwordStrength_Fair = "Niveau de sécurité : Passable";
	public final static String passwordStrength_Good = "Niveau de sécurité : Moyenne";
	public final static String passwordStrength_Strong = "Niveau de sécurité : Forte";
	public final static String errEmailAddress = "Veuillez renseigner une adresse mail";
	public final static String errEmailAddress_Invalid = "Adresse email invalide";
	public final static String errCategoryEmpty = "Veuillez renseigner correctement les champs";
        public final static String errDetailsEmpty = "Veuillez renseigner les détails";
	public final static String onlyNumberAllowed = "Seul les chiffres sont autorisés";
	public final static String errCategory_NoSpecial = "Aucun caractère spécial autorisé";
	public final static String descriptionEmpty = "Veuillez renseigner une description";
	public final static String noOfSpaceEmpty = "Veuillez renseigner le nombre de place";
        
	public final static String zip_regex = "^\\d{5}$";
	public final static String ParkingSpace_regex = "^\\d+$";
	public final static String Parking_regex = "^([a-z]+|[A-z]+|[0-9]+)+ *([a-z]*[A-z]*[0-9]*)*$";
	public final static String password_regex = "^(?=[^\\d_].*?\\d)\\w(\\w|[!@#$%]){7,14}$";
	public final static String email_regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        public final static String phoneNumber_regex = "(\\+212|0)([ \\-_/]*)(\\d[ \\-_/]*){9}";

	public final static String INSERT_ADDRESS = "INSERT INTO ADDRESS"
			+ "(LINE1,CITY,ZIP,CREATEDDATE,UPDATEDDATE)" + "VALUES(?,?,?,?,?)";
        public final static String INSERT_USER = "INSERT INTO USERPROFILE"
			+ "(FIRSTNAME,LASTNAME,ADDRESSID,USERNAME,PASSWORD,ROLE, PHONENUMBER,EMAILID,AUTHORIZED,CREATEDDATE,UPDATEDDATE)"
			+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        public final static String INSERT_PARKING_CATEGORY = "INSERT INTO PARKINGCATEGORY"
                + "(DESCRIPTION, DETAILS, NOOFSPACE, CREATEDDATE, UPDATEDDATE) VALUES(?,?,?,?,?)";
	public final static String INSERT_PARKING_LOG = "INSERT INTO parkinglot_log"
                +"(PARKINGCATEGORYID,PARKINGLOTID,USERID,FROMDATE,TODATE,STATUS,PRICE) VALUES(?,?,?,?,?,?,?)";
	public final static String INSERT_PARKING_LOT = "INSERT INTO PARKINGLOT"
			+ "(PARKINGLOTID,PARKINGCATEGORYID,STATUS,CREATEDDATE,UPDATEDDATE) "
			+ "VALUES(?,?,?,?,?)";
        
        public final static String GET_PARKINGLOT_ID = "SELECT parkinglotid FROM parkinglot WHERE status = 'A' AND parkingcategoryid = ? LIMIT 1";
        public final static String GET_FREE_PARKINGLOT = "SELECT * FROM PARKINGLOT WHERE STATUS = 'A'";
	public final static String GET_PARKING_SPACE_Detail = "SELECT PARKINGLOT.PARKINGLOTID, PARKINGCATEGORY.DESCRIPTION, PARKINGCATEGORY.DETAILS, PARKINGLOT.NOOFSPACE,parkinglot.CURRENTPLACE FROM PARKINGLOT JOIN PARKINGCATEGORY ON PARKINGLOT.PARKINGCATEGORYID = PARKINGCATEGORY.PARKINGCATEGORYID";
        public final static String GETPARKINGLOG ="SELECT PARKINGLOT_LOG.BOOKINGID,PARKINGLOT_LOG.USERID, (SELECT USERNAME FROM USERPROFILE WHERE USERID = PARKINGLOT_LOG.USERID) AS USERNAME, (SELECT CONCAT(DESCRIPTION,PARKINGLOT_LOG.PARKINGLOTID) FROM PARKINGCATEGORY, PARKINGLOT WHERE PARKINGLOT.PARKINGLOTID = PARKINGLOT_LOG.PARKINGLOTID AND PARKINGLOT.PARKINGCATEGORYID = PARKINGCATEGORY.PARKINGCATEGORYID AND PARKINGLOT_LOG.PARKINGCATEGORYID = PARKINGCATEGORY.PARKINGCATEGORYID) AS DESCRIPTION, PARKINGLOT_LOG.FROMDATE,PARKINGLOT_LOG.TODATE,PARKINGLOT_LOG.STATUS FROM PARKINGLOT_LOG";
        public final static String GET_CATEGORY_ID = "SELECT * FROM PARKINGCATEGORY WHERE DESCRIPTION= ?";
	public final static String GET_PARKING_LOT = "SELECT PARKINGLOTID , PARKINGCATEGORY.DESCRIPTION AS CATEGORY,"
                        + "PARKINGCATEGORY.DETAILS AS DESCRIPTION"
			+ "FROM PARKINGLOT"
			+ "INNER JOIN PARKINGCATEGORY "
			+ "ON PARKINGLOT.PARKINGCATEGORYID = PARKINGCATEGORY.PARKINGCATEGORYID "
                        + "ORDER BY PARKINGCATEGORY.DESCRIPTION ASC ";
        public final static String GET_USER_DETAIL = "SELECT * FROM USERPROFILE WHERE USERNAME=?";
	public final static String GET_USER_ADDRESS = "SELECT * FROM address where ADDRESSID=?";
	public final static String GET_ALL_USER = "SELECT * FROM USERPROFILE";
        public final static String GET_PARKING_CATEGORY = "SELECT * FROM PARKINGCATEGORY ORDER BY DESCRIPTION ASC";
        public final static String GET_PARKING_CATEGORY_WITH_FREE_SPACE = "SELECT parkingcategory.*,(SELECT COUNT(*) FROM parkinglot WHERE PARKINGCATEGORYID = parkingcategory.PARKINGCATEGORYID AND STATUS='A') AS FREESPACE FROM PARKINGCATEGORY ORDER BY DESCRIPTION ASC";
        public final static String GET_PARKINGLOT_STATUS = "SELECT * FROM parkinglot_log WHERE parkingcategoryid = ? AND parkinglotid = ? AND TODATE > ? AND FROMDATE < ? ";
        
        public final static String UPDATE_ADDRESS = "UPDATE ADDRESS SET LINE1=?, CITY=?, ZIP=?, UPDATEDDATE=? WHERE ADDRESSID=?";
        public final static String UPDATE_USER_WITHOUT_PASSWORD = "UPDATE userprofile SET FIRSTNAME=?, LASTNAME=?, PHONENUMBER=?, EMAILID=?,UPDATEDDATE=? WHERE USERID=?";
	public final static String UPDATE_USER_WITH_PASSWORD = "UPDATE userprofile SET FIRSTNAME=?, LASTNAME=?, PASSWORD=?, PHONENUMBER=?, EMAILID=?,UPDATEDDATE=? WHERE USERID=?";
	public final static String UPDATE_USER_ROLE = "UPDATE USERPROFILE SET ROLE=? WHERE USERID=?";
        public final static String UPDATE_PARKING_CATEGORY = "UPDATE PARKINGCATEGORY SET NOOFSPACE=? WHERE PARKINGCATEGORYID=?";
        public final static String UPDATE_PARKINGLOT_STATUS = "UPDATE parkinglot SET status = 'R' WHERE parkinglotid = ? AND parkingcategoryid = ?";
        public final static String UPDATE_PARKINGLOG_STATUS = "UPDATE parkinglot_log SET status = 'Terminé' WHERE bookingid = ?";

   	public final static String MAX_PARKING_CATEGORY_ID = "SELECT MAX(PARKINGCATEGORYID) FROM PARKINGCATEGORY";
        public final static String MAX_ADDRESS_ID = "SELECT MAX(ADDRESSID) FROM ADDRESS";
	
        public final static String USERNAME_LIST = "SELECT USERNAME FROM USERPROFILE";
	
        public final static String DELETE_USER = "DELETE FROM USERPROFILE WHERE USERID = ?";
        public final static String DELETE_PARKING_CATEGORY = "DELETE FROM PARKINGCATEGORY WHERE PARKINGCATEGORYID=?";
        public final static String DELETE_PARKING_LOTS = "DELETE FROM PARKINGLOT WHERE PARKINGCATEGORYID = ?";
        public final static String DELETE_PARKING_LOT = "DELETE FROM PARKINGLOT WHERE PARKINGCATEGORYID = ? AND PARKINGLOTID = ?";
        public final static String DELETE_PARKING_LOG = "DELETE FROM PARKINGLOT_LOG WHERE BOOKINGID = ?";
}