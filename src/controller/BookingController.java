package controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import common.Common;
import common.Constants;
import dao.AdminAccessDAO;
import dao.ParkingDAO;
import dao.Interface.IAdminAccessDAO;
import dao.Interface.IParkingDAO;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;
import java.util.stream.IntStream;
import javafx.beans.value.ChangeListener;
import model.ParkingModel;
import model.UserProfileModel;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.converter.LocalTimeStringConverter;
import model.ParkingCategoryModel;


public class BookingController extends BaseController implements Initializable {

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
	private Button btnBookParkingSpace;
	@FXML
	private TableView<ParkingModel> tblViewLog;

	private int parkingLotCategoryID_selected;

	private IAdminAccessDAO adminAccessDAO = new AdminAccessDAO();
	private IParkingDAO parkingDAO = new ParkingDAO();
        private List<ParkingCategoryModel> parkingCategories = parkingDAO.GetParkingCategoryWithFreeSpace();
	private List<ParkingModel> parkingLog = parkingDAO.GetParkingLog();
	private List<UserProfileModel> adminUser = null;

        @FXML
        private Spinner<LocalTime> fromTime;
        @FXML
        private Spinner<LocalTime> toTime;
        @FXML
        private DatePicker fromDate = new DatePicker();
        @FXML
	private DatePicker toDate = new DatePicker();
        @FXML
        private CheckBox cbeNow = new CheckBox();
        @FXML
        private ComboBox cbePlaceID = new ComboBox();
        @FXML
        private Text txtMsg;

        
        public void initialize(URL arg0,ResourceBundle arg1){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            
            cbeNow.selectedProperty().addListener(
                new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        fromDate.setVisible(!newValue);
                        fromTime.setVisible(!newValue);
                    }
                }
            );
                
            SpinnerValueFactory value = new SpinnerValueFactory<LocalTime>() {
                {
                    setConverter(new LocalTimeStringConverter(formatter,null));
                }
                @Override
                public void decrement(int steps) {
                    steps = 30;
                    if (getValue() == null)
                        setValue(LocalTime.now());
                    else {
                        LocalTime time = (LocalTime) getValue();
                        setValue(time.minusMinutes(steps));
                    }
                }

                @Override
                public void increment(int steps) {
                    steps = 30;
                    if (this.getValue() == null)
                        setValue(LocalTime.now());
                    else {
                        LocalTime time = (LocalTime) getValue();
                        setValue(time.plusMinutes(steps));
                    }
                }
            };    
            
            value.setValue(LocalTime.now().withMinute(0));
            fromTime.setValueFactory(value);
            
            SpinnerValueFactory toValue = new SpinnerValueFactory<LocalTime>() {
                {
                    setConverter(new LocalTimeStringConverter(formatter,null));
                }
                @Override
                public void decrement(int steps) {
                    steps = 30;
                    if (getValue() == null)
                        setValue(LocalTime.now());
                    else {
                        LocalTime time = (LocalTime) getValue();
                        setValue(time.minusMinutes(steps));
                    }
                }

                @Override
                public void increment(int steps) {
                    steps = 30;
                    if (this.getValue() == null)
                        setValue(LocalTime.now());
                    else {
                        LocalTime time = (LocalTime) getValue();
                        setValue(time.plusMinutes(steps));
                    }
                }
            }; 
            toValue.setValue(LocalTime.now().withMinute(0).plusMinutes(30));
            toTime.setValueFactory(toValue);
        }
        
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
                txtNoOfSpace.setText(model.getNoOfFreeSpace() + "");
                parkingLotCategoryID_selected = model.getParkingCategoryId();
                
                cbePlaceID.getItems().setAll(
                    IntStream.rangeClosed(1,model.getNoOfSpace()).boxed().collect(Collectors.toList())
                );
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
                fromDate.setDayCellFactory(dayCellFactory);
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
                    errMsg.setText("Choisir une zone de stationnement");
                    errMsg.getStyleClass().add("validationError");
            }else if(cbePlaceID.getValue() ==  null){
                    errMsg.setText("Choisir un numéro de place");
                    errMsg.getStyleClass().add("validationError");
            } 
            else if (toDate.getValue() == null || Common.IsNullOrEmpty(toDate.getValue().toString())) {
                    errMsg.setText("Renseigner correctement les dates");
                    errMsg.getStyleClass().add("validationError");
            } else {
                //int parkingLotID = parkingDAO.GetParkingLotId(parkingLotCategoryID_selected);
                int parkingLotID = Integer.parseInt(cbePlaceID.getValue().toString());
                ParkingModel model = new ParkingModel();

                model.setParkingLotCategoryId(parkingLotCategoryID_selected);
                model.setParkingLotId(parkingLotID);
                model.setUserId(userId);
                Instant instant = toDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
                Timestamp res = Timestamp.from(instant);
                model.setToDate(res);
                model.setPrice(100);
                model.setStatus("En Cours");

                //ToDate
                String toTimeStamp = toDate.getValue().toString() + " " +toTime.getValue().toString();
                model.setToDate(Timestamp.valueOf(toTimeStamp));

                //FromDate
                if(cbeNow.isSelected())
                    model.setFromDate(Timestamp.from(Instant.now()));
                else{
                    String fromTimeStamp = fromDate.getValue().toString() + " " +fromTime.getValue().toString();
                    model.setFromDate(Timestamp.valueOf(fromTimeStamp));
                }

                boolean status;
                status = parkingDAO.CheckParkingLotStatus(model);
                
                //Place déjà réservée pour cet intervalle
                if(!status){
                    errMsg.setText("La place est déjà réservée pour cet intervalle de temps");
                    errMsg.getStyleClass().add("validationError");
                }else{
                    int result = parkingDAO.AddParkingBooking(model);
                    if (result > 0) {
                            //Update parkingLot status
                            boolean r;
                            r = parkingDAO.UpdateParkingLotStatus(parkingLotID,parkingLotCategoryID_selected);
                            if(r) System.out.println("Succès update"); else System.out.println("Echec update");

                            errMsg.setText("Réservation effectuée avec succès");
                            errMsg.getStyleClass().add("successMsg");
                            btnBookParkingSpace.setDisable(true);
                    } else {
                            errMsg.setText("Une erreur s'est produite, Veuillez réessayer");
                            errMsg.getStyleClass().add("validationError");
                    }    
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
			RedirectBasedOnRole(ev, Constants.PARKINGLOTDETAILS, 'C', getLoginUser().getUserName() + " Acceuil");
		else if (getLoginUser().getRole() == 'E')
			RedirectBasedOnRole(ev, Constants.LOGIN,'E', "Employee " + getLoginUser().getUserName() + " Acceuil");
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
            RedirectBasedOnRole(ev, Constants.LOGOUT, '\0', "Connectez-vous");
	}
        
        public void btnDeleteLog_Click(ActionEvent ev){
                ParkingModel selectedLog = this.tblViewLog.getSelectionModel().getSelectedItem();
		if (selectedLog == null) {
			txtMsg.setText("Choisir une réservation");
			txtMsg.getStyleClass().add("validationError");
		}
		else{
			if(parkingDAO.DeleteParkingLog(selectedLog.getBookid())){
				LoadViewParkingLog();
                                txtMsg.setText("L'opération est un succès");
				txtMsg.getStyleClass().add("successMsg");
			}
			else{
				txtMsg.setText("Une erreur s'est produite, Veuillez réessayer plus-tard");
				txtMsg.getStyleClass().add("validationError");
			}
		}
        }
        
        public void btnTerminateLog_Click(ActionEvent event){
            ParkingModel selectedLog = this.tblViewLog.getSelectionModel().getSelectedItem();
		if (selectedLog == null) {
			txtMsg.setText("Choisir une réservation");
			txtMsg.getStyleClass().add("validationError");
		}
		else{
                    if(parkingDAO.UpdateParkingLogStatus(selectedLog.getBookid())){
                            LoadViewParkingLog();
                            txtMsg.setText("L'opération est un succès");
                            txtMsg.getStyleClass().add("successMsg");
                    }
                    else{
                            txtMsg.setText("Une erreur s'est produite, Veuillez réessayer plus-tard");
                            txtMsg.getStyleClass().add("validationError");
                    }
		}
        }
        
}
