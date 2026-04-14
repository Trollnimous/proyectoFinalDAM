package mainPruebas;

import temporalConsoleDatabase.TemporalDatabase;
import users.User;

public class DatabaseMain
{
	public static void main (String[] args)
	{
		TemporalDatabase temp = new TemporalDatabase();
		temp.printUsers();
	}
}
