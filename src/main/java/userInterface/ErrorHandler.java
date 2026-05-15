package userInterface;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;;


public class ErrorHandler
{
	 public static final int PREF_IMG_SIZE = 70;
	
	 public static void showError(Exception e) 
	 {
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText(e.getMessage());
	        ImageView imageView = new ImageView(new Image(ErrorHandler.class.getResource("/errorImage.png").toExternalForm()));
	        imageView.setFitWidth(PREF_IMG_SIZE);
	        imageView.setFitHeight(PREF_IMG_SIZE);
	        
	        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
	        alertStage.getIcons().add(new Image(ConfirmationPopUps.class.getResourceAsStream("/undertaleHeart.png")));
	        
	        alert.setGraphic(imageView);
	        alert.getDialogPane().getStylesheets().add(ErrorHandler.class.getResource("/darkMode.css").toExternalForm());
	        alert.showAndWait();
	 }
	 
	 public static void showError(String text) 
	 {
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText(text);
	        ImageView imageView = new ImageView(new Image(ErrorHandler.class.getResource("/errorImage.png").toExternalForm()));
	        imageView.setFitWidth(PREF_IMG_SIZE);
	        imageView.setFitHeight(PREF_IMG_SIZE);
	        
	        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
	        alertStage.getIcons().add(new Image(ConfirmationPopUps.class.getResourceAsStream("/undertaleHeart.png")));
	        
	        alert.setGraphic(imageView);
	        alert.getDialogPane().getStylesheets().add(ErrorHandler.class.getResource("/darkMode.css").toExternalForm());
	        alert.showAndWait();
	 }
	 
	 public static void showUnknownError() 
	 {
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Ha ocurrido un error, por favor, intentálo de nuevo");
	        ImageView imageView = new ImageView(new Image(ErrorHandler.class.getResource("/errorImage.png").toExternalForm()));
	        imageView.setFitWidth(PREF_IMG_SIZE);
	        imageView.setFitHeight(PREF_IMG_SIZE);
	        
	        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
	        alertStage.getIcons().add(new Image(ConfirmationPopUps.class.getResourceAsStream("/undertaleHeart.png")));
	        
	        alert.setGraphic(imageView);
	        alert.getDialogPane().getStylesheets().add(ErrorHandler.class.getResource("/darkMode.css").toExternalForm());
	        alert.showAndWait();
	 }
}
