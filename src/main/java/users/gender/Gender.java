package users.gender;

public enum Gender
{
	MALE, FEMALE, OTHER;
	
	public static Gender getGender(String stringToDecode)
	{
		switch(stringToDecode)
		{
			case "M":
				return Gender.MALE;
			case "F":
				return Gender.FEMALE;
			default:
				return Gender.OTHER;
		}
	}
}
