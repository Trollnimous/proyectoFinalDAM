package post;

public class PostUtils
{
	private static final int TITLE_MIN_LENGTH = 4;
	private static final int TITLE_MAX_LENGTH = 50;
	
	private static final int CONTENT_MIN_LENGTH = 20;
	private static final int CONTENT_MAX_LENGTH = 2000;
	
	public static boolean validTitleLength(String titleToCheck)
	{
		return ((TITLE_MIN_LENGTH<=titleToCheck.length())&&(titleToCheck.length()<=TITLE_MAX_LENGTH));
	}
	
	public static boolean validContentLength(String contentToCheck)
	{
		return ((CONTENT_MIN_LENGTH<=contentToCheck.length())&&(contentToCheck.length()<=CONTENT_MAX_LENGTH));
	}
}
