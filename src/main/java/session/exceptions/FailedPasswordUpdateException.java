package session.exceptions;

public class FailedPasswordUpdateException extends Exception
{
	private static final long serialVersionUID = 1L;
	private static final String ERR_MSG = "Introduce tu antigua contraseña";
	
	public FailedPasswordUpdateException()
	{
		super(ERR_MSG);
	}
}
