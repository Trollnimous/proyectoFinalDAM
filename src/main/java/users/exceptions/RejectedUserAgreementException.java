package users.exceptions;

public class RejectedUserAgreementException extends Exception
{
	private static final long serialVersionUID = 1L;
	private static final String ERR_MSG = "Se tienen que rellenar los campos obligatorios";
	
	public RejectedUserAgreementException()
	{
		super(ERR_MSG);
	}
}
