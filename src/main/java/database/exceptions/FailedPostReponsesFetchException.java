
package database.exceptions;

public class FailedPostReponsesFetchException extends Exception
{
	private static final long serialVersionUID = 1L;
	private static final String ERR_MSG = "No se han podido obtener las respuestas al post\nPor favor, inténtalo de nuevo";
	
	public FailedPostReponsesFetchException()
	{
		super(ERR_MSG);
	}
}