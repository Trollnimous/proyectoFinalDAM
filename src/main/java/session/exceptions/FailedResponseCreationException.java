package session.exceptions;

public class FailedResponseCreationException extends Exception
{
	private final static long serialVersionUID = 1L;
	private final static String ERR_MSG = "No se ha podido crear la respuesta al post\nPor favor, inténtalo de nuevo";
	
	public FailedResponseCreationException()
	{
		super(ERR_MSG);
	}
}
