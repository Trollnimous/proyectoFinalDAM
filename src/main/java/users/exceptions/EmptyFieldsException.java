package users.exceptions;

public class EmptyFieldsException extends Exception
{
	private static final long serialVersionUID = 1L;
	private static final String ERR_MSG = "Rellena los campos faltantes";
	
	public EmptyFieldsException()
	{
		super(ERR_MSG);
	}
}
