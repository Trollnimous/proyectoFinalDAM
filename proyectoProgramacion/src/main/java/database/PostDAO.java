package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import post.Post;
import java.sql.Date;

public class PostDAO implements DAO<Post>
{
	//Usar SOLO PARA CREAR UN NUEVO POST
	@Override
	public boolean insert(Post post) {
		boolean valid = false;
        String sql = "INSERT INTO posts (post_id, uploader_id, title, content, likes, publish_date, max_readings, response_email, wants_response, awaits_moderation) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ;";

        try (Connection conn = DatabaseConnection.getConexion(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, post.getPostID().toString());
            pstmt.setString(2, post.getUploaderID().toString());
            pstmt.setString(3, post.getTitle());
            pstmt.setString(4, post.getContent());
            pstmt.setInt(5, post.getLikes());
            
            pstmt.setDate(6, Date.valueOf(post.getPublishDate()));
            pstmt.setInt(7, post.getMaxReadings());
            
            pstmt.setString(8, post.getResponseEmail());
            pstmt.setBoolean(9, post.wantsResponse());
            pstmt.setBoolean(10, post.awaitsModeration());
            
            // 4. EJECUTAR
            pstmt.executeUpdate(); // Este comando envía los datos a MySQL
            System.out.println("✅ Post insertado correctamente en la BD.");
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
		//Esto no lo hacemos porque no sabemos hacerlo bien para varias cosas
		return false;
	}

	@Override
	public boolean delete(UUID idToRemove)
	{
		boolean valid = false;
        String sql = "delete from posts where (post_id = ?);";
        try (Connection conn = DatabaseConnection.getConexion(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) 
        {

            pstmt.setString(1, idToRemove.toString());
            
            // 4. EJECUTAR
            pstmt.executeUpdate(); // Este comando envía los datos a MySQL
            System.out.println("✅ Post borrado correctamente de la BD.");
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
        String sql = "select * from posts where (post_id = ?);";

        try (Connection conn = DatabaseConnection.getConexion(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, idToSearch.toString());
            
            try (ResultSet rs = pstmt.executeQuery()) 
            {
                if (rs.next()) 
                {
                	System.out.println("✅ Post encontrado correctamente en la BD.");
                    postToReturn = Post.buildPostFromResultSet(rs);
                }            
                else
                {
                	System.out.println("❌ El post no se ha encontrado en la BBDD");
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar: " + e.getMessage());
        } catch (NullPointerException e)
        {
        	System.out.println("❌ Error al insertar: " + e.getMessage());
        }
		return postToReturn;
		
	}

	@Override
	public List<Post> listAll()
	{
		List<Post> listToReturn = new LinkedList<Post>();
		String sql = "select * from posts;";
		
		try (Connection conn = DatabaseConnection.getConexion(); 
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

	            try (ResultSet rs = pstmt.executeQuery()) 
	            {
	                while(rs.next()) 
	                {
	                	listToReturn.add(Post.buildPostFromResultSet(rs));
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
	
	public List<Post> listAll(UUID uploaderID)
	{
		List<Post> listToReturn = new LinkedList<Post>();
		String sql = "select * from posts where uploader_id = ?;";
		
		try (Connection conn = DatabaseConnection.getConexion(); 
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {

				pstmt.setString(1, uploaderID.toString());
	            try (ResultSet rs = pstmt.executeQuery()) 
	            {
	                while(rs.next()) 
	                {
	                	listToReturn.add(Post.buildPostFromResultSet(rs));
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
	

}
