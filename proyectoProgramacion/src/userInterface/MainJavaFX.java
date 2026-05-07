package userInterface;

import appContext.AppContext;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import session.Session;
import session.exceptions.LoginFailedException;

public class MainJavaFX extends Application 
{
	private static final String LOG_IN_EMAIL_LABEL_TEXT = "Introduce tu email:";
	private static final String LOG_IN_PASSWORD_LABEL_TEXT = "Introduce tu contraseña:";
	
	private static final String SIGN_UP_EMAIL_LABEL_TEXT = "Introduce un email:";
	private static final String SIGN_UP_USERNAME_LABEL_TEXT = "Introduce tu nombre de usuario:";
	private static final String SIGN_UP_PASSWORD_LABEL_TEXT = "Introduce tu contraseña:";
	private static final String SIGN_UP_PASSWORD_CONFIRMATION_LABEL_TEXT = "Repite la contraseña:";
	private static final String SIGN_UP_SEX_LABEL_TEXT = "Cual es tu sexo:";
	private static final String SIGN_UP_DATE_OF_BIRTH_LABEL_TEXT = "Introduce tu fecha de nacimiento:";
	
	private static final String EMAIL_TEXT_FIELD_TEXT = "Email";
	private static final String PASSWORD_PASSWORD_FIELD_TEXT = "Contraseña";
	private static final String USERNAME_TEXT_FIELD_TEXT = "Usuario";
	
	private static final String LOG_IN_BUTTON_TEXT = "Iniciar Sesión";
	private static final String SIGN_UP_BUTTON_TEXT = "Crear Cuenta";

	private static final String CHOICE_SIGN_UP_HYPERLINK_TEXT = "¿No tienes cuenta? - Registrate";
	private static final String CHOICE_LOG_IN_HYPERLINK_TEXT = "¿Tienes cuenta? - Iniciar Sesión";

	
	private BorderPane root;
	
	public void showSignUpPage(Stage stage)
	{
		//Botones y textos
        Label labelWriteYourEmail = new Label(SIGN_UP_EMAIL_LABEL_TEXT);
        Label labelWriteYourUsername = new Label(SIGN_UP_USERNAME_LABEL_TEXT);
        Label labelWriteYourPassword = new Label(SIGN_UP_PASSWORD_LABEL_TEXT);
        Label labelConfirmYourPassword = new Label(SIGN_UP_PASSWORD_CONFIRMATION_LABEL_TEXT);
        Label labelSelectYourGender = new Label(SIGN_UP_SEX_LABEL_TEXT);
        Label labelSelectYourDateOfBirth = new Label(SIGN_UP_DATE_OF_BIRTH_LABEL_TEXT);
        
        TextField tFieldEmail = new TextField();
        tFieldEmail.setPromptText(EMAIL_TEXT_FIELD_TEXT);
        
        TextField tFieldUsername = new TextField();
        tFieldUsername.setPromptText(USERNAME_TEXT_FIELD_TEXT);
        
        PasswordField pFieldPassword = new PasswordField();
        pFieldPassword.setPromptText(PASSWORD_PASSWORD_FIELD_TEXT);
        
        PasswordField pFieldPasswordConfirmation = new PasswordField();
        pFieldPasswordConfirmation.setPromptText(PASSWORD_PASSWORD_FIELD_TEXT);
        
        ComboBox<String> comboGender = new ComboBox<String>();
        comboGender.getItems().addAll("Hombre","Mujer","Otro");
        
        DatePicker dPickerDateOfBirth = new DatePicker();
        
        Button buttonSignUp = new Button(SIGN_UP_BUTTON_TEXT);
        
        Hyperlink hlLogIn = new Hyperlink(CHOICE_LOG_IN_HYPERLINK_TEXT);
        
        //Creacion del layout
        VBox signUpLayout = new VBox();
        
        signUpLayout.getChildren().addAll(labelWriteYourEmail,tFieldEmail,labelWriteYourUsername,tFieldUsername
        		,labelWriteYourPassword,pFieldPassword,labelConfirmYourPassword,pFieldPasswordConfirmation,
        		labelSelectYourGender,comboGender,labelSelectYourDateOfBirth,dPickerDateOfBirth,buttonSignUp,hlLogIn);

        root.setCenter(signUpLayout);
        
      //Asignar comportamientos
        buttonSignUp.setOnAction(s -> {
        	String emailLogIn = tFieldEmail.getText();
        	String passwordLogIn = pFieldPassword.getText();
        	try {
        		AppContext.session.userLogin(emailLogIn, passwordLogIn);
        		ErrorHandler.showError("ea ya fucniona");
        	}
        	catch(LoginFailedException e)
        	{
        		ErrorHandler.showError(e);
        	}
        	catch(Exception e)
        	{
        		ErrorHandler.showUnknownError();
        	}
        	finally
        	{
        		signUpLayout.requestFocus();
        		pFieldPassword.clear();
        	}
        });
        
        hlLogIn.setOnAction(e->{
        	showLoginPage(stage);
        });
	}
	
	public void showLoginPage(Stage stage)
	{
		//Botones y textos
        Label labelWriteYourEmail = new Label(LOG_IN_EMAIL_LABEL_TEXT);
        Label labelWriteYourPassword = new Label(LOG_IN_PASSWORD_LABEL_TEXT);
        
        TextField tFieldEmail = new TextField();
        tFieldEmail.setPromptText(EMAIL_TEXT_FIELD_TEXT);
        
        PasswordField pFieldPassword = new PasswordField();
        pFieldPassword.setPromptText(PASSWORD_PASSWORD_FIELD_TEXT);
        
        Button buttonLogIn = new Button(LOG_IN_BUTTON_TEXT);
        
        Hyperlink hlSingUp = new Hyperlink(CHOICE_SIGN_UP_HYPERLINK_TEXT);
        
        //Creacion del layout
        VBox loginLayout = new VBox();
        
        loginLayout.getChildren().addAll(labelWriteYourEmail,tFieldEmail,labelWriteYourPassword,pFieldPassword,buttonLogIn,hlSingUp);

        root.setCenter(loginLayout);
        
      //Asignar comportamientos
        buttonLogIn.setOnAction(s -> {
        	String emailLogIn = tFieldEmail.getText();
        	String passwordLogIn = pFieldPassword.getText();
        	try {
        		AppContext.session.userLogin(emailLogIn, passwordLogIn);
        		ErrorHandler.showError("ea ya fucniona");
        	}
        	catch(LoginFailedException e)
        	{
        		ErrorHandler.showError(e);
        	}
        	catch(Exception e)
        	{
        		ErrorHandler.showUnknownError();
        	}
        	finally
        	{
        		loginLayout.requestFocus();
        		pFieldPassword.clear();
        	}
        });
        
        hlSingUp.setOnAction(e->{
        	this.showSignUpPage(stage);
        });
	}
	
    @Override
    public void start(Stage stage) 
    {
    	//Crea la sesión asociada a la aplicacion al arrancar la app
    	AppContext.session = new Session();
    	
    	root = new BorderPane();
    	        
        
        //Creacion de la escena
        Scene escena = new Scene(root, 400, 200);
        
        this.showLoginPage(stage);
        
        stage.setTitle("Aplicacion Proyecto");
        stage.setScene(escena);
        stage.setMaximized(true);
        stage.show();
        stage.getIcons().add(null);
        escena.setOnMouseClicked(e->{
        	root.requestFocus();
        });
        
        
    }
    
    public static void main(String[] args) {
        launch();
    }
}
