package responses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

public class Response
{
	private UUID responseID;
	private UUID postID;
	private UUID authorID;
	
	private String title;
	private String content;
	private LocalDate responseDate;
	
	private boolean awaitsModeration;
	
	public Response()
	{
		this.responseID = null;
		this.postID = null;
		this.authorID = null;
		
		this.responseDate = null;
		this.title = null;
		this.content = null;
		
		this.awaitsModeration = false;
	}
	
	public Response(UUID postID, UUID userResponseID, String content)
	{
		this.responseID = UUID.randomUUID();
		this.postID = postID;
		this.authorID = userResponseID;
		
		this.responseDate = LocalDate.now();
		this.title = null;
		this.content = content;
		
		this.awaitsModeration = false;
	}
	
	public Response(UUID postID, UUID userResponseID, String content, String title)
	{
		this.responseID = UUID.randomUUID();
		this.postID = postID;
		this.authorID = userResponseID;
		
		this.responseDate = LocalDate.now();
		this.title = title;
		this.content = content;
		
		this.awaitsModeration = false;
	}

	//Getters
	
	public UUID getResponseID()
	{
		return responseID;
	}

	public UUID getPostID()
	{
		return postID;
	}

	public UUID getAuthorID()
	{
		return authorID;
	}

	public String getTitle()
	{
		return title;
	}

	public String getContent()
	{
		return content;
	}

	public LocalDate getResponseDate()
	{
		return responseDate;
	}

	public boolean awaitsModeration()
	{
		return awaitsModeration;
	}
	
	public static Response buildResponseFromResultSet(ResultSet rs)
	{
		Response responseToReturn = new Response();

        try {
        	// 4. Mapeamos de SQL a Java (nombre de columna en la BD)
            responseToReturn.responseID = UUID.fromString(rs.getString("response_id"));
            responseToReturn.postID = UUID.fromString(rs.getString("post_id"));
            responseToReturn.authorID = UUID.fromString(rs.getString("author_id"));
            java.sql.Date responseDate = rs.getDate("response_date");
            responseToReturn.responseDate = responseDate.toLocalDate();
            responseToReturn.title = rs.getString("title");
            responseToReturn.content = rs.getString("content");
            responseToReturn.awaitsModeration = rs.getBoolean("awaits_moderation");

        }catch(SQLException e)
        {
        	System.out.println("Error a la hora de reconstruir la respuesta: "+ e.getMessage());
        }
        
        return responseToReturn;
	}
	
	@Override
	public String toString()
	{
		return String.format("\n--------------------------------\n%s\n--------------------------------\n%s\n--------------------------------\n", this.getTitle(),this.getContent());
	}
}
