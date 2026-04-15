package inputs;

public class InputUtils
{
	public static boolean validChoice(int choice, int minValueInclusive, int maxValueInclusive, boolean acceptsZero)
	{
		if(acceptsZero && choice == 0)
		{
			return true;
		}
		return (minValueInclusive <= choice && choice <= maxValueInclusive);
	}
}
