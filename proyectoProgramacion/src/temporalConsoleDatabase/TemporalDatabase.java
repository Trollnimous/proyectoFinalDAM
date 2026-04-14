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
	private Map<String, String> emailsAndPasswords;
	
	//Constructor
	public TemporalDatabase()
	{
		
		this.usersDB = new HashSet<User>();
		this.loadUsersFromDatabase();
		this.emailsAndPasswords = this.createUserPasswordMap();
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
		return this.emailsAndPasswords.containsKey(email);
	}
	
	//Imprime todos los usuarios de la base de datos
	public void printUsers()
	{
		for(User u : this.usersDB)
		{
			System.out.println(u);
		}
	}
	
	//Crea un mapa con todos los eamil como clave y el hash de la contraseña como valor
	private Map<String,String> createUserPasswordMap()
	{
		Map<String,String> mapDatabase = new HashMap<String,String>();
		for(User u : this.usersDB)
		{
			mapDatabase.put(u.getEmail(), u.getPassword());
		}
		return mapDatabase;
	}
	
	//Registra un usuario y devuelve si ha podido hacerlo
	public boolean registerUser(User userToRegister)
	{
		if(this.usersDB.add(userToRegister))
		{
			this.emailsAndPasswords.put(userToRegister.getEmail(), userToRegister.getPassword());
			TemporalDatabase.saveUsersToDatabase(usersFileDirectory, this.usersDB);
			return true;
		}
		return false;
	}
	
	//Carga en el programa todos los usuarios de la base de datos
	private void loadUsersFromDatabase()
	{
		this.usersDB =  usersFromDatabase(usersFileDirectory);
	}
	
	//Borra la base de datos de usuarios, luego deberiamos borrarlo, es para pruebas
	public static void deleteUsersDB()
	{
		Set<User> empty = new HashSet<User>();
		saveUsersToDatabase(usersFileDirectory, empty);
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
	public static boolean saveUsersToDatabase(String directory, Set<User> users)
	{
		try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(directory)))
		{
			oos.writeObject(users);
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
		for(User u : this.usersDB)
		{
			if(u.getEmail().equals(email))
			{
				return u;
			}
		}
		return null;
	}
	
	//Devuelve true si el usuario con ese email tiene esa contraseña
	public boolean loginAccepted(String email, String password)
	{
		String passwordHash = this.emailsAndPasswords.get(email);
		if(passwordHash == null)
		{
			return false;
		}
		return UserUtils.correctPassword(passwordHash, password);
	}
}
