package session;

import java.time.LocalDate;
import java.util.Scanner;

import database.UserDAO;
import inputs.InputUtils;
import temporalConsoleDatabase.TemporalDatabase;
import temporalConsoleMenus.SessionMenu;
import users.User;
import users.UserUtils;
import users.gender.Gender;

public class Session
{
	private final Scanner sc = new Scanner(System.in);
	private User sessionUser;
	private UserDAO uDAO;
	
	public Session()
	{
		this.uDAO = new UserDAO();
		this.sessionUser = null;
	}
	
	public void startSession()
	{
		int sessionStatus = 1;
		while(sessionStatus > 0)
		{
			sessionStatus = SessionMenu.printMenu(sessionUser);
			switch(sessionStatus)
			{
				//Session has no user attached
				case 1:
					sessionStatus = this.attachUser();
					break;
				//User menu
				case 2:
					sessionStatus = userSession();
					break;
				//Exit
				case 0:
					break;
				default:
					System.out.println("[W]: Choice not recognized");
					break;
			}
		}
		SessionMenu.sayGoodbye();
	}
	
	private int userSession()
	{
		SessionMenu.printUserMenu();
		int choice = -1;
		do {
			choice = InputUtils.askForNumber(sc);
			sc.nextLine();
			if(!InputUtils.validChoice(choice, 1, 5, true))
			{
				System.out.println("[W]: Choice not recognised");
			}
		}while(!InputUtils.validChoice(choice, 1, 5, true));
		switch(choice)
		{
			//TODO: hacer casos
			case 0:
				return 0;
		}
		return -1;
	}
	
	private int attachUser()
	{
		int choice = -1;
		SessionMenu.logUserMenu();
		do {
			SessionMenu.askForChoice();
			choice = InputUtils.askForNumber(sc);
			sc.nextLine();
			if(!(InputUtils.validChoice(choice, 1, 2, true)))
			{
				SessionMenu.choiceInvalidMessage();
			}
		}while(!InputUtils.validChoice(choice, 1, 2, true));
		switch (choice)
		{
			case 1:
				this.userLogin();
				break;
			case 2:
				this.signUpUser();
				break;
			case 0:
				this.endSession();
				return 0;
			default:
				System.err.println("[E]: Session.attachUser() - Choice not recognized");
				break;
				
		}
		return 1;
	}
	
	//Ends current session safely
	public void endSession()
	{
		
	}
	
	//Makes user sign up
	private void signUpUser()
	{
		String email = "";
		String username = "";
		String password = "";
		Gender gender = null;
		LocalDate dateOfBirth = null;
		System.out.println("\nUser login\n------------\n");
		boolean validEmail = false;
		do {
			System.out.print("Enter your email: ");
			email = sc.nextLine();
			if(UserUtils.validEmail(email)&&!this.uDAO.existsEmail(email))
			{
				validEmail = true;
			}
			else
			{
				System.out.println("\n[E]: Invalid email\n");
			}
		}while(!validEmail);
		boolean validUsername = false;
		do {
			System.out.print("Enter your username: ");
			username = sc.nextLine();
			if(UserUtils.validUsername(username)&&!this.uDAO.existsUsername(username))
			{
				validUsername = true;
			}
			else
			{
				System.out.println("\n[E]: Invalid username\n");
			}
		}while(!validUsername);
		boolean validPassword = false;
		do {
			System.out.print("Enter your password:\n(1 Special character, 1 Uppercase, 1 Lowercase, 1 Number): ");
			password = sc.nextLine();
			if(UserUtils.validPassword(password))
			{
				validPassword = true;
			}
			else
			{
				System.out.println("\n[E]: Invalid password\n");
			}
		}while(!validPassword);
		System.out.println("Select your gender: \n");
		System.out.println("[1] Male");
		System.out.println("[2] Female");
		System.out.println("[3] Other");
		int choice = -1;
		do {
			choice = InputUtils.askForNumber(sc);
			sc.nextLine();
			switch(choice)
			{
				case 1:
					gender = Gender.MALE;
					break;
				case 2:
					gender = Gender.FEMALE;
					break;
				case 3:
					gender = Gender.OTHER;
					break;
				default:
					System.out.println("[W] Choice not recognised");
					break;
			}
		}while(!InputUtils.validChoice(choice, 1, 3, false));
		//TODO: meter para introducir día de nacimiento y consetiemiento de emails
		User userToAttach = new User(email, password, username, gender, LocalDate.of(2026, 4, 11));
		this.uDAO.insert(userToAttach);
		this.sessionUser = userToAttach;
	}
	
	//Logs in user in session
	private void userLogin()
	{
		User userToLogin = null;
		String email;
		String password;
		boolean loginCompleted = false;
		do {
			SessionMenu.askForEmail();
			email = sc.nextLine();
			SessionMenu.askForPassword();
			password = sc.nextLine();
			userToLogin = this.uDAO.searchByEmail(email);
			if(userToLogin.correctPassword(password))
			{
				this.sessionUser = userToLogin;
				loginCompleted = true;
				SessionMenu.loginCompleted();
			}
			else
			{
				SessionMenu.loginFailed();
			}
		}while(!loginCompleted);
	}
	
}
