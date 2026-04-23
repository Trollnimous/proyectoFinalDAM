package users.roles;

public enum Role
{
	USER, ADMIN, GUEST;
	
	public static Role getRole(String stringToDecode)
	{
		switch(stringToDecode)
		{
			case "USER":
				return Role.USER;
			case "ADMIN":
				return Role.ADMIN;
			case "GUEST":
				return Role.GUEST;
			default:
				return Role.GUEST;
		}
	}
}
