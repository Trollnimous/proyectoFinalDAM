package users.exceptions;

public class FailedPasswordVerificationException extends Exception
{
	private static final long serialVersionUID = 1L;
	public static final String ERR_MSG = "Las contraseñas deben coincidir";
	
	public FailedPasswordVerificationException()
	{
		super(ERR_MSG);
	}
}
