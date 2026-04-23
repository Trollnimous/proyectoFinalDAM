package users;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import java.time.LocalTime;

import de.mkammerer.argon2.Argon2Factory;
import users.gender.Gender;
import users.roles.Role;

public class User implements Serializable
{
	private static final long serialVersionUID = 1L;
	private static final int MAX_SAVEABLE_WRITINGS = 3;
	private static final int MAX_SAVEABLE_READINGS = 6;
	
	private UUID userID;
	private String email;
	private String username;
	private String password;
	private String profilePicURL;
	private Gender gender;
	private LocalDate dateOfBirth;
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
	private LocalTime lastLoginHour;
	
	private boolean hasNewResponses;
	private int availableWritings;
	private int availableReadings;
	private int readPosts;
	private int writtenPosts;
	
	
	//Constructores
	public User(String email, String password, String username, Gender gender, LocalDate dateOfBirth)
	{
		this.userID = UUID.randomUUID();
		this.email = email;
		this.password = User.createPasswordHash(password);
		this.username = username;
		this.profilePicURL = null;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.role = Role.USER;
		
		this.acceptsResponseEmails = false;
		this.acceptsMainteinanceEmails = false;
		
		this.accountCreationDate = LocalDate.now();
		this.updateModificationDate();
		this.updateLoginDate();
		this.updateLastPostDate();
		this.updateLastReadingDate();
		
		this.hasNewResponses = false;
		this.availableReadings = User.MAX_SAVEABLE_READINGS;
		this.availableWritings = User.MAX_SAVEABLE_WRITINGS;
		this.readPosts = 0;
		this.writtenPosts = 0;
	}
	
	public User(String email, String password, String username, Gender gender, LocalDate dateOfBirth
			,boolean acceptsResponseEmails, boolean acceptsMainteinanceEmails)
	{
		this.userID = UUID.randomUUID();
		this.email = email;
		this.password = User.createPasswordHash(password);
		this.username = username;
		this.profilePicURL = null;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.role = Role.USER;
		
		this.acceptsResponseEmails = acceptsResponseEmails;
		this.acceptsMainteinanceEmails = acceptsMainteinanceEmails;
		
		this.accountCreationDate = LocalDate.now();
		this.updateModificationDate();
		this.updateLoginDate();
		this.updateLastPostDate();
		this.updateLastReadingDate();
		
		this.hasNewResponses = false;
		this.availableReadings = User.MAX_SAVEABLE_READINGS;
		this.availableWritings = User.MAX_SAVEABLE_WRITINGS;
		this.readPosts = 0;
		this.writtenPosts = 0;
	}
	
	//Getters
	public UUID getID()
	{
		return this.userID;
	}
	
	public String getEmail()
	{
		return this.email;
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
	public String getPasswordHash()
	{
		return this.password;
	}
	
	public String getProfilePicURL()
	{
		return this.profilePicURL;
	}
	
	public Gender getGender()
	{
		return this.gender;
	}
	
	public LocalDate getDateOfBirth()
	{
		return this.dateOfBirth;
	}
	
	public Role getRole()
	{
		return this.role;
	}
	
	public UUID getUserID()
	{
		return this.userID;
	}
	
	public boolean acceptsResponseEmails()
	{
		return this.acceptsResponseEmails;
	}
	
	public boolean acceptsManeinanceEmails()
	{
		return this.acceptsMainteinanceEmails;
	}
	
	public String getPassword()
	{
		return password;
	}

	public LocalDate getAccountCreationDate()
	{
		return accountCreationDate;
	}

	public LocalDate getAccountLastModificationDate()
	{
		return accountLastModificationDate;
	}

	public LocalDate getLastPostDate()
	{
		return lastPostDate;
	}

	public LocalTime getLastPostHour()
	{
		return lastPostHour;
	}

	public LocalDate getLastReadingDate()
	{
		return lastReadingDate;
	}

	public LocalTime getLastReadingHour()
	{
		return lastReadingHour;
	}

	public LocalDate getLastLoginDate()
	{
		return lastLoginDate;
	}

	public LocalTime getLastLoginHour()
	{
		return lastLoginHour;
	}

	public boolean isHasNewResponses()
	{
		return hasNewResponses;
	}

	public int getAvailableWritings()
	{
		return availableWritings;
	}

	public int getAvailableReadings()
	{
		return availableReadings;
	}

	public int getReadPosts()
	{
		return readPosts;
	}

	public int getWrittenPosts()
	{
		return writtenPosts;
	}
	//Setters
	
	
	
	//Metodos
	
	

	public void updateLastPostDate()
	{
		this.lastPostDate = LocalDate.now();
		this.lastPostHour = LocalTime.now();
	}
	
	public void updateLastReadingDate()
	{
		this.lastReadingDate = LocalDate.now();
		this.lastReadingHour = LocalTime.now();
	}
	
	public void updateModificationDate()
	{
		this.accountLastModificationDate = LocalDate.now();
	}
	
	public void updateLoginDate()
	{
		this.lastLoginDate = LocalDate.now();
		this.lastLoginHour = LocalTime.now();
	}
	
	private static String createPasswordHash(String password)
	{
		return Argon2Factory.create().hash(3, 65536, 1, password.toCharArray());
	}
	
	//Impresion por consola
	@Override
	public String toString()
	{
		return String.format("Email: %s, ID: %s Username: %s", this.email, this.userID.toString(),this.username);
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
	
	//Devuelve true si la contraseña introducida por el usuario concuerda con guardada en el objeto
	public boolean correctPassword(String password)
	{
		return Argon2Factory.create().verify(this.password, password.toCharArray());
	}	
}
