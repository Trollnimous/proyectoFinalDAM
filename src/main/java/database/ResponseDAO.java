package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import responses.Response;

import java.sql.Date;

public class ResponseDAO implements DAO<Response>
{
	//Usar SOLO PARA CREAR UN NUEVO POST
	@Override
	public boolean insert(Response response) {
		boolean valid = false;
        String sql = "INSERT INTO responses (response_id, post_id, author_id, response_date, title, content) VALUES (?, ?, ?, ?, ?, ?) ;";
        String sqlUpdate = "update users set has_new_responses = 1 where user_id in "
        		+ "(select user_id from users where user_id in ("
        		+ "select uploader_id from posts where post_id in "
        		+ "(select post_id from responses where post_id = ?)));";
        
        try (Connection conn = DatabaseConnection.getConexion(); 
             PreparedStatement pstmt = conn.prepareStatement(sql);
        	 PreparedStatement pstmt2 = conn.prepareStatement(sqlUpdate)){

            pstmt.setString(1, response.getResponseID().toString());
            pstmt.setString(2, response.getPostID().toString());
            pstmt.setString(3, response.getAuthorID().toString());
            
            pstmt.setDate(4, Date.valueOf(response.getResponseDate()));
            pstmt.setString(5, response.getTitle());
            pstmt.setString(6, response.getContent());
            
            pstmt2.setString(1, response.getPostID().toString());
            
            // 4. EJECUTAR
            pstmt.executeUpdate(); // Este comando envía los datos a MySQL
            pstmt2.executeUpdate();
            System.out.println("✅ Respuesta insertada correctamente en la BD.");
            valid = true;

        } catch (SQLException e) {
            System.out.println("❌ Error al insertar: " + e.getMessage());
        } catch (NullPointerException e)
        {
        	System.out.println("❌ Error al insertar: " + e.getMessage());
        }
        return valid;
    }

	@Override
	public boolean update(Response response)
	{
		//Esto no lo hacemos porque no sabemos hacerlo bien para varias cosas
		return false;
	}

	@Override
	public boolean delete(UUID idToRemove)
	{
		boolean valid = false;
        String sql = "delete from responses where (response_id = ?);";
        try (Connection conn = DatabaseConnection.getConexion(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) 
        {

            pstmt.setString(1, idToRemove.toString());
            
            // 4. EJECUTAR
            pstmt.executeUpdate(); // Este comando envía los datos a MySQL
            System.out.println("✅ Respuesta borrada correctamente de la BD.");
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
	public Response searchById(UUID idToSearch)
	{
		Response responseToReturn = null;
        String sql = "select * from responses where (response_id = ?);";

        try (Connection conn = DatabaseConnection.getConexion(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idToSearch.toString());
            
            try (ResultSet rs = pstmt.executeQuery()) 
            {
                if (rs.next()) 
                {
                	System.out.println("✅ Respuesta encontrada correctamente en la BD.");
                    responseToReturn = Response.buildResponseFromResultSet(rs);
                }            
                else
                {
                	System.out.println("❌ La respuesta no se ha encontrado en la BBDD");
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar: " + e.getMessage());
        } catch (NullPointerException e)
        {
        	System.out.println("❌ Error al insertar: " + e.getMessage());
        }
		return responseToReturn;
		
	}

	@Override
	public List<Response> listAll()
	{
		List<Response> listToReturn = new LinkedList<Response>();
		String sql = "select * from posts;";
		
		try (Connection conn = DatabaseConnection.getConexion(); 
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            try (ResultSet rs = pstmt.executeQuery()) 
	            {
	                while(rs.next()) 
	                {
	                	listToReturn.add(Response.buildResponseFromResultSet(rs));
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
	
	public List<Response> listAllFromUser(UUID authorID)
	{
		List<Response> listToReturn = new LinkedList<Response>();
		String sql = "select * from responses where author_id = ?;";
		
		try (Connection conn = DatabaseConnection.getConexion(); 
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

				pstmt.setString(1, authorID.toString());
	            try (ResultSet rs = pstmt.executeQuery()) 
	            {
	                while(rs.next()) 
	                {
	                	listToReturn.add(Response.buildResponseFromResultSet(rs));
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
	
	public List<Response> listAllFromPost(UUID postID) throws SQLException
	{
		List<Response> listToReturn = new LinkedList<Response>();
		String sql = "select * from responses where post_id = ?;";
		
		try (Connection conn = DatabaseConnection.getConexion(); 
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

				pstmt.setString(1, postID.toString());
	            try (ResultSet rs = pstmt.executeQuery()) 
	            {
	                while(rs.next()) 
	                {
	                	listToReturn.add(Response.buildResponseFromResultSet(rs));
	                }            

	            }
	        }
		
		return listToReturn;
	}
	
	public Map<UUID, Integer> getAllResponseCounts() 
	{
	    Map<UUID, Integer> counts = new HashMap<>();
	    String sql = "SELECT post_id, COUNT(*) as total FROM responses GROUP BY post_id";

	    try (Connection conn = DatabaseConnection.getConexion();
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {

	        while (rs.next()) {
	            UUID id = UUID.fromString(rs.getString("post_id"));
	            int total = rs.getInt("total");
	            counts.put(id, total);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return counts;
	}
}
