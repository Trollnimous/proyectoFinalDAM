package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import database.exceptions.EmptyPostPoolException;
import post.Post;

public class PostPoolDAO implements DAO<Post>
{
	//Usar SOLO PARA CREAR UN NUEVO POST
	@Override
	public boolean insert(Post post) {
		boolean valid = false;
        String sql = "INSERT INTO posts_pool (post_id, readings_left, wants_response) VALUES (?, ?, ?) ;";

        try (Connection conn = DatabaseConnection.getConexion(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, post.getPostID().toString());
            pstmt.setInt(2, post.getMaxReadings());
            pstmt.setBoolean(3, post.wantsResponse());
            
            // 4. EJECUTAR
            pstmt.executeUpdate(); // Este comando envía los datos a MySQL
            System.out.println("✅ Post insertado correctamente en el pool.");
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
	public boolean update(Post post)
	{
		return false;
	}
	
	//Resta uno al contador de lecturas de ese post, los borrados son manejados por el SGBD
	public boolean update(UUID idToUpdate, int previousReadingsLeft)
	{
		if(previousReadingsLeft <= 1)
		{
			return this.delete(idToUpdate);
		}
		
		String sql = "UPDATE posts_pool SET readings_left = ? where post_id = ?";

	    try (Connection conn = DatabaseConnection.getConexion();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) 
	    {
	        
	    	
	    	pstmt.setInt(1, previousReadingsLeft-1);
	    	pstmt.setString(2, idToUpdate.toString());
	    	
	        int affectedRows = pstmt.executeUpdate();
	        
	        return affectedRows > 0;
	    } catch (SQLException e) {
	        System.out.println("❌ Error al actualizar usuarios: " + e.getMessage());
	    }
	    return false;
	}

	@Override
	public boolean delete(UUID idToRemove)
	{
		boolean valid = false;
        String sql = "delete from posts_pool where (post_id = ?);";
        try (Connection conn = DatabaseConnection.getConexion(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) 
        {

            pstmt.setString(1, idToRemove.toString());
            
            // 4. EJECUTAR
            pstmt.executeUpdate(); // Este comando envía los datos a MySQL
            System.out.println("✅ Post borrado correctamente de la pool.");
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
	public Post searchById(UUID idToSearch)
	{
		Post postToReturn = null;
        String sql = "select * from posts_pool where (post_id = ?);";

        try (Connection conn = DatabaseConnection.getConexion(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idToSearch.toString());
            
            try (ResultSet rs = pstmt.executeQuery()) 
            {
                if (rs.next()) 
                {
                	System.out.println("✅ Post encontrado correctamente en la pool.");
                    postToReturn = Post.buildPostFromResultSet(rs);
                }            
                else
                {
                	System.out.println("❌ El post no se ha encontrado en la pool");
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al buscar: " + e.getMessage());
        } catch (NullPointerException e)
        {
        	System.out.println("❌ Error al buscar: " + e.getMessage());
        }
		return postToReturn;
		
	}

	@Override
	public List<Post> listAll()
	{
		return null;
	}
	
	public List<Post> listAll(PostDAO pDAO)
	{
		List<Post> listToReturn = new LinkedList<Post>();
		String sql = "select * from posts_pool;";
		
		try (Connection conn = DatabaseConnection.getConexion(); 
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            try (ResultSet rs = pstmt.executeQuery()) 
	            {
	                while(rs.next()) 
	                {
	                	listToReturn.add(pDAO.searchById(UUID.fromString(rs.getString("post_id"))));
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
	
	public List<Post> listAll(UUID postID)
	{
		PostDAO pDAO = new PostDAO();
		List<Post> listToReturn = new LinkedList<Post>();
		String sql = "select * from posts_pool where post_id = ?;";
		
		try (Connection conn = DatabaseConnection.getConexion(); 
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

				pstmt.setString(1, postID.toString());
	            try (ResultSet rs = pstmt.executeQuery()) 
	            {
	                while(rs.next()) 
	                {
	                	listToReturn.add(pDAO.searchById(postID));
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
	
	public UUID getPostToRead() throws EmptyPostPoolException
	{
        String sql = "select * from posts_pool WHERE wants_response = 0 ORDER BY RAND() LIMIT 1;";

        try (Connection conn = DatabaseConnection.getConexion(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) 
        {           
            try (ResultSet rs = pstmt.executeQuery()) 
            {
                if (rs.next()) 
                {
                	this.update(UUID.fromString(rs.getString("post_id")), rs.getInt("readings_left"));
                	return UUID.fromString(rs.getString("post_id"));
                }            
                else
                {
                	throw new EmptyPostPoolException();
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error obtener un mensaje que leer: " + e.getMessage());
        } catch (NullPointerException e)
        {
        	System.out.println("❌ Error obtener un mensaje que leer: " + e.getMessage());
        }
        return null;
	}
	
	public UUID getPostToReadAndRespond() throws EmptyPostPoolException
	{
        String sql = "select * from posts_pool WHERE wants_response = 1 ORDER BY RAND() LIMIT 1;";

        try (Connection conn = DatabaseConnection.getConexion(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) 
        {           
            try (ResultSet rs = pstmt.executeQuery()) 
            {
                if (rs.next()) 
                {
                	this.update(UUID.fromString(rs.getString("post_id")), rs.getInt("readings_left"));
                	return UUID.fromString(rs.getString("post_id"));
                }            
                else
                {
                	throw new EmptyPostPoolException();
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error obtener un mensaje que leer: " + e.getMessage());
        } catch (NullPointerException e)
        {
        	System.out.println("❌ Error obtener un mensaje que leer: " + e.getMessage());
        }
        return null;
	}
}
