package session.exceptions;

public class FailedPostToReadFetchException extends Exception
{
	private  final static long serialVersionUID = 1L;
	private final static String ERR_MSG = "No se ha podido obtener un post para leer\nPor favor, inténtalo de nuevo";
	
	public FailedPostToReadFetchException()
	{
		super(ERR_MSG);
	}
}
