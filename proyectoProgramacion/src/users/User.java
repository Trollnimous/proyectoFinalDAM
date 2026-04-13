package users;

import java.time.LocalDate;
import java.util.UUID;
import java.time.LocalTime;

import de.mkammerer.argon2.Argon2Factory;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import users.roles.Rol;

public class User
{
	private UUID userID;
	private String email;
	private String username;
	private String password;
	private String profilePicURL;
	private String description;
	private char gender;
	private LocalDate birthdate;
	private Rol role;
	
	private boolean acceptsResponseEmails;
	private boolean acceptsMainteinanceEmails;
	
	private LocalDate accountCreationDate;
	private LocalDate accountLastModificationDate;
	private LocalDate lastPostDate;
	private LocalTime lastPostHour;
	private LocalDate lastReadingDate;
	private LocalTime lastReadingHour;
	private LocalDate lastLoginDate;
	
	private int availableReadings;
	private int readPosts;
	private int writtenPosts;
	
	
	//Constructores
	public User(String email, String password)
	{
		if(UserUtils.validEmail(email))
		{
			this.email = email;
		}
		else
		{
			this.email = "";
		}
		this.password = User.createPasswordHash(password);
	}
	
	//Getters
	public String getEmail()
	{
		return this.email;
	}
	
	public String getPassword()
	{
		return this.password;
	}
	
	//Setters
	
	//Metodos
	public boolean correctPassword(String password)
	{
		return Argon2Factory.create().verify(this.password, password.toCharArray());
	}
	
	private static String createPasswordHash(String password)
	{
		return Argon2Factory.create().hash(3, 65536, 1, password.toCharArray());
	}
}
