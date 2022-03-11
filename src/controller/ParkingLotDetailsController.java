package controller;

import model.ParkingLot;
import model.ParkingCategoryModel;
import dao.Interface.IParkingDAO;
import dao.ParkingDAO;
import common.Common;
import common.Constants;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;


public class ParkingLotDetailsController extends BaseController {

	/* Private Variables */
	@FXML
	private Accordion accordion;
	@FXML
	private TitledPane firstAccordion;
	@FXML
	private TitledPane thirdAccordion;
	@FXML
	private TextField txtCategory;
        @FXML
	private TextField txtDetails;
	@FXML
	private Text txtCategoryMsg;
	@FXML
	private TextField txtNoOfSpace;
	@FXML
	private Text errParking;
	@FXML
	private TableView<ParkingCategoryModel> tblParkingDetails;
	@FXML
	private RadioButton rbUpdate;
	@FXML
	private RadioButton rbDelete;
	@FXML
	private TextField txtUpdate;
	@FXML
	private Text txterror;
	@FXML
	private Button btnUpdateParking;
        @FXML
        private Hyperlink hlinkLogout;
        
	public final ToggleGroup group = new ToggleGroup();

	private IParkingDAO parkingDAO = new ParkingDAO();

	/**
	 * Set Accordion Open
	 */
	public void SetAccordionOpen() {
		accordion.setExpandedPane(firstAccordion);
		rbUpdate.setToggleGroup(group);
		rbDelete.setToggleGroup(group);
		rbUpdate.setSelected(true);

		group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			if (rbDelete.isSelected()) {
				txtUpdate.setVisible(false);
				txterror.setText("");
			} else {
				txtUpdate.setVisible(true);
				txtUpdate.setPromptText("Nouvelle valeur");
			}
		});
	}

	/**
	 * Set Customer view
	 */
	public void CustomerView() {
		firstAccordion.setVisible(false);
		accordion.setExpandedPane(thirdAccordion);
		thirdAccordion.setVisible(true);
		thirdAccordion.setDisable(false);
		thirdAccordion.setText("Informations sur le Parking");
		rbUpdate.setVisible(false);
		rbDelete.setVisible(false);
		txtUpdate.setVisible(false);
		btnUpdateParking.setVisible(false);
	}
        
        public void VisitorView() {
		firstAccordion.setVisible(false);
		accordion.setExpandedPane(thirdAccordion);
		thirdAccordion.setVisible(true);
		thirdAccordion.setDisable(false);
		thirdAccordion.setText("Informations sur le Parking");
		rbUpdate.setVisible(false);
		rbDelete.setVisible(false);
		txtUpdate.setVisible(false);
		btnUpdateParking.setVisible(false);
                hlinkLogout.setVisible(false);
                txterror.getStyleClass().add("validationError");
                txterror.setText("Connectez-vous afin d'effectuer une réservation");
	}

//	/**
//	 * Load the Category
//	 */
//	public void LoadCategory() {
//		cbeCategory.getItems().clear();
//		List<ParkingCategoryModel> categoryList = parkingDAO.GetParkingCategory();
//		for (ParkingCategoryModel m : categoryList) {
//			cbeCategory.getItems().add(m.getDescription());
//		}
//	}

	/**
	 * Loading Parking Category Table
	 */
	public void LoadParkingCategoryTable() {
		tblParkingDetails.getItems().clear();
		tblParkingDetails.setItems(FXCollections.observableArrayList(parkingDAO.GetParkingCategoryWithFreeSpace()));
	}

	/**
	 * add category button
	 * 
	 * @param ev
	 *            ev
	 */
	public void btnAddCategory_Click(ActionEvent ev) {
		if (!Common.IsNullOrEmpty(txtCategory.getText()) && !Common.IsNullOrEmpty(txtNoOfSpace.getText())) {
			ParkingCategoryModel category = new ParkingCategoryModel();
			category.setDescription(txtCategory.getText());
                        category.setDetails(txtDetails.getText());
                        category.setNoOfSpace(Integer.parseInt(txtNoOfSpace.getText()));
			int result = parkingDAO.AddCategory(category);
			if (result > 0) {
                                //Add parking lots
                                ParkingLot parkingLot = new ParkingLot();
                                parkingLot.setParkingLotCategoryId(parkingDAO.GetMaxParkingId());
                                parkingLot.setStatus("A");
                                
                                for (int i = 1; i <= Integer.parseInt(txtNoOfSpace.getText()); i++){
                                    parkingLot.setParkingLotId(i);
                                    parkingDAO.AddParking(parkingLot);
                                }
                                
                                txtCategoryMsg.getStyleClass().add("successMsg");
                                txtCategoryMsg.setText("La catégorie " + txtCategory.getText() + " a été ajoutée avec succès");
				txtCategory.setText(Constants.String_Empty);
                                txtDetails.setText(Constants.String_Empty);
                                txtNoOfSpace.clear();
				LoadParkingCategoryTable();
			} else {
				txtCategoryMsg.getStyleClass().add("validationError");
				txtCategoryMsg.setText(txtCategory.getText() + " n'a pas été ajouté : Veuillez réessayer plus tard");
			}
		} else {
			txtCategoryMsg.getStyleClass().add("validationError");
			SetErrorMessage(txtCategory.getText(), txtCategoryMsg, Constants.errCategoryEmpty);
		}
	}
        
	/**
	 * validate category
	 * 
	 * @param ev
	 *            ev
	 */
	public void ValidateCategory(KeyEvent ev) {
		txtCategoryMsg.getStyleClass().add("validationError");
		ValidateRegex(txtCategory.getText(), txtCategoryMsg, Constants.Parking_regex, Constants.errCategoryEmpty,
				Constants.errCategory_NoSpecial);
	}
        
        /**
	 * validate details
	 * 
	 * @param ev
	 *            ev
	 */
	public void ValidateDetails(KeyEvent ev) {
		txtCategoryMsg.getStyleClass().add("validationError");
		ValidateRegex(txtDetails.getText(), txtCategoryMsg, Constants.Parking_regex, Constants.errDetailsEmpty,
				Constants.errCategory_NoSpecial);
	}

	/**
	 * Validate no of space
	 * 
	 * @param ev
	 *            ev
	 */
	public void ValidateNoOfSpace(KeyEvent ev) {
		ValidateRegex(txtNoOfSpace.getText(), errParking, Constants.ParkingSpace_regex, Constants.noOfSpaceEmpty,
				Constants.onlyNumberAllowed);
	}

	/**
	 * Action method for back link
	 * 
	 * @param ev
	 *            event
	 */
	public void hlinkBack_Click(ActionEvent ev) {
                if (getLoginUser() == null)
			RedirectBasedOnRole(ev, Constants.LOGOUT, '\0', "Login");
                else if (getLoginUser().getRole() == 'C')
			RedirectBasedOnRole(ev, Constants.PARKINGLOTDETAILS, 'C', getLoginUser().getUserName() + " Acceuil");
		else if (getLoginUser().getRole() == 'E')
			RedirectBasedOnRole(ev, Constants.LOGIN, 'E', "Employee " + getLoginUser().getUserName() + " Acceuil");
                
                else
			RedirectBasedOnRole(ev, Constants.PARKINGLOTDETAILS, 'A', "Panneau d'administration");
	}

	/**
	 * Action for logout button
	 * 
	 * @param ev
	 *            event
	 */
	public void hlinkLogout_Click(ActionEvent ev) {
		setLoginUser(null);
		RedirectBasedOnRole(ev, Constants.LOGOUT, '\0', "Login");
	}

	/**
	 * Add Parking Space
	 * 
	 * @param ev
	 *            ev
	 */
//	public void btnAddParkingSpace_Click(ActionEvent ev) {
//                if (!Common.IsNullOrEmpty(txtNoOfSpace.getText())) {
//                        ParkingLot parking = new ParkingLot();
////                        parking.setDescription(txtDescription.getText());
//                        parking.setNoOfSpace(Integer.parseInt(txtNoOfSpace.getText()));
//                        parking.setParkingCategoryId(parkingDAO.GetCategoryId(cbeCategory.getValue() == null ? "A" : cbeCategory.getValue()));
//                        parking.setStatus("A");
//
//                        int result = parkingDAO.AddParking(parking);
//                        if (result > 0) {
//                                errParking.getStyleClass().add("successMsg");
//                                errParking.setText(txtDescription.getText() + " a été ajouté avec succès");
//                                LoadParkingTable();
//                        } else {
//                                errParking.setText("Une erreur s'est produite, Veuillez réessayer plus-tard");
//                        }
//                } else
//                        SetErrorMessage(txtDescription.getText(), errParking, Constants.noOfSpaceEmpty);
//	}

	/**
	 * Update Parking Lot Space
	 */
	public void UpdateParkingLotSpace() {
		if (rbUpdate.isSelected()) {
			int updateValue;
			if (this.tblParkingDetails.getSelectionModel().getSelectedItem() == null) {
				txterror.setText("Veuillez choisir une catégorie");
				txterror.getStyleClass().add("validationError");
			} else if (Common.IsNullOrEmpty(txtUpdate.getText())) {
				txterror.setText("Veullez entrer une valeur");
				txterror.getStyleClass().add("validationError");
			} else {
				try {
                                    updateValue = Integer.parseInt(txtUpdate.getText());
                                    ParkingCategoryModel parkingCategory = new ParkingCategoryModel();
                                    int oldValue = this.tblParkingDetails.getSelectionModel().getSelectedItem().getNoOfSpace();



                                        parkingCategory.setParkingCategoryId(
                                                        this.tblParkingDetails.getSelectionModel().getSelectedItem().getParkingCategoryId());
                                        parkingCategory.setNoOfSpace(updateValue);

                                        if (parkingDAO.UpdateParkingCategory(parkingCategory)) {

                                            //New value is superior to the actual NoOfSpace
                                            if(updateValue > oldValue){
                                                //Add Parking Lots
                                                ParkingLot parkingLot = new ParkingLot();
                                                parkingLot.setParkingLotCategoryId(parkingCategory.getParkingCategoryId());
                                                parkingLot.setStatus("A");

                                                for (int i = oldValue + 1; i <= updateValue; i++){
                                                    parkingLot.setParkingLotId(i);
                                                    parkingDAO.AddParking(parkingLot);
                                                }

                                                LoadParkingCategoryTable();
                                                txterror.getStyleClass().add("successMsg");
                                                txterror.setText("Mise à jour réussie");
                                            }
                                            else if(updateValue < oldValue){
                                                //Delete Parking Lots
                                                for (int i = oldValue; i > updateValue; i--){
                                                    parkingDAO.DeleteParkingLot(this.tblParkingDetails.getSelectionModel().getSelectedItem().getParkingCategoryId(),i);
                                                }
                                                
                                                LoadParkingCategoryTable();
                                                txterror.getStyleClass().add("successMsg");
                                                txterror.setText("Mise à jour réussie");
                                            }
                                            else if(updateValue == oldValue){
                                                txterror.getStyleClass().add("successMsg");
                                                txterror.setText("Aucun changement");
                                            }
                                        } else {
                                                txterror.setText("Une erreur s'est produite, Veuillez réessayer plus tard");
                                                txterror.getStyleClass().add("validationError");
                                        }

				} catch (NumberFormatException ex) {
					txterror.setText("Veullez entrer une valeur");
					txterror.getStyleClass().add("validationError");
				}
			}
		} else if (rbDelete.isSelected()) {
			if (this.tblParkingDetails.getSelectionModel().getSelectedItem() == null) {
				txterror.setText("Veuillez choisir une catégorie");
				txterror.getStyleClass().add("validationError");
			} else {
				try {
					ParkingCategoryModel parkingCategory = new ParkingCategoryModel();
					parkingCategory.setParkingCategoryId(
							this.tblParkingDetails.getSelectionModel().getSelectedItem().getParkingCategoryId());
                                        
					if (parkingDAO.DeleteParkingCategory(parkingCategory)) {
                                                //Delete all the parkinglots for the category    
                                                parkingDAO.DeleteParkingLot(parkingCategory.getParkingCategoryId());
                                                
                                                LoadParkingCategoryTable();
                                                txterror.getStyleClass().add("successMsg");
						txterror.setText("Suppression reussie");
					} else {
						txterror.setText("Une erreur s'est produite, Veuillez réessayer plus tard");
						txterror.getStyleClass().add("validationError");
					}
				} catch (NumberFormatException ex) {
					txterror.setText("Veuillez renseigner une valeur correcte");
					txterror.getStyleClass().add("validationError");
				}
			}
		}
	}
}
