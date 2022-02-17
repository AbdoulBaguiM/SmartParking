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



public class AdminAccessController extends BaseController {
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
	private Button btnGrandAccess;
	@FXML
	private Button btnRevokeAccess;

	private IAdminAccessDAO adminAccessDAO = new AdminAccessDAO();

	/**
	 * Loads the Admin type user
	 */
	public void LoadAdminUser() {
		List<UserProfileModel> adminUser = adminAccessDAO.getUserDetails().stream().filter(u -> u.getRole() == 'A')
				.collect(Collectors.toList());
		adminUser.removeIf(u -> u.getUserName().equals(getLoginUser().getUserName()));
		if (adminUser.size() == 0){
			txtMsgRevoke.setText("Aucun admministrateur à charger");
			txtMsgRevoke.getStyleClass().add("validationError");
			btnRevokeAccess.setVisible(false);
			tblAdminAccessView.setItems(null);
		}
		else{
			tblAdminAccessView.setItems(FXCollections.observableArrayList(adminUser));
			txtMsgRevoke.setText(Constants.String_Empty);
			btnRevokeAccess.setVisible(true);
		}
	}

	/**
	 * Loads the Employee Type user
	 */
	public void LoadEmployeeUser() {
		List<UserProfileModel> employeeList = adminAccessDAO.getUserDetails().stream().filter(u -> u.getRole() == 'E')
				.collect(Collectors.toList());
		if (employeeList.size() == 0){
			txtMsg.setText("Aucun administrateur à charger");
			txtMsg.getStyleClass().add("validationError");
			btnGrandAccess.setVisible(false);
			tblEmployeeAccessView.setItems(null);
		}
		else{
			tblEmployeeAccessView.setItems(FXCollections.observableArrayList(employeeList));
			txtMsg.setText(Constants.String_Empty);
			btnGrandAccess.setVisible(true);
		}
	}

	/** 
	 * Action method for back link
	 * @param ev event
	 */
	public void hlinkBack_Click(ActionEvent ev) {
		RedirectBasedOnRole(ev, Constants.ADMINACCESS, 'A', "Admin Home");
	}

	/**
	 * Action for logout button
	 * @param ev event
	 */
	public void hlinkLogout_Click(ActionEvent ev) {
		setLoginUser(null);
		RedirectBasedOnRole(ev, Constants.LOGOUT, '\0', "Login");
	}

	/**
	 * Funtion for granting acces to employee and user
	 * @param ev event
	 */
	public void btnGrantAccess_Click(ActionEvent ev) {
		UserProfileModel selectedUser = this.tblEmployeeAccessView.getSelectionModel().getSelectedItem();
		if (selectedUser == null) {
			txtMsg.setText("Choisir un utilisateur");
			txtMsg.getStyleClass().add("validationError");
		}
		else{
			if(adminAccessDAO.UpdateUserDetail(selectedUser.getUserId(), "A")){
				LoadAdminUser();
				LoadEmployeeUser();
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
	public void btnRevokeAccess_Click(ActionEvent ev) {
		UserProfileModel selectedUser = this.tblAdminAccessView.getSelectionModel().getSelectedItem();
		if (selectedUser == null) {
			txtMsgRevoke.setText("Choisir un utilisateur");
			txtMsgRevoke.getStyleClass().add("validationError");
		}
		else{
			if(adminAccessDAO.UpdateUserDetail(selectedUser.getUserId(), "E")){
				LoadAdminUser();
				LoadEmployeeUser();
			}
			else{
				txtMsgRevoke.setText("Une erreur s'est produite, Veuillez réessayer plus-tard");
				txtMsgRevoke.getStyleClass().add("validationError");
			}
		}
	}
}
