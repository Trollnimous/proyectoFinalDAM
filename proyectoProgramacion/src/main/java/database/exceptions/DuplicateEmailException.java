package database.exceptions;

public class DuplicateEmailException extends Exception
{
	private static final long serialVersionUID = 1L;
	private static final String ERR_MSG = "Este email ya está registrado";
	
	public DuplicateEmailException()
	{
		super(ERR_MSG);
	}
}
