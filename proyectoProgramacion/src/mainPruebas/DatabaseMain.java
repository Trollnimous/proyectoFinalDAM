package mainPruebas;

import java.sql.SQLException;
import java.time.LocalDate;

import java.sql.Connection;
import database.DatabaseConnection;
import temporalConsoleDatabase.TemporalDatabase;
import users.User;
import users.gender.Gender;

public class DatabaseMain
{
	public static void main (String[] args)
	{
		Connection miConex = DatabaseConnection.getConexion();

        if (miConex != null) {
            // Aquí irían tus consultas SQL
            System.out.println("Ya puedes operar en la base de datos.");

            // Al terminar, cerramos la conexión
            try {
                miConex.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
}
