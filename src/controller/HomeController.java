package controller;

import common.Constants;
import iot.Server;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;


public class HomeController extends BaseController {

	/* Private variables */
	@FXML
	private Text lblWelcome;
	@FXML
	private Text txtAdminSuccess;
	@FXML
	private Button btnAddCustomer;
	@FXML
	private Button btnAddEmployee;
	@FXML
	private Button btnAccessButton;
	@FXML
	private Button btnViewParkingSpace;
	@FXML
	private Button btnViewParkingLog;
	@FXML
	private Hyperlink hlinkLogout;
        @FXML
        private Text txtTemperature;

        
	/**
	 * Sets a welcome message
	 * @param msg msg
	 * @param successMsg successMsg
	 */
	public void SetWelcome(String msg, String successMsg) {
		lblWelcome.setText(msg);
		txtAdminSuccess.setText(successMsg);
	}

	/* Admin Related Functions */
	
	/**
	 * Add Customer event
	 * @param ev ev
	 */
	public void btnAddCustomer_Click(ActionEvent ev) {
		RedirectBasedOnRole(ev, Constants.ADMINHOME, 'C', "Créer un compte");
	}
	
	/**
	 * Add Employee event
	 * @param ev ev
	 */
	public void btnAddEmployee_Click(ActionEvent ev) {
		RedirectBasedOnRole(ev, Constants.ADMINHOME, 'E', "Ajouter un employé");
	}

	/**
	 * Access event
	 * @param ev ev
	 */
	public void btnAccessButton_Click(ActionEvent ev) {
		RedirectBasedOnRole(ev, Constants.ADMINHOME, 'G', "Accorder / Revoquer Accès administrateur");
	}

	/**
	 * View Parking Space event
	 * @param ev ev
	 */
	public void btnViewParkingSpace_Click(ActionEvent ev) {
		RedirectBasedOnRole(ev, Constants.ADMINHOME, 'A', "Consulter le parking");
	}

	/**
	 * Add parking Log event
	 * @param ev ev
	 */
	public void btnViewParkingLog_Click(ActionEvent ev) {
		RedirectBasedOnRole(ev, Constants.ADMINHOME, 'V', "Voir les réservations");
	}

	/* Customer Related Functions */
	
	/**
	 * Edit profile event
	 * @param ev ev
	 */
	public void btnEditProfile_Click(ActionEvent ev) {
		RedirectBasedOnRole(ev, Constants.CUSTOMEMR, 'E', "Modifier mon profil");
	}

	/**
	 * Customer view parking event
	 * @param ev ev
	 */
	public void btnCustViewParkingSpace_Click(ActionEvent ev) {
		RedirectBasedOnRole(ev, Constants.CUSTOMEMR, 'C', "Consulter les places");
	}

	/**
	 * Book parking space event
	 * @param ev ev
	 */
	public void btnBookParkingSpace_Click(ActionEvent ev) {
		RedirectBasedOnRole(ev, Constants.CUSTOMEMR, 'B', "Faire une réservation");
	}

	/**
	 * view parking event
	 * @param ev ev
	 */
	public void btnViewLog_Click(ActionEvent ev) {
		RedirectBasedOnRole(ev, Constants.CUSTOMEMR, 'V', "Consulter mes réservations");
	}

	/**
	 * Action for logout button
	 * @param ev event
	 */
	public void Logout_Click(ActionEvent ev) {
		setLoginUser(null);
		RedirectBasedOnRole(ev, Constants.LOGOUT, '\0', "Connectez-vous");
	}
        
        public void btnAccountManagement_Click(ActionEvent ev) {
		RedirectBasedOnRole(ev, Constants.ADMINMANAGE, 'G', "Gestion des comptes");
	}



}
