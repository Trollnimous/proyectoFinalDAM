package session;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import database.PostDAO;
import database.PostPoolDAO;
import database.ResponseDAO;
import database.UserDAO;
import database.exceptions.EmptyPostPoolException;
import inputs.InputUtils;
import post.Post;
import responses.Response;
import temporalConsoleMenus.SessionMenu;
import users.User;
import users.UserUtils;
import users.gender.Gender;
import users.roles.Role;

public class Session
{
	private final Scanner sc = new Scanner(System.in);
	private User sessionUser;
	private UserDAO uDAO;
	private PostDAO pDAO;
	private PostPoolDAO ppDAO;
	private ResponseDAO rDAO;
	
	public Session()
	{
		this.rDAO = new ResponseDAO();
		this.ppDAO = new PostPoolDAO();
		this.pDAO = new PostDAO();
		this.uDAO = new UserDAO();
		this.sessionUser = null;
	}
	
	public void startSession()
	{
		int sessionStatus = 1;
		while(sessionStatus > 0)
		{
			sessionStatus = SessionMenu.printMenu(sessionUser);
			switch(sessionStatus)
			{
				//Session has no user attached
				case 1:
					sessionStatus = this.attachUser();
					break;
				//User menu
				case 2:
					sessionStatus = userSession();
					break;
				//Admin menu
				case 3:
					sessionStatus = adminSession();
					break;
				//Exit
				case 0:
					break;
				default:
					System.out.println("[W]: Choice not recognized");
					break;
			}
		}
		SessionMenu.sayGoodbye();
	}
	
	private int userSession()
	{
		SessionMenu.printUserName(this.sessionUser.getUsername());
		SessionMenu.printUserMenu();
		int choice = -1;
		do {
			SessionMenu.askForChoice();
			choice = InputUtils.askForNumber(sc);
			sc.nextLine();
			if(!InputUtils.validChoice(choice, 1, 7, true))
			{
				System.out.println("[W]: Choice not recognised");
			}
		}while(!InputUtils.validChoice(choice, 1, 7, true));
		switch(choice)
		{
			//TODO: hacer casos
			case 1:
				this.createPost();
				return 1;
			case 2:
				this.readPost();
				return 1;
			case 3:
				this.readPostAndRespond();
				return 1;
			case 4:
				this.seeUserPosts();
				return 1;
			case 5:
				this.seeResponsesToUsersPosts();
				return 1;
			case 6:
				this.changeUserPassword();
				return 1;
			case 7:
				if(this.deleteUser(this.sessionUser))
				{
					return 0;
				}
				return 1;
			case 0:
				return 0;
		}
		return -1;
	}
	
	private int readPostAndRespond()
	{
		try
		{
			UUID idToRead = this.ppDAO.getPostToReadAndRespond();
			System.out.println(this.pDAO.searchById(idToRead));
			System.out.println();
			System.out.println("Write the title of the response:");
			String title = this.sc.nextLine();
			System.out.println("\nWrite the content of the response:");
			String content = this.sc.nextLine();
			this.rDAO.insert(new Response(idToRead,this.sessionUser.getID(),content, title));
		} catch (EmptyPostPoolException e)
		{
			System.out.println("\n[E]: The post pool is empty\n");
		}
		
		return 1;
	}
	
	private int readPost()
	{
		try
		{
			System.out.println(this.pDAO.searchById(this.ppDAO.getPostToRead()));
			System.out.println();
		} catch (EmptyPostPoolException e)
		{
			System.out.println("\n[E]: The post pool is empty\n");
		}
		
		return 1;
	}
	
	private int adminSession()
	{
		SessionMenu.printUserName(this.sessionUser.getUsername());
		SessionMenu.printAdminMenu();
		int choice = -1;
		do {
			SessionMenu.askForChoice();
			choice = InputUtils.askForNumber(sc);
			sc.nextLine();
			if(!InputUtils.validChoice(choice, 1, 2, true))
			{
				System.out.println("[W]: Choice not recognised");
			}
		}while(!InputUtils.validChoice(choice, 1, 2, true));
		switch(choice)
		{
			case 1:
				this.changeUserPassword();
				return 1;
			case 2:
				this.deleteUser(this.sessionUser);
				return 1;
			case 0:
				return 0;
		}
		return -1;
	}
	
	private boolean createPost()
	{
		String title = "";
		String content = "";
		int maxReadings = -1;
		int wantsResponseChoice = -1;
		boolean wantsResponse;
		System.out.println("Introduce the post title:");
		SessionMenu.askForChoice();
		title = sc.nextLine();
		System.out.println("Introduce the post content:");
		SessionMenu.askForChoice();
		content = sc.nextLine();
		do {
			System.out.print("Introduce the max readings of the post: ");
			SessionMenu.askForChoice();
			maxReadings = InputUtils.askForNumber(sc);
			sc.nextLine();
			if(!InputUtils.validChoice(maxReadings, 1, 20, false))
			{
				System.out.println("[E]: Enter a valid value");
			}
		}while(!InputUtils.validChoice(maxReadings, 1, 20, false));
		System.out.println("Do you want a response to your post?");
		System.out.println("[1] Yes");
		System.out.println("[0] No\n");
		SessionMenu.askForChoice();
		wantsResponseChoice = InputUtils.askForNumber(sc);
		wantsResponse = wantsResponseChoice == 1 ? true : false;
		Post postToInsert = new Post(this.sessionUser.getUserID(),title, content, maxReadings, this.sessionUser.getEmail(),wantsResponse);
		return (this.pDAO.insert(postToInsert) && this.ppDAO.insert(postToInsert));
	}
	
	private void seeUserPosts()
	{
		int choice = -1;
		List<Post> usersPosts = this.pDAO.listAll(this.sessionUser.getID());
		for(int i = 0; i < usersPosts.size(); i++)
		{
			System.out.println(String.format("[%d] %s", i+1,usersPosts.get(i).getTitle()));
		}
		System.out.println("[0] Return\n");
		SessionMenu.askForChoice();
		choice = InputUtils.askForNumber(sc);
		sc.nextLine();
		if(choice == 0)
		{
			return;
		}
		if(InputUtils.validChoice(choice, 0, usersPosts.size(), false))
		{
			System.out.println(usersPosts.get(choice-1));
			System.out.println();
		}
		return;
	}
	
	private void seeResponsesToUsersPosts()
	{
		int choice = -1;
		List<Post> usersPosts = this.pDAO.listAll(this.sessionUser.getID());
		for(int i = 0; i < usersPosts.size(); i++)
		{
			System.out.println(String.format("[%d] %s", i+1,usersPosts.get(i).getTitle()));
		}
		System.out.println("[0] Return\n");
		SessionMenu.askForChoice();
		choice = InputUtils.askForNumber(sc);
		sc.nextLine();
		if(choice == 0 )
		{
			return;
		}
		System.out.println(usersPosts.get(choice-1));
		System.out.println("\nThese are the responses:\n");
		List<Response> postResponses = this.rDAO.listAllFromPost(usersPosts.get(choice-1).getPostID());
		if(postResponses.size() < 1)
		{
			System.out.println("There are no responses to this post\n");
			return;
		}
		for(int i = 0; i < postResponses.size(); i++)
		{
			System.out.println(String.format("[%d] %s", i+1,postResponses.get(i).getTitle()));
		}
		System.out.println("[0] Return\n");
		SessionMenu.askForChoice();
		choice = InputUtils.askForNumber(sc);
		sc.nextLine();
		if(choice == 0 || !InputUtils.validChoice(choice, 0, postResponses.size(), false))
		{
			return;
		}
		System.out.println(postResponses.get(choice-1));
		System.out.println();
	}
	
	private boolean deleteUser(User sessionUser)
	{
		
		if(sessionUser.getRole() == Role.ADMIN)
		{
			String userToDelete = "";
			System.out.print("Write the username of the user to delete: ");
			userToDelete = sc.nextLine();
			if(this.uDAO.existsUsername(userToDelete))
			{
				return this.uDAO.delete(this.uDAO.searchByUsername(userToDelete).getID());
			}
			System.out.println("[E]: Username not found");
			return false;
		}
		System.out.println("Are you sure you want to delete your user?");
		System.out.println("[1] Yes");
		System.out.println("[0] No");
		SessionMenu.askForChoice();
		int choice = InputUtils.askForNumber(sc);
		sc.nextLine();
		if(choice == 1)
		{
			return this.uDAO.delete(this.sessionUser.getID());
		}
		return false;		
	}
	
	private void changeUserPassword()
	{
		String currentPassword;
		do {
			System.out.print("Enter current password: ");
			currentPassword = sc.nextLine();
			if(!this.sessionUser.correctPassword(currentPassword))
			{
				System.out.println("[E]: Incorrect password");
			}
		}while(!this.sessionUser.correctPassword(currentPassword));
		currentPassword = null;
		String newPassword;
		System.out.print("Enter new password: ");
		do {
			newPassword = sc.nextLine();
		}while(!UserUtils.validPassword(newPassword));
		this.sessionUser.setPassword(newPassword);
		this.uDAO.updatePassword(this.sessionUser.getID(), User.createPasswordHash(newPassword));
		newPassword = null;
		System.out.println("\nPassword changed succesfully!\n");
	}
	
	private int attachUser()
	{
		int choice = -1;
		SessionMenu.logUserMenu();
		do {
			SessionMenu.askForChoice();
			choice = InputUtils.askForNumber(sc);
			sc.nextLine();
			if(!(InputUtils.validChoice(choice, 1, 2, true)))
			{
				SessionMenu.choiceInvalidMessage();
			}
		}while(!InputUtils.validChoice(choice, 1, 2, true));
		switch (choice)
		{
			case 1:
				this.userLogin();
				break;
			case 2:
				this.signUpUser();
				break;
			case 0:
				this.endSession();
				return 0;
			default:
				System.err.println("[E]: Session.attachUser() - Choice not recognized");
				break;
				
		}
		return 1;
	}
	
	//Ends current session safely
	public void endSession()
	{
		this.closeScanner();
	}
	
	//Makes user sign up
	private void signUpUser()
	{
		String email = "";
		String username = "";
		String password = "";
		Gender gender = null;
		System.out.println("\nUser signup\n------------\n");
		boolean validEmail = false;
		do {
			System.out.print("Enter your email: ");
			email = sc.nextLine();
			if(UserUtils.validEmail(email)&&!this.uDAO.existsEmail(email))
			{
				validEmail = true;
			}
			else
			{
				System.out.println("\n[E]: Invalid email\n");
			}
		}while(!validEmail);
		boolean validUsername = false;
		do {
			System.out.print("Enter your username: ");
			username = sc.nextLine();
			if(UserUtils.validUsername(username)&&!this.uDAO.existsUsername(username))
			{
				validUsername = true;
			}
			else
			{
				System.out.println("\n[E]: Invalid username\n");
			}
		}while(!validUsername);
		boolean validPassword = false;
		do {
			System.out.print("Enter your password:\n(1 Special character, 1 Uppercase, 1 Lowercase, 1 Number): ");
			password = sc.nextLine();
			if(UserUtils.validPassword(password))
			{
				validPassword = true;
			}
			else
			{
				System.out.println("\n[E]: Invalid password\n");
			}
		}while(!validPassword);
		System.out.println("Select your gender: \n");
		System.out.println("[1] Male");
		System.out.println("[2] Female");
		System.out.println("[3] Other");
		int choice = -1;
		do {
			SessionMenu.askForChoice();
			choice = InputUtils.askForNumber(sc);
			sc.nextLine();
			switch(choice)
			{
				case 1:
					gender = Gender.MALE;
					break;
				case 2:
					gender = Gender.FEMALE;
					break;
				case 3:
					gender = Gender.OTHER;
					break;
				default:
					System.out.println("[W] Choice not recognised");
					break;
			}
		}while(!InputUtils.validChoice(choice, 1, 3, false));
		//TODO: meter para introducir día de nacimiento y consetiemiento de emails
		User userToAttach = new User(email, password, username, gender, LocalDate.of(2026, 4, 11));
		this.uDAO.insert(userToAttach);
		this.sessionUser = userToAttach;
	}
	
	//Logs in user in session
	private void userLogin()
	{
		User userToLogin = null;
		String email;
		String password;
		boolean loginCompleted = false;
		do {
			SessionMenu.askForEmail();
			email = sc.nextLine();
			SessionMenu.askForPassword();
			password = sc.nextLine();
			userToLogin = this.uDAO.searchByEmail(email);
			if(this.uDAO.existsEmail(email) && userToLogin.correctPassword(password))
			{
				this.sessionUser = userToLogin;
				this.uDAO.updateLastLoginDate(this.sessionUser.getID());
				this.updateLastLoginFromUser();
				loginCompleted = true;
				SessionMenu.loginCompleted();
			}
			else
			{
				SessionMenu.loginFailed();
			}
		}while(!loginCompleted);
	}
	
	public void updateLastLoginFromUser()
	{
		if(this.sessionUser == null)
		{
			return;
		}
		this.sessionUser.updateLoginDate();
	}
	
	public void closeScanner()
	{
		this.sc.close();
	}
}
