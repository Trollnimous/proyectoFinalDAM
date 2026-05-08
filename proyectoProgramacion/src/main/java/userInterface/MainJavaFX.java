package userInterface;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import appContext.AppContext;
import database.exceptions.DuplicateEmailException;
import database.exceptions.DuplicateUsernameException;
import javafx.application.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.*;
import session.Session;
import session.exceptions.LoginFailedException;
import session.exceptions.SignUpFailedException;
import users.exceptions.FailedPasswordVerificationException;
import users.exceptions.InvalidDateOfBirthException;
import users.exceptions.InvalidEmailException;
import users.exceptions.InvalidPasswordException;
import users.exceptions.InvalidUsernameException;
import users.roles.Role;

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
	private static final String MAINTENANCE_EMAILS_CHECKBOX_TEXT = "Acepto recibir emails de mantenimiento";
	private static final String RESPONSE_EMAILS_CHECKBOX_TEXT = "Acepto recibir emails a las respuestas de mis posts";
	
	private static final String EMAIL_TEXT_FIELD_TEXT = "Email";
	private static final String PASSWORD_PASSWORD_FIELD_TEXT = "Contraseña";
	private static final String USERNAME_TEXT_FIELD_TEXT = "Usuario";
	
	private static final String LOG_IN_BUTTON_TEXT = "Iniciar Sesión";
	private static final String SIGN_UP_BUTTON_TEXT = "Crear Cuenta";

	private static final String CHOICE_SIGN_UP_HYPERLINK_TEXT = "¿No tienes cuenta? - Registrate";
	private static final String CHOICE_LOG_IN_HYPERLINK_TEXT = "¿Tienes cuenta? - Iniciar Sesión";

	private static final int PREFERRED_TEXT_FIELD_WIDTH = 200;
	private static final int PREFERRED_FORM_WIDTH = 250;
	private static final int MAX_FORM_WIDTH = 300;

	private static final int CHECKBOX_INNER_SPACING = 50;
	
	private BorderPane root;
	
	public void showSignUpPage(Stage stage)
	{
		//Titulo
		Image gif = new Image(getClass().getResourceAsStream("/loginTitle.gif"));
		ImageView titleView = new ImageView(gif);
		
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
        
        CheckBox checkBoxAcceptsResponse = new CheckBox(RESPONSE_EMAILS_CHECKBOX_TEXT); 
        CheckBox checkBoxAcceptsMaintenance = new CheckBox(MAINTENANCE_EMAILS_CHECKBOX_TEXT); 
        HBox checkBoxLayout = new HBox(checkBoxAcceptsResponse,checkBoxAcceptsMaintenance);
        checkBoxLayout.setAlignment(Pos.CENTER);
        checkBoxLayout.setMaxWidth(Double.MAX_VALUE);
        checkBoxLayout.setSpacing(CHECKBOX_INNER_SPACING);
        StackPane checkBoxCentered = new StackPane(checkBoxLayout);
        
        Button buttonSignUp = new Button(SIGN_UP_BUTTON_TEXT);
        
        Hyperlink hlLogIn = new Hyperlink(CHOICE_LOG_IN_HYPERLINK_TEXT);
        
        //Creacion del layout
        VBox signUpLayout = new VBox();
        
        signUpLayout.getChildren().addAll(titleView, labelWriteYourEmail,tFieldEmail,labelWriteYourUsername,tFieldUsername
        		,labelWriteYourPassword,pFieldPassword,labelConfirmYourPassword,pFieldPasswordConfirmation,
        		labelSelectYourGender,comboGender,labelSelectYourDateOfBirth,dPickerDateOfBirth,checkBoxCentered,buttonSignUp,hlLogIn);

        signUpLayout.setAlignment(Pos.CENTER);
        signUpLayout.setSpacing(10);
        signUpLayout.setPrefWidth(PREFERRED_FORM_WIDTH);
        signUpLayout.setMaxWidth(MAX_FORM_WIDTH);
        
        StackPane centerContainer = new StackPane(signUpLayout);
        
        root.setCenter(centerContainer);
        
        //Asignar comportamientos
        buttonSignUp.setOnAction(s -> {
        	String emailSignUp = tFieldEmail.getText();
        	String usernameSignUp = tFieldUsername.getText();
        	String passwordSignUp = pFieldPassword.getText();
        	String passwordConfirmationSignUp = pFieldPasswordConfirmation.getText();
        	String genderSignUp = comboGender.getValue();
        	LocalDate dateOfBirthSignUp = dPickerDateOfBirth.getValue();
        	boolean validDate = true;
        	if(dateOfBirthSignUp == null)
        	{
        		ErrorHandler.showError("Introduce un formato de fecha válido");
        		validDate = false;
        	}
        	if(validDate)
        	{
        		try 
            	{
    				AppContext.session.signUpUser(emailSignUp, usernameSignUp, passwordSignUp, passwordConfirmationSignUp, genderSignUp, dateOfBirthSignUp);
    				this.showLoggedInPage(stage);
            	}
            	catch(InvalidEmailException | InvalidUsernameException | DuplicateUsernameException| InvalidDateOfBirthException
    					| DuplicateEmailException | InvalidPasswordException | FailedPasswordVerificationException | SignUpFailedException e)
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
            	}
        	}
        });
        
        hlLogIn.setOnAction(e->{
        	showLoginPage(stage);
        });
	}
	
	public void showLoginPage(Stage stage)
	{
		//Titulo
		Image gif = new Image(getClass().getResourceAsStream("/loginTitle.gif"));
		ImageView titleView = new ImageView(gif);
		
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
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setSpacing(10);
        loginLayout.setPrefWidth(PREFERRED_FORM_WIDTH);
        loginLayout.setMaxWidth(MAX_FORM_WIDTH);
        
        StackPane centerContainer = new StackPane(loginLayout);
        
        loginLayout.getChildren().addAll(titleView, labelWriteYourEmail,tFieldEmail,labelWriteYourPassword,pFieldPassword,buttonLogIn,hlSingUp);

        root.setCenter(centerContainer);
        
      //Asignar comportamientos
        buttonLogIn.setOnAction(s -> {
        	String emailLogIn = tFieldEmail.getText();
        	String passwordLogIn = pFieldPassword.getText();
        	try {
        		AppContext.session.userLogin(emailLogIn, passwordLogIn);
        		this.showLoggedInPage(stage);
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
	
	public void showLoggedInPage(Stage stage)
	{
		if(AppContext.session.getUser().getRole() == Role.ADMIN)
		{
			//TODO: hacer la vista de administrador
		}
		if(AppContext.session.getUser().getRole() == Role.USER)
		{
			this.showUserPage(stage);
		}
	}
	
	public void showUserPage(Stage stage)
	{
		//Botones y textos
        Label labelWriteYourEmail = new Label("Hola, "+AppContext.session.getUser().getUsername());
        
        //Creacion del layout
        VBox loginLayout = new VBox();
        
        loginLayout.getChildren().addAll(labelWriteYourEmail);

        root.setCenter(loginLayout);
        
        //Asignar comportamientos

	}
	
    @Override
    public void start(Stage stage) 
    {
    	//Crea la sesión asociada a la aplicacion al arrancar la app
    	AppContext.session = new Session();
    	
    	root = new BorderPane();
    	        
        
        //Creacion de la escena
        Scene escena = new Scene(root, 400, 200);
        escena.getStylesheets().add(getClass().getResource("/darkMode.css").toExternalForm());
        
        this.showLoginPage(stage);
        
        stage.setTitle("Aplicacion Proyecto");
        stage.setScene(escena);
        stage.setMaximized(true);
        stage.show();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/undertaleHeart.png")));
        escena.setOnMouseClicked(e->{
        	root.requestFocus();
        });
        
        
    }
    
    public static void main(String[] args) {
        launch();
    }
}
