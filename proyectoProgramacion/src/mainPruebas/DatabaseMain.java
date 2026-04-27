package mainPruebas;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.sql.Connection;
import database.DatabaseConnection;
import database.PostDAO;
import database.UserDAO;
import post.Post;
import temporalConsoleDatabase.TemporalDatabase;
import users.User;
import users.gender.Gender;

public class DatabaseMain
{
	public static void main (String[] args)
	{
		PostDAO pDAO = new PostDAO();
		UserDAO uDAO = new UserDAO();
		//uDAO.insert(new User("trollnimous2@gmail.com", "@--", "AguaTaFaka", Gender.MALE, LocalDate.of(2005, 4, 23)));
		/*List<User> lista = uDAO.listAll();
		for(User u : lista)
		{
			System.out.println(u);
		}*/
		pDAO.insert(new Post(UUID.fromString("41801abe-5b41-4f48-a9a3-08b6652de016"),"Titulo1","Contenido1",10));
	}
}
