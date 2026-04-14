package users;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import java.time.LocalTime;

import de.mkammerer.argon2.Argon2Factory;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import users.roles.Role;

public class User implements Serializable
{
	private static final long serialVersionUID = 1L;
	private UUID userID;
	private String email;
	private String username;
	private String password;
	private String profilePicURL;
	private char gender;
	private LocalDate birthdate;
	private Role role;
	
	private boolean acceptsResponseEmails;
	private boolean acceptsMainteinanceEmails;
	
	private LocalDate accountCreationDate;
	private LocalDate accountLastModificationDate;
	private LocalDate lastPostDate;
	private LocalTime lastPostHour;
	private LocalDate lastReadingDate;
	private LocalTime lastReadingHour;
	private LocalDate lastLoginDate;
	
	private int availableWritings;
	private int availableReadings;
	private int readPosts;
	private int writtenPosts;
	
	
	//Constructores
	public User(String email, String password)
	{
		this.userID = UUID.randomUUID();
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
	
	public Role getRole()
	{
		return this.role;
	}
	
	//Setters
	
	
	
	//Metodos
	
	private static String createPasswordHash(String password)
	{
		return Argon2Factory.create().hash(3, 65536, 1, password.toCharArray());
	}
	
	//Impresion por consola
	@Override
	public String toString()
	{
		return String.format("Email: %s, ID: %s HashContraseña: %s", this.email, this.userID.toString(),this.password);
	}
	
	//Método equals
	@Override
	public boolean equals(Object o)
	{
		if(o == null)
		{
			return false;
		}
		if(o == this)
		{
			return true;
		}
		if(!(o instanceof User))
		{
			return false;
		}
		User u = (User)o;
		return ((this.userID.equals(u.userID))&&(this.username.equals(u.username)));
	}
	
	//Hashchode
	@Override
	public int hashCode()
	{
		return Objects.hash(this.userID,this.username);
	}
}
