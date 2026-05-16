package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import database.exceptions.RegistryNotFoundException;
import users.User;

import java.sql.Date;

public class UserDAO implements DAO<User>
{
	//Usar SOLO PARA CREAR UN NUEVO USUARIO
	@Override
	public boolean insert(User user) throws SQLException, NullPointerException 
	{
		boolean valid = false;
        String sql = "INSERT INTO users (user_id, email, username, password_hash, gender, date_of_birth, user_role, accepts_response_emails, accepts_manteinance_emails, account_creation_date, account_last_modification_date, last_post_date, last_post_hour, last_reading_date, last_reading_hour, last_login_date, last_login_hour, available_writings, available_readings ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ;";

        try (Connection conn = DatabaseConnection.getConexion(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUserID().toString());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getUsername());
            pstmt.setString(4, user.getPasswordHash());
            pstmt.setString(5, String.valueOf(user.getGender().name().toUpperCase().charAt(0)));
            pstmt.setDate(6, Date.valueOf(user.getDateOfBirth()));
            pstmt.setString(7, user.getRole().name().toUpperCase());
            
            pstmt.setBoolean(8, user.acceptsResponseEmails());
            pstmt.setBoolean(9, user.acceptsManeinanceEmails());
            
            pstmt.setDate(10, Date.valueOf(user.getAccountCreationDate()));
            pstmt.setDate(11, Date.valueOf(user.getAccountLastModificationDate()));
            pstmt.setDate(12, Date.valueOf(user.getLastPostDate()));
            pstmt.setTime(13, Time.valueOf(user.getLastPostHour()));
            pstmt.setDate(14, Date.valueOf(user.getLastReadingDate()));
            pstmt.setTime(15, Time.valueOf(user.getLastReadingHour()));
            pstmt.setDate(16, Date.valueOf(user.getLastLoginDate()));
            pstmt.setTime(17, Time.valueOf(user.getLastLoginHour()));
            
            pstmt.setInt(18, user.getAvailableWritings());
            pstmt.setInt(19, user.getAvailableReadings());
            
            // 4. EJECUTAR
            pstmt.executeUpdate(); // Este comando envía los datos a MySQL
            System.out.println("✅ Usuario insertado correctamente en la BD.");
            valid = true;

        }
        return valid;
    }

	@Override
	public boolean update(User object)
	{
		//Esto no lo hacemos porque no sabemos hacerlo bien para varias cosas
		return false;
	}

	@Override
	public boolean delete(UUID idToRemove)
	{
		boolean valid = false;
        String sql = "delete from users where (user_id = ?);";
        try (Connection conn = DatabaseConnection.getConexion(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) 
        {

            pstmt.setString(1, idToRemove.toString());
            
            // 4. EJECUTAR
            pstmt.executeUpdate(); // Este comando envía los datos a MySQL
            System.out.println("✅ Usuario borrado correctamente de la BD.");
            valid = true;

        } catch (SQLException e) {
            System.out.println("❌ Error al borrar: " + e.getMessage());
        } catch (NullPointerException e)
        {
        	System.out.println("❌ Error al borrar: " + e.getMessage());
        }
        return valid;
	}

	@Override
	public User searchById(UUID idToSearch)
	{
		User userToReturn = null;
        String sql = "select * from users where (user_id = ?);";

        try (Connection conn = DatabaseConnection.getConexion(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idToSearch.toString());
            
            try (ResultSet rs = pstmt.executeQuery()) 
            {
                if (rs.next()) 
                {
                	System.out.println("✅ Usuario encontrado correctamente en la BD.");
                    userToReturn = User.buildUserFromResultSet(rs);
                }            
                else
                {
                	System.out.println("❌ El usuario no se ha encontrado en la BBDD");
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar: " + e.getMessage());
        } catch (NullPointerException e)
        {
        	System.out.println("❌ Error al insertar: " + e.getMessage());
        }
		return userToReturn;
		
	}
	
	public boolean userHasNewResponses(UUID idToSearch)
	{
		boolean hasNewResponses = false;
		String sql = "select has_new_responses from users where (user_id = ?);";

        try (Connection conn = DatabaseConnection.getConexion(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idToSearch.toString());
            
            try (ResultSet rs = pstmt.executeQuery()) 
            {
                if (rs.next()) 
                {
                	hasNewResponses = rs.getBoolean("has_new_responses");
                }            
                else
                {
                	System.out.println("❌ El usuario no se ha encontrado en la BBDD");
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar: " + e.getMessage());
        } catch (NullPointerException e)
        {
        	System.out.println("❌ Error al insertar: " + e.getMessage());
        }
        catch(Exception e)
        {
        	System.out.println("Ha petado");
        }
		return hasNewResponses;
		
	}

	@Override
	public List<User> listAll()
	{
		List<User> listToReturn = new LinkedList<User>();
		String sql = "select * from users;";
		
		try (Connection conn = DatabaseConnection.getConexion(); 
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            try (ResultSet rs = pstmt.executeQuery()) 
	            {
	                while(rs.next()) 
	                {
	                	listToReturn.add(User.buildUserFromResultSet(rs));
	                }            

	            }
	        } catch (SQLException e) {
	            System.out.println("❌ Error al obtener lista: " + e.getMessage());
	        } catch (NullPointerException e)
	        {
	        	System.out.println("❌ Error al obtener lista: " + e.getMessage());
	        }
		
		return listToReturn;
	}
	
	//Devuelve el usuario
	public User searchByEmail(String emailToSearch) throws RegistryNotFoundException
	{
		User userToReturn = null;
        String sql = "select * from users where (email = ?);";

        try (Connection conn = DatabaseConnection.getConexion(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, emailToSearch);
            
            try (ResultSet rs = pstmt.executeQuery()) 
            {
                if (rs.next()) 
                {
                	//System.out.println("✅ Usuario encontrado correctamente en la BD.");
                    userToReturn = User.buildUserFromResultSet(rs);
                }            
                else
                {
                	System.out.println("❌ El usuario no se ha encontrado en la BBDD");
                	throw new RegistryNotFoundException("Usuario", "email");
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar: " + e.getMessage());
        } catch (NullPointerException e)
        {
        	System.out.println("❌ Error al insertar: " + e.getMessage());
        }
		return userToReturn;
		
	}
	
	public User searchByUsername(String usernameToSearch)
	{
		User userToReturn = null;
        String sql = "select * from users where (username = ?);";

        try (Connection conn = DatabaseConnection.getConexion(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usernameToSearch);
            
            try (ResultSet rs = pstmt.executeQuery()) 
            {
                if (rs.next()) 
                {
                	//System.out.println("✅ Usuario encontrado correctamente en la BD.");
                    userToReturn = User.buildUserFromResultSet(rs);
                }            
                else
                {
                	System.out.println("❌ El usuario no se ha encontrado en la BBDD");
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al encontrar: " + e.getMessage());
        } catch (NullPointerException e)
        {
        	System.out.println("❌ Error al encontrar: " + e.getMessage());
        }
		return userToReturn;
		
	}
	
	public boolean existsEmail(String email) {
	    // Usamos SELECT 1 y EXISTS para que sea lo más rápido posible
	    String sql = "SELECT EXISTS(SELECT 1 FROM users WHERE email = ?) AS is_present";

	    try (Connection conn = DatabaseConnection.getConexion();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) 
	    {
	        
	        pstmt.setString(1, email);
	        
	        try (ResultSet rs = pstmt.executeQuery()) 
	        {
	            if (rs.next()) 
	            {
	                return rs.getBoolean("is_present");
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("❌ Error al comprobar existencia: " + e.getMessage());
	    }
	    return false;
	}
	
	public boolean existsUsername(String username) {
	    String sql = "SELECT EXISTS(SELECT 1 FROM users WHERE username = ?) AS is_present";

	    try (Connection conn = DatabaseConnection.getConexion();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) 
	    {
	        
	        pstmt.setString(1, username);
	        
	        try (ResultSet rs = pstmt.executeQuery()) 
	        {
	            if (rs.next()) 
	            {

	                return rs.getBoolean("is_present");
	            }
	        }
	    } catch (SQLException e) {
	        System.out.println("❌ Error al comprobar existencia: " + e.getMessage());
	    }
	    return false;
	}
	
	public boolean updateLastLoginDate(UUID userToUpdateID)
	{
	    String sql = "UPDATE users SET last_login_date = ?, last_login_hour = ? where user_id = ?";

	    try (Connection conn = DatabaseConnection.getConexion();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) 
	    {
	        
	        pstmt.setDate(1, Date.valueOf(LocalDate.now()));
	        pstmt.setTime(2, Time.valueOf(LocalTime.now()));
	        pstmt.setString(3, userToUpdateID.toString());
	        
	        int affectedRows = pstmt.executeUpdate();
	        
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        System.out.println("❌ Error al actualizar usuarios: " + e.getMessage());
	    }
	    return false;
	}
	
	
	public boolean updatePassword(UUID userToUpdateID, String passwordHashUpdated) {
	    String sql = "UPDATE users SET password_hash = ? where user_id = ?";

	    try (Connection conn = DatabaseConnection.getConexion();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) 
	    {
	        
	        pstmt.setString(1, passwordHashUpdated);
	        pstmt.setString(2, userToUpdateID.toString());
	        
	        int affectedRows = pstmt.executeUpdate();
	        
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        System.out.println("❌ Error al actualizar contraseña: " + e.getMessage());
	    }
	    return false;
	}
}
