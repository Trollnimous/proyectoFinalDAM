package mainPruebas;

import java.time.LocalDate;

import temporalConsoleDatabase.TemporalDatabase;
import users.User;
import users.gender.Gender;

public class DatabaseMain
{
	public static void main (String[] args)
	{
		TemporalDatabase temp = new TemporalDatabase();
		temp.printUsers();
	}
}
