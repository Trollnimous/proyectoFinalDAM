package database.exceptions;

public class FailedUserDeletionException extends Exception
{
	private static final long serialVersionUID = 1L;
	private static final String ERR_MSG = "No se ha podido borrar el usuario de la base de datos";
	
	public FailedUserDeletionException()
	{
		super(ERR_MSG);
	}
}
