package users;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;

public abstract class UserUtils
{
	private static final int MAX_PASSWORD_LENGTH = 30;
	private static final int MIN_PASSWORD_LENGTH = 8;
	private static final int MAX_USERNAME_LENGTH = 20;
	private static final int MIN_USERNAME_LENGTH = 4;
	private static final String USERNAME_REGEX = "^[A-Za-z0-9_.]+$";
	private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[^A-Za-z0-9])(?!.*[.])\\S*$";
	
	public static boolean validEmail(String email)
	{
		try
		{
			InternetAddress comprobar = new InternetAddress(email);
			comprobar.validate();
			return true;
		} catch (AddressException e)
		{
			return false;
		}
	}
	
	public static boolean validUsername(String username)
	{
		if(validUsernameLength(username)&& username.matches(USERNAME_REGEX))
		{
			return true;
		}
		return false;
	}
	
	private static boolean validUsernameLength(String username)
	{
		return (UserUtils.MIN_USERNAME_LENGTH<=username.length()&&username.length()<=UserUtils.MAX_USERNAME_LENGTH);
	}
	
	public static boolean validPassword(String password)
	{
		if(validPasswordLength(password)&& password.matches(PASSWORD_REGEX))
		{
			return true;
		}
		return false;
	}
	
	private static boolean validPasswordLength(String password)
	{
		return (UserUtils.MIN_PASSWORD_LENGTH<=password.length()&&password.length()<=UserUtils.MAX_PASSWORD_LENGTH);
	}
}
