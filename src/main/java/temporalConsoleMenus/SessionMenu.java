package temporalConsoleMenus;

import users.roles.Role;
import users.User;

public class SessionMenu
{
	public static int printMenu(User userMenu)
	{
		if(userMenu == null)
		{
			return printStartupMenu();
		}
		Role roleSwitch = userMenu.getRole();
		switch(roleSwitch)
		{
			case Role.USER:
				return 2;
			case Role.ADMIN:
				return 3;
			case Role.GUEST:
				break;
			default:
				System.out.println("[E]: Could not process user role");
				return -1;
		}
		return -1;
	}
	
	public static void printUserMenu()
	{
		System.out.println("[1] Post");
		System.out.println("[2] Read");
		System.out.println("[3] Read and respond");
		System.out.println("[4] See your posts");
		System.out.println("[5] See responses to your posts");
		System.out.println("[6] Change password");
		System.out.println("[7] Delete your user");
		System.out.println("[0] Exit\n");
	}
	
	public static void printUserName(String username)
	{
		System.out.println("-------------------------\n");
		System.out.println("Welcome, "+username);
		System.out.println("-------------------------\n");
	}
	
	public static void printAdminMenu()
	{
		System.out.println("[1] Change password");
		System.out.println("[2] Delete user");
		System.out.println("[0] Exit\n");
	}
	
	private static int printStartupMenu()
	{
		System.out.println("-------");
		System.out.println("WELCOME");
		System.out.println("-------");
		return 1;
	}
	
	public static void sayGoodbye()
	{
		System.out.println("\nHave a nice day!");
	}
	
	public static void askForEmail()
	{
		System.out.print("Email: ");
	}
	
	public static void askForPassword()
	{
		System.out.print("Password: ");
	}
	
	public static void loginFailed()
	{
		System.out.println("[E]: Incorrect email or password");
	}
	
	public static void loginCompleted()
	{
		System.out.println("\nLogin Completed!\n");
	}
	
	public static void askForChoice()
	{
		System.out.print("> ");
	}
	
	public static void logUserMenu()
	{
		System.out.println("[1] Log in");
		System.out.println("[2] Sign up");
		System.out.println("[0] Exit program\n");
	}
	
	public static void choiceInvalidMessage()
	{
		System.out.println("[E] Choice was not valid");
	}

}
