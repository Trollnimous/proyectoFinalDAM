package post.exceptions;

public class InvalidPostTitleLengthException extends Exception
{
	private static final long serialVersionUID = 1L;
	private static final String ERR_MSG = "Longitud del título inválida\nDebe contener de 4 a 50 carácteres";
	
	public InvalidPostTitleLengthException()
	{
		super(ERR_MSG);
	}
}
