package users.exceptions;

public class InvalidDateOfBirthException extends Exception
{
	private static final long serialVersionUID = 1L;
	private static final String ERR_MSG = "Debes de tener más de 18 años para registrarte";
	
	public InvalidDateOfBirthException()
	{
		super(ERR_MSG);
	}
}
