package users.exceptions;

public class InvalidUsernameException extends Exception
{
	private static final long serialVersionUID = 1L;
	private static final String ERR_MSG = "Nombre de usuario inválido";
	
	public InvalidUsernameException()
	{
		super(ERR_MSG);
	}
}
