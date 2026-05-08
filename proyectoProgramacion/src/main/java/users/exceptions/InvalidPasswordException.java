package users.exceptions;

public class InvalidPasswordException extends Exception
{
	private static final long serialVersionUID = 1L;
	private static final String ERR_MSG = "La contraseña no es válida";
	
	public InvalidPasswordException()
	{
		super(ERR_MSG);
	}
}
