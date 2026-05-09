package users.exceptions;

public class InvalidDateFormatException extends Exception
{
	private static final long serialVersionUID = 1L;
	private static final String ERR_MSG = "Introduce un formato de fecha válido";
	
	public InvalidDateFormatException()
	{
		super(ERR_MSG);
	}
}
