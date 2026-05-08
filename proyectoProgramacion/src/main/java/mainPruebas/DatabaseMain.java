package mainPruebas;

import java.util.List;
import java.util.UUID;
import database.ResponseDAO;
import responses.Response;

public class DatabaseMain
{
	public static void main (String[] args)
	{
		ResponseDAO rDAO = new ResponseDAO();
		//PostDAO pDAO = new PostDAO();
		//uDAO.insert(new User("trollnimous2@gmail.com", "@--", "AguaTaFaka", Gender.MALE, LocalDate.of(2005, 4, 23)));
		/*List<User> lista = uDAO.listAll();
		for(User u : lista)
		{
			System.out.println(u);
		}*/
		//pDAO.insert(new Post(UUID.fromString("41801abe-5b41-4f48-a9a3-08b6652de016"),"Titulo1","Contenido1",10));
		List<Response> lista = rDAO.listAllFromUser(UUID.fromString("6e7dcb1b-1f56-4e74-bcdf-42752fceaac7"));
		for(Response r : lista)
		{
			System.out.println(r);
		}
		
	}
}
