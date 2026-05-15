package session.exceptions;

public class DuplicatePasswordException extends Exception
{
	private final static long serialVersionUID = 1L;
	private final static String ERR_MSG = "Tu nueva contraseña no puede ser igual que la anterior";
	
	public DuplicatePasswordException()
	{
		super(ERR_MSG);
	}
}
