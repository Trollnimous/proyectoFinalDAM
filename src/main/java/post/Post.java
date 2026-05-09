package post;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

import database.UserDAO;
import users.User;

public class Post
{
	private UUID postID;
	private UUID uploaderID;
	
	private String title;
	private String content;
	private int likes;
	private LocalDate publishDate;
	
	private int maxReadings;	
	private String responseEmail;
	private boolean wantsResponse;
	private boolean awaitsModeration;
	
	
	//Constructores
	public Post()
	{
		this.postID = null;
		this.uploaderID = null;
		
		this.title = null;
		this.content = null;
		this.likes = 0;
		this.publishDate = null;
		
		this.maxReadings = 0;
		this.responseEmail = null;
		this.wantsResponse = false;
		this.awaitsModeration = false;
	}
	
	public Post(UUID uploaderID, String title, String content, int maxReadings, String responseEmail,
			boolean wantsResponse)
	{
		this.postID = UUID.randomUUID();
		this.uploaderID = uploaderID;
		
		this.title = title;
		this.content = content;
		this.likes = 0;
		this.publishDate = LocalDate.now();
		
		this.maxReadings = maxReadings;
		this.responseEmail = responseEmail;
		this.wantsResponse = wantsResponse;
		this.awaitsModeration = false;
	}
	
	public Post(User user, String title, String content, int maxReadings, String responseEmail,
			boolean wantsResponse)
	{
		this.postID = UUID.randomUUID();
		this.uploaderID = user.getUserID();
		
		this.title = title;
		this.content = content;
		this.likes = 0;
		this.publishDate = LocalDate.now();
		
		this.maxReadings = maxReadings;
		this.responseEmail = responseEmail;
		this.wantsResponse = wantsResponse;
		this.awaitsModeration = false;
	}
	
	public Post(UUID uploaderID, String title, String content, int maxReadings)
	{
		this.postID = UUID.randomUUID();
		this.uploaderID = uploaderID;
		
		this.title = title;
		this.content = content;
		this.likes = 0;
		this.publishDate = LocalDate.now();
		
		this.maxReadings = maxReadings;
		this.responseEmail = null;
		this.wantsResponse = false;
		this.awaitsModeration = false;
	}
	
	public Post(User user, String title, String content, int maxReadings)
	{
		this.postID = UUID.randomUUID();
		this.uploaderID = user.getUserID();
		
		this.title = title;
		this.content = content;
		this.likes = 0;
		this.publishDate = LocalDate.now();
		
		this.maxReadings = maxReadings;
		this.responseEmail = null;
		this.wantsResponse = false;
		this.awaitsModeration = false;
	}
	
	
	
	//Getters
	public UUID getPostID()
	{
		return this.postID;
	}

	public UUID getUploaderID()
	{
		return uploaderID;
	}

	public String getTitle()
	{
		return title;
	}

	public String getContent()
	{
		return content;
	}

	public int getLikes()
	{
		return likes;
	}

	public LocalDate getPublishDate()
	{
		return publishDate;
	}

	public int getMaxReadings()
	{
		return maxReadings;
	}

	public String getResponseEmail()
	{
		return responseEmail;
	}

	public boolean wantsResponse()
	{
		return wantsResponse;
	}

	public boolean awaitsModeration()
	{
		return awaitsModeration;
	}
	
	public User getUser(UserDAO uDAO)
	{
		return uDAO.searchById(this.uploaderID);
	}
	
	//Métodos
	public static Post buildPostFromResultSet(ResultSet rs)
	{
		Post postToReturn = new Post();

        try {
        	// 4. Mapeamos de SQL a Java (nombre de columna en la BD)
            postToReturn.postID = UUID.fromString(rs.getString("post_id"));
            postToReturn.uploaderID = UUID.fromString(rs.getString("uploader_id"));
            postToReturn.title = rs.getString("title");
            postToReturn.content = rs.getString("content");
            postToReturn.likes = rs.getInt("likes");
            java.sql.Date publishDate = rs.getDate("publish_date");
            postToReturn.publishDate = publishDate.toLocalDate();
            postToReturn.maxReadings = rs.getInt("max_readings");
            postToReturn.responseEmail = rs.getString("response_email");
            postToReturn.wantsResponse = rs.getBoolean("wants_response");
            postToReturn.awaitsModeration = rs.getBoolean("awaits_moderation");

        }catch(SQLException e)
        {
        	System.out.println("Error a la hora de reconstruir el post: "+ e.getMessage());
        }
        
        return postToReturn;
	}
	
	@Override
	public String toString()
	{
		return String.format("\n--------------------------------\n%s\n--------------------------------\n%s\n--------------------------------\n", this.title,this.content);
	}
}
