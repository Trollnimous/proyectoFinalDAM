package session;

import java.util.Scanner;

import temporalConsoleDatabase.TemporalDatabase;
import temporalConsoleMenus.SessionMenu;
import users.User;

public class Session
{
	private final Scanner sc = new Scanner(System.in);
	private TemporalDatabase database;
	private User sessionUser;
	
	public Session()
	{
		this.database = new TemporalDatabase();
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
					this.attachUser();
				//Exit
				case 0:
					SessionMenu.sayGoodbye();
					break;
				default:
					System.out.println("[W]: Choice not recognized");
					break;
			}
		}
	}
	
	private void attachUser()
	{
		int choice = -1;
		SessionMenu.logUserMenu();
		do {
			SessionMenu.askForChoice();
			choice = sc.nextInt();
			sc.nextLine();
			if(choice != 1 && choice != 2)
			{
				SessionMenu.choiceInvalidMessage();
			}
		}while(choice != 1 && choice != 2);
		if(choice == 1)
		{
			this.userLogin();
		}
		else
		{
			signUpUser();
		}
	}
	
	//Makes user sign up
	private void signUpUser()
	{
		//TODO: hacer el signup
	}
	
	//Logs in user in session
	private void userLogin()
	{
		String email;
		String password;
		boolean loginCompleted = false;
		do {
			SessionMenu.askForEmail();
			email = sc.nextLine();
			if(this.database.existsEmail(email))
			{
				System.out.println("Existe");
			}
			SessionMenu.askForPassword();
			password = sc.nextLine();
			if(this.database.loginAccepted(email, password))
			{
				this.sessionUser = this.database.getUserFromMail(email);
				loginCompleted = true;
			}
			else
			{
				SessionMenu.loginFailed();
			}
		}while(!loginCompleted);
	}
	
}
