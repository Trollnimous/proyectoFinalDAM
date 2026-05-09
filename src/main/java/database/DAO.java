package database;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface DAO<T>
{
	boolean insert(T object) throws SQLException, NullPointerException;
    boolean update(T object);
    boolean delete(UUID object);
    T searchById(UUID object);
    List<T> listAll();
}
