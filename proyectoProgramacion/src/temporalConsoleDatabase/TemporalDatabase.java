package temporalConsoleDatabase;

import users.User;
import users.UserUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TemporalDatabase
{
	private static final String usersFileDirectory = "databaseFiles/users.ser";
	private Set<User> usersDB;
	
	//Constructor
	public TemporalDatabase()
	{
		
		this.usersDB = new HashSet<User>();
		this.loadUsersFromDatabase();
	}
	
	
	
	//Getters
	public Set<User> getUserDB()
	{
		return this.usersDB;
	}
	
	
	
	//Métodos
	
	//Devuelve true si el email existe
	public boolean existsEmail(String email)
	{
		/*for(User u : this.usersDB)
		{
			if(u.getEmail().equals(email))
			{
				return true;
			}
		}
		return false;*/
		return this.usersDB.stream().anyMatch( u -> u.getEmail().equals(email));
	}
	
	//Imprime todos los usuarios de la base de datos
	public void printUsers()
	{
		/*for(User u : this.usersDB)
		{
			System.out.println(u);
		}*/
		this.usersDB.stream().forEach(  u -> System.out.println(u)  );
	}
	
	
	//Registra un usuario y devuelve si ha podido hacerlo
	public boolean registerUser(User userToRegister)
	{
		if(this.usersDB.add(userToRegister))
		{
			this.saveUsersToDatabase();
			return true;
		}
		return false;
	}
	
	//Carga en el programa todos los usuarios de la base de datos
	private void loadUsersFromDatabase()
	{
		this.usersDB =  usersFromDatabase(usersFileDirectory);
		if(this.usersDB == null)
		{
			System.err.println("[W]: Database is null, creating fresh database");
			this.usersDB = new HashSet<User>();
		}
	}
	
	//Borra la base de datos de usuarios, luego deberiamos borrarlo, es para pruebas
	public void deleteUsersDB()
	{
		this.usersDB = new HashSet<User>();
		this.saveUsersToDatabase();
	}
	
	//Devuelve un set con todos los usuarios que estaban serializados de la direccion pasada
	@SuppressWarnings("unchecked")
	public static Set<User> usersFromDatabase(String directory)
	{
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(directory)))
		{
			try
			{
				return (Set<User>)(ois.readObject());
			} catch (ClassNotFoundException e)
			{
				System.err.println("[E]: Could not find User class");
			}
		}catch(FileNotFoundException e)
		{
			System.err.println("[E]: Could not find database file");
		}
		catch(IOException e)
		{
			System.err.println("[E]: Could not load Set<User> from database");
		}
		return null;
	}	
		
	//Guarda un set de usuarios en la base de datos
	public boolean saveUsersToDatabase()
	{
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(TemporalDatabase.usersFileDirectory)))
		{
			oos.writeObject(this.usersDB);
			return true;
		} catch (FileNotFoundException e)
		{
			System.err.println("[E]: Could not find database file");
		} catch (IOException e)
		{
			System.err.println("[E]: Could not load Set<User> to database");
		}
		return false;
	}	
	
	//Devuelve un usuario por su email
	public User getUserFromMail(String email)
	{
		/*for(User u : this.usersDB)
		{
			if(u.getEmail().equals(email))
			{
				return u;
			}
		}
		return null;*/
		return this.usersDB.stream().filter(u -> u.getEmail().equals(email)).findFirst().orElse(null);
	}
	
	//Devuelve true si el usuario con ese email tiene esa contraseña
	public boolean loginAccepted(String email, String password)
	{
		User user = this.getUserFromMail(email);
		if(user == null)
		{
			return false;
		}
		return user.correctPassword(password);
	}
}
