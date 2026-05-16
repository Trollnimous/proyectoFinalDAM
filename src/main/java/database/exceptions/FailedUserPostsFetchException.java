package database.exceptions;

public class FailedUserPostsFetchException extends Exception
{
	private static final long serialVersionUID = 1L;
	private static final String ERR_MSG = "No se han podido obtener tus posts\nPor favor, inténtalo de nuevo";
	
	public FailedUserPostsFetchException()
	{
		super(ERR_MSG);
	}
}
