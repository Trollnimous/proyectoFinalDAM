package session.exceptions;

public class LoginFailedException extends Exception
{	
	private static final long serialVersionUID = 1L;
	private final static String ERR_MSG = "Email o contraseña incorrecto";
	
	public LoginFailedException()
	{
		super(ERR_MSG);
	}
}

	
