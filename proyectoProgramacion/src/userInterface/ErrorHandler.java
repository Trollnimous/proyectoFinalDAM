package userInterface;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;;


public class ErrorHandler
{
	 public static void showError(Exception e) 
	 {
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText(e.getMessage());
	        alert.showAndWait();
	 }
	 
	 public static void showError(String text) 
	 {
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText(text);
	        alert.showAndWait();
	 }
	 
	 public static void showUnknownError() 
	 {
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Error");
	        alert.setHeaderText("Ha ocurrido un error, por favor, intentálo de nuevo");
	        alert.showAndWait();
	 }
}
