package mainPruebas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import session.Session;
import temporalConsoleDatabase.TemporalDatabase;
import users.User;
import users.UserUtils;

public class Main
{

	public static void main(String[] args) throws IOException
	{
		Session activeSession = new Session();
		activeSession.startSession();
	}

}
