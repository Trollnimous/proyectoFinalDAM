package inputs;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputUtils
{
	public static boolean validChoice(int choice, int minValueInclusive, int maxValueInclusive, boolean acceptsZero)
	{
		try {
			if(acceptsZero && choice == 0)
			{
				return true;
			}
			return (minValueInclusive <= choice && choice <= maxValueInclusive);
		}catch(InputMismatchException e)
		{
			return false;
		}
	}
	
	public static int askForNumber(Scanner sc)
	{
		try {
			int number = sc.nextInt();
			return number;
		}catch (InputMismatchException e)
		{
			return Integer.MIN_VALUE;
		}
	}
}
