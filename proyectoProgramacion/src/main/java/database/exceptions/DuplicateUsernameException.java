package database.exceptions;

public class DuplicateUsernameException extends Exception
{
	private static final long serialVersionUID = 1L;
	private static final String ERR_MSG = "Este usuario ya está cogido";
	
	public DuplicateUsernameException()
	{
		super(ERR_MSG);
	}
}
