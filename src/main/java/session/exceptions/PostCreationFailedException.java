package session.exceptions;

public class PostCreationFailedException extends Exception
{
	private static final long serialVersionUID = 1L;
	private static final String ERR_MSG = "No se ha podido crear el post\nPor favor, inténtalo de nuevo";
	
	public PostCreationFailedException()
	{
		super(ERR_MSG);
	}
}
