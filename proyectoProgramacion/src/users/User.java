package users;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;
import java.time.LocalTime;

import de.mkammerer.argon2.Argon2Factory;
import users.gender.Gender;
import users.roles.Role;

import java.sql.Time;
import java.sql.Date;

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
	private boolean acceptsMaintenanceEmails;
	
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
	public User()
	{
		this.userID = null;
		this.email = null;
		this.password = null;
		this.username = null;
		this.profilePicURL = null;
		this.gender = null;
		this.dateOfBirth = null;
		this.role = null;
		
		this.acceptsResponseEmails = false;
		this.acceptsMaintenanceEmails = false;
		
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
		this.acceptsMaintenanceEmails = false;
		
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
		this.acceptsMaintenanceEmails = acceptsMainteinanceEmails;
		
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
		return this.acceptsMaintenanceEmails;
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
	
	public static User buildUserFromResultSet(ResultSet rs)
	{
		User userToReturn = new User();

        try {
        	// 4. Mapeamos de SQL a Java (nombre de columna en la BD)
            userToReturn.userID = UUID.randomUUID();
            userToReturn.email = rs.getString("email");
            userToReturn.username = rs.getString("username");
            userToReturn.password = rs.getString("password_hash");
            userToReturn.profilePicURL = rs.getString("profile_pic_url");
            userToReturn.gender = Gender.getGender(rs.getString("gender"));
            java.sql.Date dateOfBirth = rs.getDate("date_of_birth");
            userToReturn.dateOfBirth = dateOfBirth.toLocalDate();
            userToReturn.role = Role.getRole(rs.getString("user_role"));

            userToReturn.acceptsResponseEmails = rs.getBoolean("accepts_response_emails");
            userToReturn.acceptsMaintenanceEmails = rs.getBoolean("accepts_maintenance_emails");
            
            java.sql.Date accCreationDate = rs.getDate("account_creation_date");
            userToReturn.dateOfBirth = accCreationDate.toLocalDate();
            java.sql.Date accLastModDate = rs.getDate("account_last_modification_date");
            userToReturn.dateOfBirth = accLastModDate.toLocalDate();
            java.sql.Date lastPostDate = rs.getDate("last_post_date");
            userToReturn.dateOfBirth = lastPostDate.toLocalDate();
            java.sql.Time lastPostHour = rs.getTime("last_post_hour");
            userToReturn.lastPostHour = lastPostHour.toLocalTime();
            java.sql.Date lastReadingDate = rs.getDate("last_reading_date");
            userToReturn.dateOfBirth = lastReadingDate.toLocalDate();
            java.sql.Time lastReadingHour = rs.getTime("last_reading_hour");
            userToReturn.lastPostHour = lastReadingHour.toLocalTime();
            java.sql.Date lastLoginDate = rs.getDate("last_login_date");
            userToReturn.dateOfBirth = lastLoginDate.toLocalDate();
            java.sql.Time lastLoginHour = rs.getTime("last_login_hour");
            userToReturn.lastPostHour = lastLoginHour.toLocalTime();
            
            userToReturn.hasNewResponses = rs.getBoolean("has_new_responses");
            userToReturn.availableWritings = rs.getInt("available_writings");
            userToReturn.availableReadings = rs.getInt("available_readings");
            userToReturn.readPosts = rs.getInt("read_posts");
            userToReturn.writtenPosts = rs.getInt("written_posts");

            
        }catch(SQLException e)
        {
        	System.out.println("Error a la hora de reconstruir el usuario: "+ e.getMessage());
        }
        
        return userToReturn;
	}

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
	
	@Override
	public String toString()
	{
		return String.format("User: %s. Email: %s. Gender: %s. BirthDay: %s. Role: %s", this.username, this.email, this.gender.toString(), this.dateOfBirth.toString(), this.role.toString());
	}

	public String detailedToString()
	{
		return "User [userID=" + userID + ", email=" + email + ", username=" + username + ", password=" + password
				+ ", profilePicURL=" + profilePicURL + ", gender=" + gender + ", dateOfBirth=" + dateOfBirth + ", role="
				+ role + ", acceptsResponseEmails=" + acceptsResponseEmails + ", acceptsMaintenanceEmails="
				+ acceptsMaintenanceEmails + ", accountCreationDate=" + accountCreationDate
				+ ", accountLastModificationDate=" + accountLastModificationDate + ", lastPostDate=" + lastPostDate
				+ ", lastPostHour=" + lastPostHour + ", lastReadingDate=" + lastReadingDate + ", lastReadingHour="
				+ lastReadingHour + ", lastLoginDate=" + lastLoginDate + ", lastLoginHour=" + lastLoginHour
				+ ", hasNewResponses=" + hasNewResponses + ", availableWritings=" + availableWritings
				+ ", availableReadings=" + availableReadings + ", readPosts=" + readPosts + ", writtenPosts="
				+ writtenPosts + "]";
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
