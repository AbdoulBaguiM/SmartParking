package controller;

import java.util.List;
import common.Common;
import common.Constants;
import dao.LoginDAO;
import dao.UserRegistrationDAO;
import dao.Interface.ILoginDAO;
import dao.Interface.IUserRegistration;
import model.AddressModel;
import model.UserProfileModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;


public class UserRegistrationController extends BaseController {

	/* private variables */
	@FXML
	private TextField txtUserName;
	@FXML
	private Text errUserName;
	@FXML
	private TextField txtFirstName;
	@FXML
	private Text errFirstName;
	@FXML
	private TextField txtLastName;
	@FXML
	private Text errLastName;
	@FXML
	private TextField txtAddressLine1;
	@FXML
	private Text errAddressLine1;
	@FXML
	private TextField txtCity;
	@FXML
	private Text errCity;
	@FXML
	private TextField txtZipCode;
	@FXML
	private Text errZipCode;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private Text errPassword;
	@FXML
	private PasswordField txtPassword2;
	@FXML
	private Text errPassword1;
	@FXML
	private TextField txtPhoneNumber;
        @FXML
        private Text errPhoneNumber;
	@FXML
	private TextField txtEmailAddress;
	@FXML
	private Text errEmailAddress;
	@FXML
	private Button btnRegister;
	@FXML
	private Text txtUserRegistrationHeading;
	@FXML
	private Hyperlink hlinkBack;
	@FXML
	private Hyperlink hlinkLogout;
	@FXML
	private Text errUserRegistration;

	private List<String> userNameList;

	private IUserRegistration userRegistrationDAO = new UserRegistrationDAO();

	private ILoginDAO loginDAO = new LoginDAO();

	/**
	 * UserRegistrationController constructor
	 */
	public UserRegistrationController() {
		userNameList = loginDAO.getUserNameList();
	}

	/**
	 * Set Field for all the variables
	 */
	public void SetField() {
		txtUserName.setText(getLoginUser().getUserName());
		txtUserName.setDisable(true);
		txtFirstName.setText(getLoginUser().getFirstName());
		txtLastName.setText(getLoginUser().getLastName());
		txtAddressLine1.setText(getLoginUser().getAddress().getLine1());
		txtCity.setText(getLoginUser().getAddress().getCity());
		txtZipCode.setText(getLoginUser().getAddress().getZip() + "");
		txtPhoneNumber.setText(getLoginUser().getPhoneNumber());
		txtEmailAddress.setText(getLoginUser().getEmailId());
		btnRegister.setText("Mettre à Jour");
	}

	/**
	 * Set Page Heading
	 * 
	 * @param msg
	 *            msg
	 */
	public void SetPageHeading(String msg) {
		this.txtUserRegistrationHeading.setText(msg);
	}

	/**
	 * Checks whether user name is available
	 * 
	 * @param name
	 *            name
	 * @return true for valid and false for invalid
	 */
	private boolean IsUserNameAvailabile(String name) {
		return !this.userNameList.contains(name);
	}

	/**
	 * CheckUserName
	 * 
	 * @param ev
	 *            ev
	 */
	public void CheckUserName(KeyEvent ev) {
		if (getRole() == 'G' || getLoginUser().getRole() == 'A') {
			SetErrorMessage(txtUserName.getText(), errUserName, Constants.errUserName);
			if (!Common.IsNullOrEmpty(txtUserName.getText()))
				if (!IsUserNameAvailabile(txtUserName.getText()))
					SetErrorMessage(errUserName, Constants.errUserNameNotAvailable);
				else
					SetErrorMessage(errUserName, Constants.String_Empty);
		}
	}

	/**
	 * ValidateFirstName
	 * 
	 * @param ev
	 *            ev
	 */
	public void ValidateFirstName(KeyEvent ev) {
		SetErrorMessage(txtFirstName.getText(), errFirstName, Constants.errFirstName);
	}

	/**
	 * ValidateLastName
	 * 
	 * @param ev
	 *            ev
	 */
	public void ValidateLastName(KeyEvent ev) {
		SetErrorMessage(txtLastName.getText(), errLastName, Constants.errLastName);
	}

	/**
	 * ValidateAddressLine1
	 * 
	 * @param ev
	 *            ev
	 */
	public void ValidateAddressLine1(KeyEvent ev) {
		SetErrorMessage(txtAddressLine1.getText(), errAddressLine1, Constants.errAddressLine1);
	}

	/**
	 * ValidateCity
	 * 
	 * @param ev
	 *            ev
	 */
	public void ValidateCity(KeyEvent ev) {
		SetErrorMessage(txtCity.getText(), errCity, Constants.errCity);
	}

	/**
	 * ValidateZipCode
	 * 
	 * @param ev
	 *            ev
	 */
	public void ValidateZipCode(KeyEvent ev) {
		ValidateRegex(txtZipCode.getText(), errZipCode, Constants.zip_regex, Constants.errZipCode,
				Constants.errZipCode_Should_Be_Number);
	}

	/**
	 * ValidatePassword1
	 * 
	 * @param ev
	 *            ev
	 */
	public void ValidatePassword1(KeyEvent ev) {
		if (getRole() == 'G' || getLoginUser().getRole() == 'A'
				|| (getLoginUser().getRole() == 'C' && !Common.IsNullOrEmpty(txtPassword.getText()))
				|| (getLoginUser().getRole() == 'E' && !Common.IsNullOrEmpty(txtPassword.getText())))
			ValidatePassword(txtPassword, errPassword);
	}

	/**
	 * ValidatePassword2
	 * 
	 * @param ev
	 *            ev
	 */
	public void ValidatePassword2(KeyEvent ev) {
		if (getRole() == 'G' || getLoginUser().getRole() == 'A'
				|| (getLoginUser().getRole() == 'C' && !Common.IsNullOrEmpty(txtPassword2.getText()))
				|| (getLoginUser().getRole() == 'E' && !Common.IsNullOrEmpty(txtPassword2.getText())))
			ValidatePassword(txtPassword2, errPassword1);
	}

	/**
	 * Validate password
	 * 
	 * @param input
	 *            input
	 * @param errField
	 *            errField
	 */
	private void ValidatePassword(TextField input, Text errField) {
		ValidateRegex(input.getText(), errField, Constants.password_regex, Constants.errPassword,
				Constants.errPassword_Min_Length);
		if (errField.getText() == null) {
			int length = input.getText().length();
			if (length < 8)
				SetPasswordStrength(input, errField, Constants.passwordStrength_Bad, Color.RED, "bad");
			else if (length < 10)
				SetPasswordStrength(input, errField, Constants.passwordStrength_Fair, Color.ORANGERED, "fair");
			else if (length < 12)
				SetPasswordStrength(input, errField, Constants.passwordStrength_Good, Color.ORANGE, "good");
			else
				SetPasswordStrength(input, errField, Constants.passwordStrength_Strong, Color.GREEN, "strong");
		}
	}

	/**
	 * SetPasswordStrength sets the password strength
	 * 
	 * @param input
	 *            input
	 * @param errField
	 *            errField
	 * @param strengthMsg
	 *            strengthMsg
	 * @param colorValue
	 *            colorValue
	 * @param cssClass
	 *            cssClass
	 */
	private void SetPasswordStrength(TextField input, Text errField, String strengthMsg, Color colorValue,
			String cssClass) {
		SetErrorMessage(errField, strengthMsg);
		errField.setFill(colorValue);
		input.getStyleClass().add(cssClass);
	}

	/**
	 * ValidateRetypePassword
	 * 
	 * @param ev
	 *            ev
	 */
	public void ValidateRetypePassword(KeyEvent ev) {
		ValidateRegex(txtPassword2.getText(), errPassword1, Constants.password_regex, Constants.errPassword,
				Constants.errPassword_Min_Length);
		if (!txtPassword.getText().equals(txtPassword2.getText()))
			errPassword1.setText(Constants.errPassword_MisMatch);
	}

	/**
	 * ValidateEmail
	 * 
	 * @param ev
	 *            ev
	 */
	public void ValidateEmail(KeyEvent ev) {
		 ValidateRegex(txtEmailAddress.getText(), errEmailAddress,
		 Constants.email_regex, Constants.errEmailAddress,
		 Constants.errEmailAddress_Invalid);
	}
        
        	/**
	 * ValidatePhoneNumber
	 * 
	 * @param ev
	 *            ev
	 */
	public void ValidatePhoneNumber(KeyEvent ev) {
		ValidateRegex(txtPhoneNumber.getText(), errPhoneNumber,
		 Constants.phoneNumber_regex, Constants.errPhoneNumber,
		 Constants.errPhoneNumber_Invalid);
	}

	/**
	 * Checks for error message
	 * 
	 * @return true and false
	 */
	public boolean checkErrorMessage() {
		if (Common.IsNullOrEmpty(errUserName.getText()) && Common.IsNullOrEmpty(errFirstName.getText())
				&& Common.IsNullOrEmpty(errLastName.getText()) && Common.IsNullOrEmpty(errAddressLine1.getText())
				&& Common.IsNullOrEmpty(errCity.getText()) && Common.IsNullOrEmpty(errPhoneNumber.getText())
				&& Common.IsNullOrEmpty(errZipCode.getText()) && Common.IsNullOrEmpty(errPassword.getText())
				&& Common.IsNullOrEmpty(errPassword1.getText()) && Common.IsNullOrEmpty(errEmailAddress.getText()))
			return true;
		else
			return false;
	}

	/**
	 * Checks for validations
	 */
	private void CheckValidations() {
		CheckUserName(null);
		ValidateFirstName(null);
		ValidateLastName(null);
		ValidateAddressLine1(null);
		ValidateCity(null);
                ValidatePhoneNumber(null);
                ValidateEmail(null);
		ValidateZipCode(null);
		if (getRole() == 'G' || getLoginUser().getRole() == 'A'
				|| (getLoginUser().getRole() == 'C' && !Common.IsNullOrEmpty(txtPassword2.getText()))
				|| (getLoginUser().getRole() == 'C' && !Common.IsNullOrEmpty(txtPassword.getText()))
				|| (getLoginUser().getRole() == 'E' && !Common.IsNullOrEmpty(txtPassword2.getText()))
				|| (getLoginUser().getRole() == 'E' && !Common.IsNullOrEmpty(txtPassword.getText())))
			ValidateRetypePassword(null);
		else {
			errPassword.setText(Constants.String_Empty);
			errPassword1.setText(Constants.String_Empty);
		}
	}

	/**
	 * btnRegister register event
	 * 
	 * @param ev
	 *            ev
	 */
	public void btnRegister(ActionEvent ev) {
		CheckValidations();
		if (checkErrorMessage()) {
			UserProfileModel userProfile = new UserProfileModel();
			AddressModel address = new AddressModel();
                        
			address.setLine1(txtAddressLine1.getText());
			address.setCity(txtCity.getText());
			address.setZip(Integer.parseInt(txtZipCode.getText()));

			userProfile.setFirstName(txtFirstName.getText());
			userProfile.setLastName(txtLastName.getText());
			userProfile.setAddress(address);
			userProfile.setPassword(txtPassword.getText());
			userProfile.setPhoneNumber(txtPhoneNumber.getText());
			userProfile.setEmailId(txtEmailAddress.getText());

			if (getRole() == 'G' || getLoginUser().getRole() == 'A') {
				userProfile.setUserName(txtUserName.getText());
				userProfile.setRole(getRole() == 'G' ? 'C' : getRole());
				userProfile.setAuthorized('A');

				int result = userRegistrationDAO.AddUserProfile(userProfile);

				if (result > 0) {
					SuccessMsg = userProfile.getUserName().toUpperCase() + " créé avec succès";
					if (getRole() == 'G')
						RedirectBasedOnRole(ev, Constants.USERREGISTRATION, 'G', "Connectez-vous");
					else if (getLoginUser() != null) {
						if (getLoginUser().getRole() == 'A')
							RedirectBasedOnRole(ev, Constants.USERREGISTRATION, 'A', "Panneau d'administration");
						else if (getLoginUser().getRole() == 'E') {
							// redirect to employee dashboard
						}
					} else {
						this.errUserRegistration.setText("Une erreur s'est produite, Veuillez réessayer plus tard");
						this.btnRegister.setDisable(true);
					}
				} else
					errEmailAddress.setText("Veuillez vérifier les champs");
			} else if (getLoginUser().getRole() == 'C' || getLoginUser().getRole() == 'E') {

				address.setAddressId(getLoginUser().getAddress().getAddressId());
				userProfile.setUserId(getLoginUser().getUserId());

				int result = userRegistrationDAO.UpdateUserProfile(userProfile);

				if (result > 0) {
					SuccessMsg = "Mise à jour réussie";
					setLoginUser(loginDAO.getUserDetails(getLoginUser().getUserName()));
					RedirectBasedOnRole(ev, Constants.LOGIN, 'C', "Acceuil");

				} else
					errUserRegistration.setText("Veuillez vérifier les champs");
			}
		}
	}

	/**
	 * Disable Logout Link
	 */
	public void DisableLogoutLink() {
		// hlinkBack.setVisible(false);
		hlinkLogout.setVisible(false);
	}

	/**
	 * Action method for back link
	 * 
	 * @param ev
	 *            event
	 */
	public void Back_Click(ActionEvent ev) {
		SuccessMsg = Constants.String_Empty;
		if (getRole() == 'G')
			Logout_Click(ev);
		else if (getRole() == 'F')
			RedirectBasedOnRole(ev, Constants.LOGIN, 'G', "Connectez-vous");
		else if (getLoginUser().getRole() == 'A')
			RedirectBasedOnRole(ev, Constants.USERREGISTRATION, 'A', "Panneau d'administration");
		else if (getLoginUser().getRole() == 'E')
			if (getRole() == 'X')
				RedirectBasedOnRole(ev, Constants.LOGIN,'E', "Tableau de bord Employé");
			else
				RedirectBasedOnRole(ev, Constants.USERREGISTRATION, 'E', "Panneau d'administration");
		else if (getLoginUser().getRole() == 'C')
			RedirectBasedOnRole(ev, Constants.LOGIN, 'C', "Acceuil");
	}

	/**
	 * Action for logout button
	 * 
	 * @param ev
	 *            event
	 */
	public void Logout_Click(ActionEvent ev) {
		setLoginUser(null);
		RedirectBasedOnRole(ev, Constants.LOGOUT, '\0', "Login");
	}

}
