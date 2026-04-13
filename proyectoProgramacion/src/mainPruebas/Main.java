package mainPruebas;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import users.User;

public class Main
{

	public static void main(String[] args) throws IOException
	{
		User usuario = new User("trollnimous2mail.com","Contraseña");
		System.out.println(usuario.correctPassword("Contaseña"));
		System.out.println(usuario.getEmail());
		File file = new File("baseDeDatos/temp.txt");
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write("Hola mundo");
		writer.close();
	}

}
