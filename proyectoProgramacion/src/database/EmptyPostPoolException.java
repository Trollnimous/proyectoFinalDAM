package database;
 
public class EmptyPostPoolException extends Exception
{

	private static final long serialVersionUID = 1L;
	private static final String ERROR_MESSAGE = "The post pool is empty";
	
	public EmptyPostPoolException()
	{
		super(ERROR_MESSAGE);
	}
}
