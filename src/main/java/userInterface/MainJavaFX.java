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
import users.exceptions.InvalidDateFormatException;
import users.exceptions.InvalidDateOfBirthException;
import users.exceptions.InvalidEmailException;
import users.exceptions.InvalidPasswordException;
import users.exceptions.InvalidUsernameException;
import users.exceptions.RejectedUserAgreementException;
import users.roles.Role;

public class MainJavaFX extends Application 
{
	private static final int WINDOW_MIN_HEIGHT = 600;
	private static final int WINDOW_MIN_WIDTH = 800;
	
	private static final String LOG_IN_EMAIL_LABEL_TEXT = "Introduce tu email:";
	private static final String LOG_IN_PASSWORD_LABEL_TEXT = "Introduce tu contraseña:";
	
	private static final String SIGN_UP_EMAIL_LABEL_TEXT = "*Introduce un email:";
	private static final String SIGN_UP_USERNAME_LABEL_TEXT = "*Introduce un nombre de usuario:";
	private static final String SIGN_UP_PASSWORD_LABEL_TEXT = "*Introduce una contraseña:";
	private static final String SIGN_UP_PASSWORD_CONFIRMATION_LABEL_TEXT = "*Repite la contraseña:";
	private static final String SIGN_UP_SEX_LABEL_TEXT = "*Introduce tu género";
	private static final String SIGN_UP_DATE_OF_BIRTH_LABEL_TEXT = "*Introduce tu fecha de nacimiento:";
	private static final String MAINTENANCE_EMAILS_CHECKBOX_TEXT = "*Acepto recibir emails de mantenimiento";
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
	private static final int CHECKBOX_SHOW_PASSWORD_INNER_SPACING = 15;
	
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
        TextField tFieldPassword = new TextField();
        tFieldPassword.setPromptText(PASSWORD_PASSWORD_FIELD_TEXT);
        tFieldPassword.setManaged(false);
        tFieldPassword.setVisible(false);
        HBox.setHgrow(tFieldPassword, Priority.ALWAYS);
        HBox.setHgrow(pFieldPassword, Priority.ALWAYS);
        CheckBox pFieldShowPasswordCheckbox = new CheckBox();
        pFieldShowPasswordCheckbox.getStyleClass().add("check-ojo");
        HBox passwordLayoutBox = new HBox(pFieldPassword,tFieldPassword,pFieldShowPasswordCheckbox);
        passwordLayoutBox.setAlignment(Pos.CENTER);
        passwordLayoutBox.setMaxWidth(Double.MAX_VALUE);
        passwordLayoutBox.setSpacing(CHECKBOX_SHOW_PASSWORD_INNER_SPACING);
        
        PasswordField pFieldPasswordConfirmation = new PasswordField();
        pFieldPasswordConfirmation.setPromptText(PASSWORD_PASSWORD_FIELD_TEXT);
        TextField tFieldPasswordConfirmation = new TextField();
        tFieldPasswordConfirmation.setPromptText(PASSWORD_PASSWORD_FIELD_TEXT);
        tFieldPasswordConfirmation.setManaged(false);
        tFieldPasswordConfirmation.setVisible(false);
        HBox.setHgrow(pFieldPasswordConfirmation, Priority.ALWAYS);
        HBox.setHgrow(tFieldPasswordConfirmation, Priority.ALWAYS);
        CheckBox pFieldShowPasswordConfirmationCheckbox = new CheckBox();
        pFieldShowPasswordConfirmationCheckbox.getStyleClass().add("check-ojo");
        HBox passwordLayoutConfirmationBox = new HBox(pFieldPasswordConfirmation,tFieldPasswordConfirmation,pFieldShowPasswordConfirmationCheckbox);
        passwordLayoutConfirmationBox.setAlignment(Pos.CENTER);
        passwordLayoutConfirmationBox.setMaxWidth(Double.MAX_VALUE);
        passwordLayoutConfirmationBox.setSpacing(CHECKBOX_SHOW_PASSWORD_INNER_SPACING);
        
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
        		,labelWriteYourPassword,passwordLayoutBox,labelConfirmYourPassword,passwordLayoutConfirmationBox,
        		labelSelectYourGender,comboGender,labelSelectYourDateOfBirth,dPickerDateOfBirth,checkBoxCentered,buttonSignUp,hlLogIn);

        signUpLayout.setAlignment(Pos.CENTER);
        signUpLayout.setSpacing(10);
        signUpLayout.setPrefWidth(PREFERRED_FORM_WIDTH);
        signUpLayout.setMaxWidth(MAX_FORM_WIDTH);
        
        StackPane centerContainer = new StackPane(signUpLayout);
        
        root.setCenter(centerContainer);
        
        //Asignar comportamientos
        pFieldShowPasswordCheckbox.setOnAction(s->{
        	if (pFieldShowPasswordCheckbox.isSelected()) {
                // Sincronizar texto: de oculto a visible
        		tFieldPassword.setText(pFieldPassword.getText());
                
                // Intercambiar visibilidad
                pFieldPassword.setVisible(false);
                pFieldPassword.setManaged(false);
                
                tFieldPassword.setVisible(true);
                tFieldPassword.setManaged(true);
            } else {
                // Sincronizar texto: de visible a oculto (por si el usuario editó mientras veía)
                pFieldPassword.setText(tFieldPassword.getText());
                
                // Intercambiar visibilidadºs
                tFieldPassword.setVisible(false);
                tFieldPassword.setManaged(false);
                
                pFieldPassword.setVisible(true);
                pFieldPassword.setManaged(true);
            }
        });

        pFieldShowPasswordConfirmationCheckbox.setOnAction(s->{
        	if (pFieldShowPasswordConfirmationCheckbox.isSelected()) {
                // Sincronizar texto: de oculto a visible
        		tFieldPasswordConfirmation.setText(pFieldPasswordConfirmation.getText());
                
                // Intercambiar visibilidad
                pFieldPasswordConfirmation.setVisible(false);
                pFieldPasswordConfirmation.setManaged(false);
                
                tFieldPasswordConfirmation.setVisible(true);
                tFieldPasswordConfirmation.setManaged(true);
            } else {
                // Sincronizar texto: de visible a oculto (por si el usuario editó mientras veía)
                pFieldPasswordConfirmation.setText(tFieldPasswordConfirmation.getText());
                
                // Intercambiar visibilidadºs
                tFieldPasswordConfirmation.setVisible(false);
                tFieldPasswordConfirmation.setManaged(false);
                
                pFieldPasswordConfirmation.setVisible(true);
                pFieldPasswordConfirmation.setManaged(true);
            }
        });
        
        buttonSignUp.setOnAction(s -> {
        	String emailSignUp = tFieldEmail.getText();
        	String usernameSignUp = tFieldUsername.getText();
        	String passwordSignUp = null;
        	if(pFieldPassword.isVisible())
        	{
        		passwordSignUp = pFieldPassword.getText();
        	}
        	else
        	{
        		passwordSignUp = tFieldPassword.getText();
        	}
        	String passwordConfirmationSignUp = null;
        	if(pFieldPasswordConfirmation.isVisible())
        	{
        		passwordConfirmationSignUp = pFieldPasswordConfirmation.getText();
        	}
        	else
        	{
        		passwordConfirmationSignUp = tFieldPasswordConfirmation.getText();
        	}

        	String genderSignUp = comboGender.getValue();
        	LocalDate dateOfBirthSignUp = dPickerDateOfBirth.getValue();
        	boolean validDateOfBirth = true;
        	if(dateOfBirthSignUp == null)
        	{
        		ErrorHandler.showError(new InvalidDateFormatException());
        		validDateOfBirth = false;
        	}
        	boolean acceptsResponseEmailsSignUp = checkBoxAcceptsResponse.isSelected();
        	boolean acceptsManteinanceEmailsSignUp = checkBoxAcceptsMaintenance.isSelected();
        	if(validDateOfBirth)
        	{
        		try 
            	{
    				AppContext.session.signUpUser(emailSignUp, usernameSignUp, passwordSignUp, passwordConfirmationSignUp
    						, genderSignUp, dateOfBirthSignUp, acceptsResponseEmailsSignUp, acceptsManteinanceEmailsSignUp);
    				this.showLoggedInPage(stage);
            	}
            	catch(InvalidEmailException | InvalidUsernameException | DuplicateUsernameException| InvalidDateOfBirthException
    					|InvalidDateFormatException|RejectedUserAgreementException| DuplicateEmailException | InvalidPasswordException | FailedPasswordVerificationException | SignUpFailedException e)
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
        TextField tFieldPassword = new TextField();
        tFieldPassword.setPromptText(PASSWORD_PASSWORD_FIELD_TEXT);
        tFieldPassword.setManaged(false);
        tFieldPassword.setVisible(false);
        HBox.setHgrow(tFieldPassword, Priority.ALWAYS);
        HBox.setHgrow(pFieldPassword, Priority.ALWAYS);
        CheckBox pFieldShowPasswordCheckbox = new CheckBox();
        pFieldShowPasswordCheckbox.getStyleClass().add("check-ojo");
        HBox passwordLayoutBox = new HBox(pFieldPassword,tFieldPassword,pFieldShowPasswordCheckbox);
        passwordLayoutBox.setAlignment(Pos.CENTER);
        passwordLayoutBox.setMaxWidth(Double.MAX_VALUE);
        passwordLayoutBox.setSpacing(CHECKBOX_SHOW_PASSWORD_INNER_SPACING);
        
        Button buttonLogIn = new Button(LOG_IN_BUTTON_TEXT);
        
        Hyperlink hlSingUp = new Hyperlink(CHOICE_SIGN_UP_HYPERLINK_TEXT);
        
        //Creacion del layout
        VBox loginLayout = new VBox();
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setSpacing(10);
        loginLayout.setPrefWidth(PREFERRED_FORM_WIDTH);
        loginLayout.setMaxWidth(MAX_FORM_WIDTH);
        
        StackPane centerContainer = new StackPane(loginLayout);
        
        loginLayout.getChildren().addAll(titleView, labelWriteYourEmail,tFieldEmail,labelWriteYourPassword,passwordLayoutBox,buttonLogIn,hlSingUp);

        root.setCenter(centerContainer);
        
      //Asignar comportamientos
        pFieldShowPasswordCheckbox.setOnAction(s->{
        	if (pFieldShowPasswordCheckbox.isSelected()) {
                // Sincronizar texto: de oculto a visible
        		tFieldPassword.setText(pFieldPassword.getText());
                
                // Intercambiar visibilidad
                pFieldPassword.setVisible(false);
                pFieldPassword.setManaged(false);
                
                tFieldPassword.setVisible(true);
                tFieldPassword.setManaged(true);
            } else {
                // Sincronizar texto: de visible a oculto (por si el usuario editó mientras veía)
                pFieldPassword.setText(tFieldPassword.getText());
                
                // Intercambiar visibilidadºs
                tFieldPassword.setVisible(false);
                tFieldPassword.setManaged(false);
                
                pFieldPassword.setVisible(true);
                pFieldPassword.setManaged(true);
            }
        	
        });
        
        buttonLogIn.setOnAction(s -> {
        	String emailLogIn = tFieldEmail.getText();
        	String passwordLogIn = null;
        	if(pFieldPassword.isVisible())
        	{
        		passwordLogIn = pFieldPassword.getText();
        	}
        	else
        	{
        		passwordLogIn = tFieldPassword.getText();
        	}
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
    	
    	BorderPane borderRoot = new BorderPane();
    	ScrollPane scrollRoot = new ScrollPane(borderRoot);
    	scrollRoot.setFitToWidth(true);
    	scrollRoot.setFitToHeight(true);
        
    	this.root = borderRoot;
    	
        //Creacion de la escena
        Scene escena = new Scene(scrollRoot, 400, 200);
        escena.getStylesheets().add(getClass().getResource("/darkMode.css").toExternalForm());
        
        this.showLoginPage(stage);
        
        stage.setTitle("Aplicacion Proyecto");
        stage.setScene(escena);
        stage.setMaximized(true);
        stage.setMinHeight(WINDOW_MIN_HEIGHT);
        stage.setMinWidth(WINDOW_MIN_WIDTH);
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
