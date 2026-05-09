package users.exceptions;

public class InvalidPasswordException extends Exception
{
	private static final long serialVersionUID = 1L;
	private static final String ERR_MSG = "La contraseña no es válida.\nTiene que tener de 8 a 30 carácteres, 1 número, una mayúscula y 1 símbolo";
	
	public InvalidPasswordException()
	{
		super(ERR_MSG);
	}
}
