package mainPruebas;

import java.io.IOException;
import session.Session;

public class Main
{

	public static void main(String[] args) throws IOException
	{
		Session activeSession = new Session();
		try 
		{
			activeSession.startSession();
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
		}finally
		{
			activeSession.closeScanner();
		}
		
	}

}
