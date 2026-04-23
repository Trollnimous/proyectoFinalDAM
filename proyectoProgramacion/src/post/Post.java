package post;

import java.time.LocalDate;
import java.util.UUID;

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
	public UUID getUUID()
	{
		return this.getUUID();
	}
}
