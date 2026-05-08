package users.exceptions;

public class InvalidEmailException extends Exception
{
	private static final long serialVersionUID = 1L;
	private static final String ERR_MSG = "Email inválido";
	
	
	public InvalidEmailException()
	{
		super(ERR_MSG);
	}
}
