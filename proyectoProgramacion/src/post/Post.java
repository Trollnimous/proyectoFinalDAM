package post;

import java.time.LocalDate;
import java.util.UUID;

public class Post
{
	private UUID postID;
	private UUID uploaderID;
	private String title;
	private String content;
	private int likes;
	private int maxReadings;
	private LocalDate publishDate;
	private String responseEmail;
	private boolean wantsResponse;
	private boolean awaitsTrial;
	
	
}
