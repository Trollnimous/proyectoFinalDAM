package responses;

public class ResponseUtils
{
	private final static int RESPONSE_MIN_LENGTH = 10;
	private final static int RESPONSE_MAX_LENGTH = 1000;

	public static boolean validResponseContentLength(String responseContent)
	{
		return ((RESPONSE_MIN_LENGTH<=responseContent.length())&&(responseContent.length()<=RESPONSE_MAX_LENGTH));
	}
}
