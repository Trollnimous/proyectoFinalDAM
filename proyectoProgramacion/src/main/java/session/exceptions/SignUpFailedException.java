package session.exceptions;

public class SignUpFailedException extends Exception
{
	private static final long serialVersionUID = 1L;
	private static final String ERR_MSG = "Error al registrarse, por favor, inténtalo de nuevo";
	
	public SignUpFailedException()
	{
		super(ERR_MSG);
	}
}
