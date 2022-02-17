package home;

import common.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
	public static Stage stage;
        Image icon = new Image(getClass().getResourceAsStream(Constants.ICON_URL));
        
	@Override
	public void start(Stage primaryStage) {
		try {
			stage = primaryStage;
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource(Constants.LOGIN_URL));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(Constants.CSS_URL);
			stage.setTitle("Connectez-vous");
                        stage.getIcons().add(icon);
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}