package responses;

import java.time.LocalDate;
import java.util.UUID;

import post.Post;
import users.User;

public class Response
{
	private UUID responseID;
	private UUID postID;
	private UUID authorID;
	
	private String title;
	private String content;
	private LocalDate responseDate;
	
	private boolean awaitsModeration;
	
	public Response(UUID postID, UUID userResponseID, String content)
	{
		this.responseID = UUID.randomUUID();
		this.postID = postID;
		this.authorID = userResponseID;
		
		this.responseDate = LocalDate.now();
		this.title = null;
		this.content = content;
	}
	
	public Response(Post postID, User userResponseID, String content)
	{
		this.responseID = UUID.randomUUID();
		this.postID = postID.getUUID();
		this.authorID = userResponseID.getUserID();
		
		this.responseDate = LocalDate.now();
		this.title = null;
		this.content = content;
	}
	
	public Response(UUID postID, UUID userResponseID, String content, String title)
	{
		this.responseID = UUID.randomUUID();
		this.postID = postID;
		this.authorID = userResponseID;
		
		this.responseDate = LocalDate.now();
		this.title = title;
		this.content = content;
	}
	
	public Response(Post postID, User userResponseID, String content, String title)
	{
		this.responseID = UUID.randomUUID();
		this.postID = postID.getUUID();
		this.authorID = userResponseID.getUserID();
		
		this.responseDate = LocalDate.now();
		this.title = title;
		this.content = content;
	}
}
