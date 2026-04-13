package mainPruebas;

import users.User;

public class Main
{

	public static void main(String[] args)
	{
		User usuario = new User("trollnimous2mail.com","Contraseña");
		System.out.println(usuario.correctPassword("Contaseña"));
		System.out.println(usuario.getEmail());
	}

}
