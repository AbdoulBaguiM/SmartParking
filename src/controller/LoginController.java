package controller;

import common.Common;
import common.Constants;
import dao.LoginDAO;
import dao.Interface.ILoginDAO;
import home.Main;
import iot.Server;
import java.net.URL;
import java.util.ResourceBundle;
import model.UserProfileModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;


public class LoginController extends BaseController {

	/* private variables */
	@FXML
	private TextField txtUserName;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private Text errUserName;
	@FXML
	private Text errPassword;
	@FXML
	private Button btnLogin;
	@FXML
	private Button btnReset;
        @FXML
        private Button btnViewParking;
	@FXML
        private Text txtLoginMsg;

	private ILoginDAO loginDAO = new LoginDAO();


	/**
	 * Sets success message
	 * @param msg msg
	 */
	public void SetSuccessMessage(String msg) {
		this.txtLoginMsg.setText(msg);
	}

	/**
	 * checks whether a valid user name
	 * @return true for valid and false for not valid
	 */
	public boolean IsUserNameValid() {
		return loginDAO.getUserNameList().contains(txtUserName.getText());
	}
	
	/**
	 * ValidateUserName
	 * @param ev ev
	 */
	public void ValidateUserName(KeyEvent ev) {
		SetErrorMessage(txtUserName.getText(), errUserName, Constants.errUserName);
	}

	/**
	 * ValidatePassword
	 * @param ev ev
	 */
	public void ValidatePassword(KeyEvent ev) {
		SetErrorMessage(txtPassword.getText(), errPassword, Constants.errPassword);
	}

	/**
	 * login click button 
	 * @param ev ev
	 */
	public void btnLogin_Click(ActionEvent ev) {
		SetErrorMessage(txtUserName.getText(), errUserName, Constants.errUserName);
		SetErrorMessage(txtPassword.getText(), errPassword, Constants.errPassword);
		
		if (!defaultCheck()) {
			if (IsUserNameValid()) {
				UserProfileModel userDetail = loginDAO.getUserDetails(txtUserName.getText());
				if (userDetail.getPassword().equals(txtPassword.getText())) {
					try {

						setLoginUser(userDetail);
						switch (userDetail.getRole()) {
						case 'A':				
							RedirectBasedOnRole(ev, Constants.LOGIN, 'A', "Panneau d'administration");
							break;
						case 'C':
							RedirectBasedOnRole(ev, Constants.LOGIN,'C', "Tableau de bord " + userDetail.getUserName());
							break;
						case 'E':
							RedirectBasedOnRole(ev, Constants.LOGIN,'E', "Tableau de bord " + userDetail.getUserName());
							break;
						default:
							break;
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
						e.printStackTrace();
					}
				} else {
					SetErrorMessage(errPassword, Constants.errIncorrectPassword);
					txtPassword.setText(Constants.String_Empty);
				}

			} else {
				SetErrorMessage(errUserName, Constants.errUserNameNotFound);
				txtPassword.setText(Constants.String_Empty);
				errPassword.setText(Constants.String_Empty);
			}
		}
	}

	/**
	 * checks for default checking
	 * @return boolean
	 */
	public boolean defaultCheck() {
		return (Common.IsNullOrEmpty(txtUserName.getText()) || Common.IsNullOrEmpty(txtPassword.getText())) ? true
				: false;
	}

	/**
	 * Reset Button 
	 * @param ev ev
	 */
	public void btnReset_Click(ActionEvent ev) {
		txtUserName.setText(Constants.String_Empty);
		txtPassword.setText(Constants.String_Empty);
		errUserName.setText(Constants.String_Empty);
		errPassword.setText(Constants.String_Empty);
	}

	/**
	 * Redirects to registrations
	 * @param ev ev
	 */
	public void RedirectToRegistration(ActionEvent ev) {
		try {
			RedirectBasedOnRole(ev, Constants.LOGIN, 'F', "Création de compte");

		} catch (Exception e) {
			e.getMessage();
		}
	}
        
        public void btn_ViewParking_Click(ActionEvent event){
            RedirectBasedOnRole(event, Constants.VISITOR, 'V', "Consulter les places");
        }
}
