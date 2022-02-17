package controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import common.Common;
import common.Constants;
import dao.AdminAccessDAO;
import dao.ParkingDAO;
import dao.Interface.IAdminAccessDAO;
import dao.Interface.IParkingDAO;
import model.ParkingModel;
import model.UserProfileModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.util.Callback;
import model.ParkingCategoryModel;


public class BookingController extends BaseController {

	/* Private Variables */
	@FXML
	private ComboBox<String> cbeUser = new ComboBox<>();
	@FXML
	private ComboBox<String> cbeParkingSpace = new ComboBox<>();
	@FXML
	private Text txtCategory;
	@FXML
	private Text txtNoOfSpace;
	@FXML
	private Text txtSelectUser;
	@FXML
	private Text errMsg;
	@FXML
	private DatePicker toDate = new DatePicker();
	@FXML
	private Button btnBookParkingSpace;
	@FXML
	private TableView<ParkingModel> tblViewLog;

	private int parkingLotID_selected;

	private IAdminAccessDAO adminAccessDAO = new AdminAccessDAO();
	private IParkingDAO parkingDAO = new ParkingDAO();
        private List<ParkingCategoryModel> parkingCategories = parkingDAO.GetParkingCategory();
	private List<ParkingModel> parkingLog = parkingDAO.GetParkingLog();
	private List<UserProfileModel> adminUser = null;

	/**
	 * Loading view parking lot happens here
	 */
	public void LoadViewParkingLog() {
		tblViewLog.getItems().clear();
		if (getLoginUser().getRole() == 'C')
			tblViewLog.setItems(FXCollections.observableArrayList(parkingLog.stream()
					.filter(u -> u.getUserId() == getLoginUser().getUserId()).collect(Collectors.toList())));
		else
			tblViewLog.setItems(FXCollections.observableArrayList(parkingLog));
	}

	/**
	 * Sets for Customer
	 */
	public void SetForCustomer() {
		cbeUser.setVisible(false);
		txtSelectUser.setVisible(false);
	}

	/**
	 * Loads the user Details
	 */
	public void LoadUserDetails() {
		adminUser = adminAccessDAO.getUserDetails().stream().filter(u -> u.getRole() == 'C')
				.collect(Collectors.toList());
		adminUser.removeIf(u -> u.getUserName().equals(getLoginUser().getUserName()));

		cbeUser.getItems().clear();
		for (UserProfileModel m : adminUser) {
			cbeUser.getItems().add(m.getUserName());
		}
	}

	/**
	 * Loads Category Type
	 */
        public void LoadCategoryText() {
            cbeParkingSpace.getItems().clear();
            for (ParkingCategoryModel m : parkingCategories)
                    cbeParkingSpace.getItems().add(m.getDetails());

            cbeParkingSpace.valueProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                ParkingCategoryModel model = (parkingCategories.stream()
                        .filter(u -> u.getDetails().equals(cbeParkingSpace.getSelectionModel().getSelectedItem()))
                        .collect(Collectors.toList())).get(0);
                txtCategory.setText(model.getDescription());
                txtNoOfSpace.setText(model.getNoOfSpace() + "");
                parkingLotID_selected = model.getParkingCategoryId();
            });
	}

	/**
	 * Loads the Validation for Date Picker
	 */
	public void DatePickerValidation() {
		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);
						LocalDate currDate = LocalDate.now();
						if (item.isBefore(currDate.plusDays(0))) {
							setDisable(true);
							setStyle("-fx-background-color: #DCDCDC;");
						}
					}
				};
			}
		};
		toDate.setDayCellFactory(dayCellFactory);
	}

	/**
	 * Book a parking
	 */
	public void BookParking_Click() {
		int userId = getLoginUser().getUserId();

		if (getLoginUser().getRole() == 'E') {
			if (Common.IsNullOrEmpty((cbeUser.getSelectionModel().getSelectedItem()))) {
				errMsg.setText("Choisir un utilisateur");
				errMsg.getStyleClass().add("validationError");
				return;
			} else {
				userId = adminUser.stream()
						.filter(u -> u.getUserName().equals(cbeUser.getSelectionModel().getSelectedItem()))
						.collect(Collectors.toList()).get(0).getUserId();
			}
		}
		if (Common.IsNullOrEmpty(cbeParkingSpace.getSelectionModel().getSelectedItem())) {
			errMsg.setText("Choisir une place de parking");
			errMsg.getStyleClass().add("validationError");
		} else if (toDate.getValue() == null || Common.IsNullOrEmpty(toDate.getValue().toString())) {
			errMsg.setText("Renseigner la date de fin");
			errMsg.getStyleClass().add("validationError");
		} else {
			ParkingModel model = new ParkingModel();
                        model.setParkingLotId(parkingLotID_selected);
			model.setUserId(userId);
			Instant instant = toDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
			Date res = Date.from(instant);
			model.setToDate(res);
			model.setPrice(100);

			int result = parkingDAO.AddParkingBooking(model);
			if (result > 0) {
                                errMsg.getStyleClass().add("successMsg");
                                errMsg.setText("Réservation effectuée avec succès");
				btnBookParkingSpace.setDisable(true);
			} else {
				errMsg.setText("Une erreur s'est produite, Veuillez réessayer");
				errMsg.getStyleClass().add("validationError");
			}
		}
	}

	/**
	 * Action method for back link
	 * 
	 * @param ev
	 *            event
	 */
	public void hlinkBack_Click(ActionEvent ev) {
		if (getLoginUser().getRole() == 'C')
			RedirectBasedOnRole(ev, Constants.PARKINGLOTDETAILS, 'C', getLoginUser().getUserName() + " Home");
		else if (getLoginUser().getRole() == 'E')
			RedirectBasedOnRole(ev, Constants.LOGIN,'E', "Employee " + getLoginUser().getUserName() + " Home");
		else
			RedirectBasedOnRole(ev, Constants.PARKINGLOTDETAILS, 'A', "Admin Home");
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
}
