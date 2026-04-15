package session;

import java.util.Scanner;

import inputs.InputUtils;
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
					sessionStatus = this.attachUser();
					break;
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
	
	private int attachUser()
	{
		int choice = -1;
		SessionMenu.logUserMenu();
		do {
			SessionMenu.askForChoice();
			choice = sc.nextInt();
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
				SessionMenu.sayGoodbye();
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
				System.out.println("Debug: Existe");
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
