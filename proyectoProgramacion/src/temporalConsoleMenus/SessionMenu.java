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
			case USER:
				break;
			case ADMIN:
				break;
			case GUEST:
				break;
			default:
				System.out.println("[E]: Could not process user role");
				return -1;
		}
		return -1;
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
		System.out.println("Have a nice day!");
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
	
	public static void askForChoice()
	{
		System.out.print("> ");
	}
	
	public static void logUserMenu()
	{
		System.out.println("[1] Log in");
		System.out.println("[2] Sign up\n");
	}
	
	public static void choiceInvalidMessage()
	{
		System.out.println("[E] Choice was not valid");
	}

}
