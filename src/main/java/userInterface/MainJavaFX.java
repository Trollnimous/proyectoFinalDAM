package userInterface;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import appContext.AppContext;
import database.exceptions.DuplicateEmailException;
import database.exceptions.DuplicateUsernameException;
import database.exceptions.FailedUserDeletionException;
import javafx.application.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.stage.*;
import session.Session;
import session.exceptions.DuplicatePasswordException;
import session.exceptions.FailedPasswordUpdateException;
import session.exceptions.LoginFailedException;
import session.exceptions.SignUpFailedException;
import users.User;
import users.exceptions.EmptyFieldsException;
import users.exceptions.FailedPasswordVerificationException;
import users.exceptions.InvalidDateFormatException;
import users.exceptions.InvalidDateOfBirthException;
import users.exceptions.InvalidEmailException;
import users.exceptions.InvalidPasswordException;
import users.exceptions.InvalidUsernameException;
import users.exceptions.RejectedUserAgreementException;
import users.gender.Gender;
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
	
	private static final String CREATE_POST_BUTTON_TEXT = "Subir post";
	private static final String READ_POST_BUTTON_TEXT = "Leer un post";
	private static final String SEE_YOUR_POSTS_BUTTON_TEXT = "Ver tus posts";
	private static final String CHANGE_YOUR_PASSWORD_BUTTON_TEXT = "Cambiar contraseña";
	private static final String CHANGE_YOUR_PROFILE_PICTURE_BUTTON_TEXT = "Cambiar foto de perfil";
	private static final String DELETE_YOUR_ACCOUNT_BUTTON_TEXT = "Borrar cuenta";
	private static final String EXIT_BUTTON_TEXT = "Cerrar sesión";

	
	
	
	private static final int PREFERRED_TEXT_FIELD_WIDTH = 200;
	private static final int PREFERRED_FORM_WIDTH = 250;
	private static final int MAX_FORM_WIDTH = 300;

	private static final int CHECKBOX_INNER_SPACING = 50;
	private static final int CHECKBOX_SHOW_PASSWORD_INNER_SPACING = 15;
	
	private static final int TITLE_PADDING_VALUE = 35;
	private static final int SIDEBAR_BUTTON_PADDING_VALUE = 35;
	private static final int SIDEBAR_WIDTH = 400;
	private static final int PROFILE_PIC_SIZE = 64;
	private static final int PROFILE_PIC_SPACING = 25;
	private static final int EXIT_ICON_SIZE = 32;
	private static final int EXIT_ICON_SPACING = 15;
	private static final int CENTER_LAYOUT_PADDING = 60;
	private static final int CENTER_LAYOUT_SPACING = 20;
	private static final int BOTTOM_LAYOUT_SPACING = 20;
	
	private static final String WRITE_POST_TITLE_LABEL = "Título del post";
	private static final String WRITE_POST_TITLE_TEXT_FIELD_TEXT = "¡Menudo día he tenido!";
	private static final String WRITE_POST_CONTENT_LABEL = "Mensaje";
	private static final String WRITE_POST_CONTENT_TEXT_FIELD_TEXT = "Escribe aquí...";
	private static final String SELECT_MAX_READINGS_LEFT_COMBO = "Selecciona las lecturas máximas";
	private static final String POST_WANTS_RESPONSE_CHECKBOX_TEXT = "¿Quieres respuestas en el post?";
	private static final int TEXT_AREA_PREF_ROW_COUNT = 15;

	private static final String ACCOUNT_DELETION_WARNING_LABEL = "AVISO: EL BORRADO DE LA CUENTA ES PERMANENTE";
	private static final String ACCOUNT_DELETION_EMAIL_LABEL = "Escribe tu email:";
	private static final String ACCOUNT_DELETION_USERNAME_LABEL = "Escribe tu nombre de usuario";
	private static final String ACCOUNT_DELETION_PASSWORD_LABEL = "Escribe tu contraseña";
	private static final String ACCOUNT_DELETION_ACCEPTANCE_CHECKBOX = "Comprendo que el borrado de mi cuenta y sus posts asociados es permanente y no se puede deshacer";
	private static final String ACCOUNT_DELETION_BUTTON_TEXT = "Borrar cuenta";
	
	private static final String OLD_PASSWORD_PASSWORD_MODIFICATION_LABEL = "Introduce tu contraseña actual";
	private static final String NEW_PASSWORD_PASSWORD_MODIFICATION_LABEL = "Introduce tu nueva contraseña";
	private static final String NEW_PASSWORD_CONFIRMATION_PASSWORD_MODIFICATION_LABEL = "Repite tu nueva contraseña";
	private static final String UPDATE_PASSWORD_PASSWORD_MODIFICATION_BUTTON = "Actualizar contraseña";

	
	private BorderPane root;
	
	public void showSignUpPage(Stage stage)
	{
		this.root.getChildren().clear();
		
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
        	if (pFieldShowPasswordCheckbox.isSelected()) 
        	{
                // Sincronizar texto: de oculto a visible
        		tFieldPassword.setText(pFieldPassword.getText());
                
                // Intercambiar visibilidad
                pFieldPassword.setVisible(false);
                pFieldPassword.setManaged(false);
                
                tFieldPassword.setVisible(true);
                tFieldPassword.setManaged(true);
            } 
        	else
            {
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
        	if (pFieldShowPasswordConfirmationCheckbox.isSelected()) 
        	{
                // Sincronizar texto: de oculto a visible
        		tFieldPasswordConfirmation.setText(pFieldPasswordConfirmation.getText());
                
                // Intercambiar visibilidad
                pFieldPasswordConfirmation.setVisible(false);
                pFieldPasswordConfirmation.setManaged(false);
                
                tFieldPasswordConfirmation.setVisible(true);
                tFieldPasswordConfirmation.setManaged(true);
            } 
        	else 
            {
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
		this.root.getChildren().clear();
		
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
        tFieldEmail.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
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
            	catch(EmptyFieldsException e)
            	{
            		ErrorHandler.showError(e);
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
            }
        });
        
        tFieldPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
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
            	catch(EmptyFieldsException e)
            	{
            		ErrorHandler.showError(e);
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
            }
        });
        
        pFieldPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
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
            	catch(EmptyFieldsException e)
            	{
            		ErrorHandler.showError(e);
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
            }
        });
        
        pFieldShowPasswordCheckbox.setOnAction(s->{
        	if (pFieldShowPasswordCheckbox.isSelected()) 
        	{
                // Sincronizar texto: de oculto a visible
        		tFieldPassword.setText(pFieldPassword.getText());
                
                // Intercambiar visibilidad
                pFieldPassword.setVisible(false);
                pFieldPassword.setManaged(false);
                
                tFieldPassword.setVisible(true);
                tFieldPassword.setManaged(true);
            }
        	else 
        	{
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
        	catch(EmptyFieldsException e)
        	{
        		ErrorHandler.showError(e);
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
		this.root.getChildren().clear();
		
		//Top de la pagina
		Image titleGif = new Image(getClass().getResourceAsStream("/loginTitle.gif"));
		ImageView titleView = new ImageView(titleGif);
		VBox header = new VBox(titleView);
		header.setAlignment(Pos.CENTER);
		header.getStyleClass().add("header");
		header.setPadding(new javafx.geometry.Insets(TITLE_PADDING_VALUE));
		
		
		this.showSidebar(stage);		
		
		this.root.setTop(header);
		
		
	}
	
	public void showCreatePostCenter(Stage stage)
	{
		
		Label writePostTitleLabel = new Label(WRITE_POST_TITLE_LABEL);
		writePostTitleLabel.setAlignment(Pos.CENTER_LEFT);
		writePostTitleLabel.setMaxWidth(Double.MAX_VALUE);
		writePostTitleLabel.getStyleClass().add("titulo-center");
		
		TextField writePostTitleTextField = new TextField();
		writePostTitleTextField.setPromptText(WRITE_POST_TITLE_TEXT_FIELD_TEXT);
		
		Label writePostContentLabel = new Label(WRITE_POST_CONTENT_LABEL);
		writePostContentLabel.getStyleClass().add("titulo-center");
		writePostContentLabel.setMaxWidth(Double.MAX_VALUE);
		writePostContentLabel.setAlignment(Pos.CENTER_LEFT);
		
		TextArea writePostContentTextArea = new TextArea();
		writePostContentTextArea.setPrefRowCount(TEXT_AREA_PREF_ROW_COUNT);
		writePostContentTextArea.setPromptText(WRITE_POST_CONTENT_TEXT_FIELD_TEXT);
		
		Label selectMaxReadingLabel = new Label(SELECT_MAX_READINGS_LEFT_COMBO);
		ComboBox<Integer> comboBoxMaxReadings = new ComboBox<Integer>();
        comboBoxMaxReadings.getItems().addAll(1,2,3,4,5);
		Label checkBoxWantsResponseLabel = new Label(POST_WANTS_RESPONSE_CHECKBOX_TEXT);
        CheckBox checkBoxWantsResponse = new CheckBox(); 
        HBox.setMargin(checkBoxWantsResponseLabel, new Insets(0, 0, 0, 200));
        HBox bottomLayout = new HBox(selectMaxReadingLabel,comboBoxMaxReadings,checkBoxWantsResponseLabel,checkBoxWantsResponse);
        bottomLayout.setAlignment(Pos.CENTER);
        bottomLayout.setSpacing(BOTTOM_LAYOUT_SPACING);

        Button createPostButton = new Button(CREATE_POST_BUTTON_TEXT);
        
        
		VBox createPostLayout = new VBox(writePostTitleLabel,writePostTitleTextField,writePostContentLabel,writePostContentTextArea,bottomLayout,createPostButton);
		createPostLayout.setAlignment(Pos.TOP_CENTER);
		createPostLayout.setPadding(new javafx.geometry.Insets(CENTER_LAYOUT_PADDING));
		createPostLayout.setSpacing(CENTER_LAYOUT_SPACING);
		
		this.root.setCenter(createPostLayout);
	}
	
	public void showDeleteAccountCenter(Stage stage)
	{
		
		Label accountDeletionWarning = new Label(ACCOUNT_DELETION_WARNING_LABEL);
		accountDeletionWarning.getStyleClass().add("advertencia-borrado");
		accountDeletionWarning.setMaxWidth(Double.MAX_VALUE);
		accountDeletionWarning.setAlignment(Pos.CENTER);
		
		Label writeYourEmailLabel = new Label(ACCOUNT_DELETION_EMAIL_LABEL);
		writeYourEmailLabel.getStyleClass().add("titulo-center");
		writeYourEmailLabel.setMaxWidth(Double.MAX_VALUE);
		writeYourEmailLabel.setAlignment(Pos.CENTER_LEFT);		
		TextField writeYourEmailTextField = new TextField();
		writeYourEmailTextField.setPromptText(EMAIL_TEXT_FIELD_TEXT);
		
		Label writeYourUsernameLabel = new Label(ACCOUNT_DELETION_USERNAME_LABEL);
		writeYourUsernameLabel.getStyleClass().add("titulo-center");
		writeYourUsernameLabel.setMaxWidth(Double.MAX_VALUE);
		writeYourUsernameLabel.setAlignment(Pos.CENTER_LEFT);
		TextField writeYourUsernameTextField = new TextField();
		writeYourUsernameTextField.setPromptText(USERNAME_TEXT_FIELD_TEXT);
		
		Label writeYourPasswordLabel = new Label(ACCOUNT_DELETION_PASSWORD_LABEL);
		writeYourPasswordLabel.getStyleClass().add("titulo-center");
		writeYourPasswordLabel.setMaxWidth(Double.MAX_VALUE);
		writeYourPasswordLabel.setAlignment(Pos.CENTER_LEFT);
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
        passwordLayoutBox.setAlignment(Pos.CENTER_LEFT);
        passwordLayoutBox.setMaxWidth(Double.MAX_VALUE);
        passwordLayoutBox.setSpacing(CHECKBOX_SHOW_PASSWORD_INNER_SPACING);

		Label accountDeletionCheckBoxLabel = new Label(ACCOUNT_DELETION_ACCEPTANCE_CHECKBOX);
		accountDeletionCheckBoxLabel.getStyleClass().add("checkbox-deletion");
        CheckBox checkBoxAccountDeletion = new CheckBox(); 
        HBox accountDeletionCheckboxLayout = new HBox(accountDeletionCheckBoxLabel,checkBoxAccountDeletion);
        accountDeletionCheckboxLayout.setAlignment(Pos.CENTER);
        accountDeletionCheckboxLayout.setSpacing(BOTTOM_LAYOUT_SPACING);
        
        Button deleteAccountButton = new Button(ACCOUNT_DELETION_BUTTON_TEXT); 
        deleteAccountButton.setAlignment(Pos.CENTER_LEFT);
        
        
		VBox createPostLayout = new VBox(accountDeletionWarning,writeYourEmailLabel,writeYourEmailTextField,writeYourUsernameLabel,writeYourUsernameTextField,writeYourPasswordLabel,passwordLayoutBox,accountDeletionCheckboxLayout,deleteAccountButton);
		createPostLayout.setAlignment(Pos.TOP_CENTER);
		createPostLayout.setPadding(new javafx.geometry.Insets(CENTER_LAYOUT_PADDING));
		createPostLayout.setSpacing(CENTER_LAYOUT_SPACING);
		
		//Comportamientos
		pFieldShowPasswordCheckbox.setOnAction(s->{
        	if (pFieldShowPasswordCheckbox.isSelected()) 
        	{
                // Sincronizar texto: de oculto a visible
        		tFieldPassword.setText(pFieldPassword.getText());
                
                // Intercambiar visibilidad
                pFieldPassword.setVisible(false);
                pFieldPassword.setManaged(false);
                
                tFieldPassword.setVisible(true);
                tFieldPassword.setManaged(true);
            }
        	else 
        	{
                // Sincronizar texto: de visible a oculto (por si el usuario editó mientras veía)
                pFieldPassword.setText(tFieldPassword.getText());
                
                // Intercambiar visibilidadºs
                tFieldPassword.setVisible(false);
                tFieldPassword.setManaged(false);
                
                pFieldPassword.setVisible(true);
                pFieldPassword.setManaged(true);
            }
        	
        });
		
		deleteAccountButton.setOnAction(s->{
			String emailToCheck = writeYourEmailTextField.getText();
			String usernameToCheck = writeYourUsernameTextField.getText();
			String passwordToCheck = null;
			if(pFieldPassword.isVisible())
			{
				passwordToCheck = pFieldPassword.getText();
			}
			else
			{
				passwordToCheck = tFieldPassword.getText();
			}
			if(emailToCheck == "" || usernameToCheck == "" || passwordToCheck == "" || !checkBoxAccountDeletion.isSelected())
			{
				ErrorHandler.showError("Debes de completar todos los campos para continuar");
			}
			else
			{
				User userToCheck = AppContext.session.getUser();
				if(userToCheck.getEmail().equals(emailToCheck)&&userToCheck.getUsername().equals(usernameToCheck)&&userToCheck.correctPassword(passwordToCheck))
				{
					if(AppContext.session.deleteUser())
					{
						ConfirmationPopUps.userDeletionConfirmationMessage();
						AppContext.session.endSession();				
						this.showLoginPage(stage);
					}
					else
					{
						ErrorHandler.showError(new FailedUserDeletionException());
					}					
				}
				else
				{
					ErrorHandler.showError("Los datos introducidos no coinciden.\nPor favor, revisa los datos e inténtalo de nuevo.");
				}
			}			
		});
		
		this.root.setCenter(createPostLayout);
	}
	
	public void showUpdatePasswordCenter(Stage stage)
	{
		
		Label writeOldPasswordLabel = new Label(OLD_PASSWORD_PASSWORD_MODIFICATION_LABEL);
		writeOldPasswordLabel.getStyleClass().add("titulo-center");
		writeOldPasswordLabel.setMaxWidth(Double.MAX_VALUE);
		writeOldPasswordLabel.setAlignment(Pos.CENTER_LEFT);		
		PasswordField pFieldOldPassword = new PasswordField();
        pFieldOldPassword.setPromptText(PASSWORD_PASSWORD_FIELD_TEXT);
        TextField tFieldOldPassword = new TextField();
        tFieldOldPassword.setPromptText(PASSWORD_PASSWORD_FIELD_TEXT);
        tFieldOldPassword.setManaged(false);
        tFieldOldPassword.setVisible(false);
        HBox.setHgrow(tFieldOldPassword, Priority.ALWAYS);
        HBox.setHgrow(pFieldOldPassword, Priority.ALWAYS);
        CheckBox oldPasswordCheckBox = new CheckBox();
        oldPasswordCheckBox.getStyleClass().add("check-ojo");
        HBox oldPasswordLayoutBox = new HBox(pFieldOldPassword,tFieldOldPassword,oldPasswordCheckBox);
        oldPasswordLayoutBox.setAlignment(Pos.CENTER_LEFT);
        oldPasswordLayoutBox.setMaxWidth(Double.MAX_VALUE);
        oldPasswordLayoutBox.setSpacing(CHECKBOX_SHOW_PASSWORD_INNER_SPACING);
		
        Label writeNewPasswordLabel = new Label(NEW_PASSWORD_PASSWORD_MODIFICATION_LABEL);
		writeNewPasswordLabel.getStyleClass().add("titulo-center");
		writeNewPasswordLabel.setMaxWidth(Double.MAX_VALUE);
		writeNewPasswordLabel.setAlignment(Pos.CENTER_LEFT);		
		PasswordField pFieldNewPassword = new PasswordField();
        pFieldNewPassword.setPromptText(PASSWORD_PASSWORD_FIELD_TEXT);
        TextField tFieldNewPassword = new TextField();
        tFieldNewPassword.setPromptText(PASSWORD_PASSWORD_FIELD_TEXT);
        tFieldNewPassword.setManaged(false);
        tFieldNewPassword.setVisible(false);
        HBox.setHgrow(tFieldNewPassword, Priority.ALWAYS);
        HBox.setHgrow(pFieldNewPassword, Priority.ALWAYS);
        CheckBox newPasswordCheckBox = new CheckBox();
        newPasswordCheckBox.getStyleClass().add("check-ojo");
        HBox newPasswordLayoutBox = new HBox(pFieldNewPassword,tFieldNewPassword,newPasswordCheckBox);
        newPasswordLayoutBox.setAlignment(Pos.CENTER_LEFT);
        newPasswordLayoutBox.setMaxWidth(Double.MAX_VALUE);
        newPasswordLayoutBox.setSpacing(CHECKBOX_SHOW_PASSWORD_INNER_SPACING);
        
        Label writeNewPasswordConfirmationLabel = new Label(NEW_PASSWORD_CONFIRMATION_PASSWORD_MODIFICATION_LABEL);
		writeNewPasswordConfirmationLabel.getStyleClass().add("titulo-center");
		writeNewPasswordConfirmationLabel.setMaxWidth(Double.MAX_VALUE);
		writeNewPasswordConfirmationLabel.setAlignment(Pos.CENTER_LEFT);		
		PasswordField pFieldNewPasswordConfirmation = new PasswordField();
        pFieldNewPasswordConfirmation.setPromptText(PASSWORD_PASSWORD_FIELD_TEXT);
        TextField tFieldNewPasswordConfirmation = new TextField();
        tFieldNewPasswordConfirmation.setPromptText(PASSWORD_PASSWORD_FIELD_TEXT);
        tFieldNewPasswordConfirmation.setManaged(false);
        tFieldNewPasswordConfirmation.setVisible(false);
        HBox.setHgrow(tFieldNewPasswordConfirmation, Priority.ALWAYS);
        HBox.setHgrow(pFieldNewPasswordConfirmation, Priority.ALWAYS);
        CheckBox newPasswordConfirmationCheckBox = new CheckBox();
        newPasswordConfirmationCheckBox.getStyleClass().add("check-ojo");
        HBox newPasswordConfirmationLayoutBox = new HBox(pFieldNewPasswordConfirmation,tFieldNewPasswordConfirmation,newPasswordConfirmationCheckBox);
        newPasswordConfirmationLayoutBox.setAlignment(Pos.CENTER_LEFT);
        newPasswordConfirmationLayoutBox.setMaxWidth(Double.MAX_VALUE);
        newPasswordConfirmationLayoutBox.setSpacing(CHECKBOX_SHOW_PASSWORD_INNER_SPACING);
        
        
        
        Button updatePasswordButton = new Button(UPDATE_PASSWORD_PASSWORD_MODIFICATION_BUTTON); 
        updatePasswordButton.setAlignment(Pos.CENTER_LEFT);
        
        
        VBox udpatePasswordLayout = new VBox(writeOldPasswordLabel,oldPasswordLayoutBox,writeNewPasswordLabel,newPasswordLayoutBox,writeNewPasswordConfirmationLabel,newPasswordConfirmationLayoutBox,updatePasswordButton);
		udpatePasswordLayout.setAlignment(Pos.TOP_CENTER);
		udpatePasswordLayout.setPadding(new javafx.geometry.Insets(CENTER_LAYOUT_PADDING));
		udpatePasswordLayout.setSpacing(CENTER_LAYOUT_SPACING);
		
		//Comportamientos
		oldPasswordCheckBox.setOnAction(s->{
        	if (oldPasswordCheckBox.isSelected()) 
        	{
                // Sincronizar texto: de oculto a visible
        		tFieldOldPassword.setText(pFieldOldPassword.getText());
                
                // Intercambiar visibilidad
        		pFieldOldPassword.setVisible(false);
        		pFieldOldPassword.setManaged(false);
                
                tFieldOldPassword.setVisible(true);
                tFieldOldPassword.setManaged(true);
            }
        	else 
        	{
                // Sincronizar texto: de visible a oculto (por si el usuario editó mientras veía)
        		pFieldOldPassword.setText(tFieldOldPassword.getText());
                
                tFieldOldPassword.setVisible(false);
                tFieldOldPassword.setManaged(false);
                
                pFieldOldPassword.setVisible(true);
                pFieldOldPassword.setManaged(true);
            }
        	
        });
		
		newPasswordCheckBox.setOnAction(s->{
        	if (newPasswordCheckBox.isSelected()) 
        	{
                // Sincronizar texto: de oculto a visible
        		tFieldNewPassword.setText(pFieldNewPassword.getText());
                
                // Intercambiar visibilidad
        		pFieldNewPassword.setVisible(false);
        		pFieldNewPassword.setManaged(false);
                
        		tFieldNewPassword.setVisible(true);
        		tFieldNewPassword.setManaged(true);
            }
        	else 
        	{
                // Sincronizar texto: de visible a oculto (por si el usuario editó mientras veía)
        		pFieldNewPassword.setText(tFieldNewPassword.getText());
                
                // Intercambiar visibilidadºs
        		tFieldNewPassword.setVisible(false);
        		tFieldNewPassword.setManaged(false);
                
        		pFieldNewPassword.setVisible(true);
        		pFieldNewPassword.setManaged(true);
            }
        	
        });
		
		newPasswordConfirmationCheckBox.setOnAction(s->{
        	if (newPasswordConfirmationCheckBox.isSelected()) 
        	{
                // Sincronizar texto: de oculto a visible
        		tFieldNewPasswordConfirmation.setText(pFieldNewPasswordConfirmation.getText());
                
                // Intercambiar visibilidad
        		pFieldNewPasswordConfirmation.setVisible(false);
        		pFieldNewPasswordConfirmation.setManaged(false);
                
        		tFieldNewPasswordConfirmation.setVisible(true);
        		tFieldNewPasswordConfirmation.setManaged(true);
            }
        	else 
        	{
                // Sincronizar texto: de visible a oculto (por si el usuario editó mientras veía)
        		pFieldNewPasswordConfirmation.setText(tFieldNewPasswordConfirmation.getText());
                
                // Intercambiar visibilidadºs
        		tFieldNewPasswordConfirmation.setVisible(false);
        		tFieldNewPasswordConfirmation.setManaged(false);
                
        		pFieldNewPasswordConfirmation.setVisible(true);
        		pFieldNewPasswordConfirmation.setManaged(true);
            }
        	
        });
		
		
		updatePasswordButton.setOnAction(s->{
			String oldPassword = null;
			if(pFieldOldPassword.isVisible())
			{
				oldPassword = pFieldOldPassword.getText();
			}
			else
			{
				oldPassword = tFieldOldPassword.getText();
			}
			String newPassword = null;
			if(pFieldNewPassword.isVisible())
			{
				newPassword = pFieldNewPassword.getText();
			}
			else
			{
				newPassword = tFieldNewPassword.getText();
			}
			String newPasswordConfirmation = null;
			if(pFieldNewPasswordConfirmation.isVisible())
			{
				newPasswordConfirmation = pFieldNewPasswordConfirmation.getText();
			}
			else
			{
				newPasswordConfirmation = tFieldNewPasswordConfirmation.getText();
			}
			if(oldPassword == "" || newPassword == "" || newPasswordConfirmation == "")
			{
				ErrorHandler.showError("Rellena todos los campos para continuar");
			}
			else
			{
				if(!newPassword.equals(newPasswordConfirmation))
				{
					ErrorHandler.showError(new FailedPasswordVerificationException());
				}
				else
				{
					try
					{
						AppContext.session.changeUserPassword(oldPassword, newPassword);
						ConfirmationPopUps.userPasswordChangedConfirmationMessage();
					} 
					catch(DuplicatePasswordException | InvalidPasswordException | FailedPasswordUpdateException e)
					{
						ErrorHandler.showError(e);
					}
					catch (Exception e)
					{
						ErrorHandler.showError("No se ha podido actualizar la contraseña, inténtalo de nuevo");
					}
				}
			}
		});
		
		this.root.setCenter(udpatePasswordLayout);
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
        
        //this.showLoginPage(stage);
        
        //Esto te pone directamente como un usuario de prueba

		AppContext.session.sessionUser = new User("prueba@prueba.net","Password123@","Usuario Prueba",Gender.MALE,LocalDate.now(),true,true);
        this.showUserPage(stage);
        
        
        stage.setTitle("Aplicacion Proyecto");
        stage.setScene(escena);
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.setMinHeight(WINDOW_MIN_HEIGHT);
        stage.setMinWidth(WINDOW_MIN_WIDTH);
        stage.show();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/undertaleHeart.png")));
        escena.setOnMouseClicked(e->{
        	root.requestFocus();
        });
        
        stage.setOnCloseRequest(s->{
        	if(!ConfirmationPopUps.confirmAppExit())
			{
				s.consume();
			}
        	else
        	{
        		Platform.exit();
				System.exit(0);
        	}
        });
    }
    
    public void showSidebar(Stage stage)
    {
    	//Foto de usuario y nombre
    			Label welcomeLabel = new Label(AppContext.session.getUser().getUsername());
    			welcomeLabel.getStyleClass().add("nombre-usuario");
    			Image userImage = new Image(MainJavaFX.class.getResourceAsStream("/defaultUserIcon.jpeg"));	
    			ImageView userProfilePictureView = new ImageView(userImage);
    			userProfilePictureView.setFitWidth(PROFILE_PIC_SIZE);
    			userProfilePictureView.setFitHeight(PROFILE_PIC_SIZE);
    			Circle clip = new Circle(PROFILE_PIC_SIZE/2, PROFILE_PIC_SIZE/2, PROFILE_PIC_SIZE/2);
    			userProfilePictureView.setClip(clip);
    			HBox userButton = new HBox(userProfilePictureView,welcomeLabel);
    			userButton.setSpacing(PROFILE_PIC_SPACING);
    			userButton.setPadding(new javafx.geometry.Insets(SIDEBAR_BUTTON_PADDING_VALUE));
    			userButton.getStyleClass().add("nombre-usuario-hbox");
    			userButton.setAlignment(Pos.CENTER_LEFT);
    			
    			//Botón crear post
    			Label createPostLabel = new Label(CREATE_POST_BUTTON_TEXT);
    			createPostLabel.getStyleClass().add("sidebar-button-text");
    			HBox createPostButton = new HBox(createPostLabel);
    			createPostButton.setAlignment(Pos.CENTER);
    			createPostButton.getStyleClass().add("sidebar-button");
    			createPostButton.setPadding(new javafx.geometry.Insets(SIDEBAR_BUTTON_PADDING_VALUE));
    			
    			//Leer post
    			Label readPostLabel = new Label(READ_POST_BUTTON_TEXT);
    			readPostLabel.getStyleClass().add("sidebar-button-text");
    			HBox readPostButton = new HBox(readPostLabel);
    			readPostButton.setAlignment(Pos.CENTER);
    			readPostButton.getStyleClass().add("sidebar-button");
    			readPostButton.setPadding(new javafx.geometry.Insets(SIDEBAR_BUTTON_PADDING_VALUE));
    			
    			//Ver tus posts
    			Label seeYourPostsLabel = new Label(SEE_YOUR_POSTS_BUTTON_TEXT);
    			seeYourPostsLabel.getStyleClass().add("sidebar-button-text");
    			HBox seeYourPostsButton = new HBox(seeYourPostsLabel);
    			seeYourPostsButton.setAlignment(Pos.CENTER);
    			seeYourPostsButton.getStyleClass().add("sidebar-button");
    			seeYourPostsButton.setPadding(new javafx.geometry.Insets(SIDEBAR_BUTTON_PADDING_VALUE));
    			
    			//Cambiar contraseña
    			Label changePasswordLabel = new Label(CHANGE_YOUR_PASSWORD_BUTTON_TEXT);
    			changePasswordLabel.getStyleClass().add("sidebar-button-text");
    			HBox changePasswordButton = new HBox(changePasswordLabel);
    			changePasswordButton.setAlignment(Pos.CENTER);
    			changePasswordButton.getStyleClass().add("sidebar-button");
    			changePasswordButton.setPadding(new javafx.geometry.Insets(SIDEBAR_BUTTON_PADDING_VALUE));
    			
    			//Cambiar contraseña
    			Label changeProfilePictureLabel = new Label(CHANGE_YOUR_PROFILE_PICTURE_BUTTON_TEXT);
    			changeProfilePictureLabel.getStyleClass().add("sidebar-button-text");
    			HBox changeProfilePictureButton = new HBox(changeProfilePictureLabel);
    			changeProfilePictureButton.setAlignment(Pos.CENTER);
    			changeProfilePictureButton.getStyleClass().add("sidebar-button");
    			changeProfilePictureButton.setPadding(new javafx.geometry.Insets(SIDEBAR_BUTTON_PADDING_VALUE));
    			
    			//Borrar cuenta
    			Label deleteYourAccountLabel = new Label(DELETE_YOUR_ACCOUNT_BUTTON_TEXT);
    			deleteYourAccountLabel.getStyleClass().add("sidebar-button-text");
    			HBox deleteYourAccountButton = new HBox(deleteYourAccountLabel);
    			deleteYourAccountButton.setAlignment(Pos.CENTER);
    			deleteYourAccountButton.getStyleClass().add("sidebar-button");
    			deleteYourAccountButton.setPadding(new javafx.geometry.Insets(SIDEBAR_BUTTON_PADDING_VALUE));
    			
    			//Salir
    			Label exitLabel = new Label(EXIT_BUTTON_TEXT);
    			exitLabel.getStyleClass().add("sidebar-button-text");
    			Image exitImage = new Image(MainJavaFX.class.getResourceAsStream("/exitIcon.png"));	
    			ImageView exitView = new ImageView(exitImage);
    			exitView.setFitWidth(EXIT_ICON_SIZE);
    			exitView.setFitHeight(EXIT_ICON_SIZE);
    			HBox exitButton = new HBox(exitLabel,exitView);
    			exitButton.setSpacing(EXIT_ICON_SPACING);
    			exitButton.setAlignment(Pos.CENTER);
    			exitButton.getStyleClass().add("sidebar-exit-button");
    			exitButton.setPadding(new javafx.geometry.Insets(SIDEBAR_BUTTON_PADDING_VALUE));

    			//Barra lateral
    			VBox sidebar = new VBox(userButton,createPostButton,readPostButton,seeYourPostsButton,changePasswordButton,changeProfilePictureButton, deleteYourAccountButton,exitButton);
    			sidebar.getStyleClass().add("sidebar");
    			sidebar.setPrefWidth(SIDEBAR_WIDTH);
    			
    			this.root.setLeft(sidebar);
    			
    			//Comportamientos
    			createPostButton.setOnMouseClicked(s->{
    				createPostButton.setId("sidebar-button-marked");
    				readPostButton.setId(null);
    				seeYourPostsButton.setId(null);
    				changePasswordButton.setId(null);
    				changeProfilePictureButton.setId(null);
    				deleteYourAccountButton.setId(null);
    				this.showCreatePostCenter(stage);
    			});
    			
    			changePasswordButton.setOnMouseClicked(s->{
    				createPostButton.setId(null);
    				readPostButton.setId(null);
    				seeYourPostsButton.setId(null);
    				changePasswordButton.setId("sidebar-button-marked");
    				changeProfilePictureButton.setId(null);
    				deleteYourAccountButton.setId(null);
    				this.showUpdatePasswordCenter(stage);
    			});
    			
    			deleteYourAccountButton.setOnMouseClicked(s->{
    				createPostButton.setId(null);
    				readPostButton.setId(null);
    				seeYourPostsButton.setId(null);
    				changePasswordButton.setId(null);
    				changeProfilePictureButton.setId(null);
    				deleteYourAccountButton.setId("sidebar-button-marked");
    				this.showDeleteAccountCenter(stage);
    			});
    			
    			exitButton.setOnMouseClicked(s->{
    				if(ConfirmationPopUps.confirmCloseSession())
    				{
    					AppContext.session.endSession();
    					this.showLoginPage(stage);
    				}
    			});

    }
    
    public static void main(String[] args) {
        launch();
    }
}
