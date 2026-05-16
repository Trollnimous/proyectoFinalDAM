package post.exceptions;

public class InvalidPostContentLengthException extends Exception
{
	private static final long serialVersionUID = 1L;
	private static final String ERR_MSG = "Longitud del contenido inválida\nDebe contenter de 20 a 2000 carácteres";
	
	public InvalidPostContentLengthException()
	{
		super(ERR_MSG);
	}
}
