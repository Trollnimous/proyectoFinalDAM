package responses.exceptions;

public class InvalidResponseContentLengthException extends Exception
{
	private final static long serialVersionUID = 1L;
	private final static String ERR_MSG = "Longitud de contenido inválida\nDebe contenter de 10 a 1000 carácteres";
	
	public InvalidResponseContentLengthException()
	{
		super(ERR_MSG);
	}
}
