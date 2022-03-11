package controller;

import java.util.List;
import java.util.stream.Collectors;

import common.Constants;
import dao.AdminAccessDAO;
import dao.Interface.IAdminAccessDAO;
import model.UserProfileModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;



public class AdminManageController extends BaseController {
	/* Private variables */
	
	@FXML
	private Hyperlink hlinkBack;
	@FXML
	private Hyperlink hlinkLogout;
	@FXML
	private Text txtMsg;
	@FXML
	private Text txtMsgRevoke;
	@FXML
	private TableView<UserProfileModel> tblEmployeeAccessView;
	@FXML
	private TableView<UserProfileModel> tblAdminAccessView;
	@FXML
	private Button btnDeleteUser;
	@FXML
	private Button btnDeleteEmploye;

	private IAdminAccessDAO adminAccessDAO = new AdminAccessDAO();

	/**
	 * Loads the users
	 */
	public void LoadUser() {
		List<UserProfileModel> userList = adminAccessDAO.getUserDetails().stream().filter(u -> u.getRole() == 'C')
				.collect(Collectors.toList());
		userList.removeIf(u -> u.getUserName().equals(getLoginUser().getUserName()));
		if (userList.size() == 0){
			txtMsgRevoke.setText("Aucun client à charger");
			txtMsgRevoke.getStyleClass().add("validationError");
			btnDeleteEmploye.setVisible(false);
			tblAdminAccessView.setItems(null);
		}
		else{
			tblAdminAccessView.setItems(FXCollections.observableArrayList(userList));
			txtMsgRevoke.setText(Constants.String_Empty);
			btnDeleteEmploye.setVisible(true);
		}
	}

	/**
	 * Loads the Employee Type user
	 */
	public void LoadEmployeeUser() {
		List<UserProfileModel> employeeList = adminAccessDAO.getUserDetails().stream().filter(u -> u.getRole() == 'E')
				.collect(Collectors.toList());
		if (employeeList.size() == 0){
			txtMsg.setText("Aucun employé à charger");
			txtMsg.getStyleClass().add("validationError");
			btnDeleteUser.setVisible(false);
			tblEmployeeAccessView.setItems(null);
		}
		else{
			tblEmployeeAccessView.setItems(FXCollections.observableArrayList(employeeList));
			txtMsg.setText(Constants.String_Empty);
			btnDeleteUser.setVisible(true);
		}
	}

	/** 
	 * Action method for back link
	 * @param ev event
	 */
	public void hlinkBack_Click(ActionEvent ev) {
		RedirectBasedOnRole(ev, Constants.ADMINACCESS, 'A', "Panneau d'administration");
	}

	/**
	 * Action for logout button
	 * @param ev event
	 */
	public void hlinkLogout_Click(ActionEvent ev) {
		setLoginUser(null);
		RedirectBasedOnRole(ev, Constants.LOGOUT, '\0', "Connectez-vous");
	}

	/**
	 * Funtion for granting acces to employee and user
	 * @param ev event
	 */
	public void btnDeleteUser_Click(ActionEvent ev) {
		UserProfileModel selectedUser = this.tblEmployeeAccessView.getSelectionModel().getSelectedItem();
		if (selectedUser == null) {
			txtMsg.setText("Choisir un utilisateur");
			txtMsg.getStyleClass().add("validationError");
		}
		else{
			if(adminAccessDAO.DeleteUser(selectedUser.getUserId())){
				LoadUser();
				LoadEmployeeUser();
                                txtMsg.setText("L'opération est un succès");
				txtMsg.getStyleClass().add("successMsg");
			}
			else{
				txtMsg.setText("erreur s'est produite, Veuillez réessayer plus-tard");
				txtMsg.getStyleClass().add("validationError");
			}
		}
	}
	
	/**
	 * Function to revoke the access
	 * @param ev event
	 */
	public void btnDeleteEmploye_Click(ActionEvent ev) {
		UserProfileModel selectedUser = this.tblAdminAccessView.getSelectionModel().getSelectedItem();
		if (selectedUser == null) {
			txtMsgRevoke.setText("Choisir un employe");
			txtMsgRevoke.getStyleClass().add("validationError");
		}
		else{
			if(adminAccessDAO.DeleteUser(selectedUser.getUserId())){
				LoadUser();
				LoadEmployeeUser();
                                txtMsgRevoke.setText("L'opération est un succès");
				txtMsgRevoke.getStyleClass().add("successMsg");
			}
			else{
				txtMsgRevoke.setText("Une erreur s'est produite, Veuillez réessayer plus-tard");
				txtMsgRevoke.getStyleClass().add("validationError");
			}
		}
	}
}
