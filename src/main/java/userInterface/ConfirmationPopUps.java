package userInterface;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ConfirmationPopUps
{
	public static final int PREF_IMG_SIZE = 70;
	
	public static boolean confirmAppExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ImageView imageView = new ImageView(new Image(ConfirmationPopUps.class.getResource("/exitAlertImage.jpg").toExternalForm()));
        imageView.setFitWidth(PREF_IMG_SIZE);
        imageView.setFitHeight(PREF_IMG_SIZE);
        alert.setGraphic(imageView);
        alert.setTitle("Salir del programa");
        alert.setHeaderText("");
        alert.setContentText("¿Segur@ que quieres salir?");

        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(ConfirmationPopUps.class.getResourceAsStream("/undertaleHeart.png")));
        
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(ConfirmationPopUps.class.getResource("/darkMode.css").toExternalForm());

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
	
	public static boolean confirmCloseSession() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        ImageView imageView = new ImageView(new Image(ConfirmationPopUps.class.getResource("/exitAlertImage.jpg").toExternalForm()));
        imageView.setFitWidth(PREF_IMG_SIZE);
        imageView.setFitHeight(PREF_IMG_SIZE);
        alert.setGraphic(imageView);
        alert.setTitle("Cerrar sesión");
        alert.setHeaderText("");
        alert.setContentText("¿Segur@ que quieres cerrar sesión?");

        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(ConfirmationPopUps.class.getResourceAsStream("/undertaleHeart.png")));
        
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(ConfirmationPopUps.class.getResource("/darkMode.css").toExternalForm());

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
	
	public static boolean userDeletionConfirmationMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        ImageView imageView = new ImageView(new Image(ConfirmationPopUps.class.getResource("/informationImage.jpg").toExternalForm()));
        imageView.setFitWidth(PREF_IMG_SIZE);
        imageView.setFitHeight(PREF_IMG_SIZE);
        alert.setGraphic(imageView);
        alert.setTitle("Operación completada");
        alert.setHeaderText("");
        alert.setContentText("Usuario borrado correctamente");

        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(ConfirmationPopUps.class.getResourceAsStream("/undertaleHeart.png")));
        
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(ConfirmationPopUps.class.getResource("/darkMode.css").toExternalForm());

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
	
	public static boolean userPasswordChangedConfirmationMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        ImageView imageView = new ImageView(new Image(ConfirmationPopUps.class.getResource("/informationImage.jpg").toExternalForm()));
        imageView.setFitWidth(PREF_IMG_SIZE);
        imageView.setFitHeight(PREF_IMG_SIZE);
        alert.setGraphic(imageView);
        alert.setTitle("Operación completada");
        alert.setHeaderText("");
        alert.setContentText("La contraseña se ha cambiado correctamente");

        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(ConfirmationPopUps.class.getResourceAsStream("/undertaleHeart.png")));
        
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(ConfirmationPopUps.class.getResource("/darkMode.css").toExternalForm());

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}
