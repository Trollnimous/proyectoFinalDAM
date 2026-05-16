package database;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import session.exceptions.PostCreationFailedException;

public interface DAO<T>
{
	boolean insert(T object) throws SQLException, NullPointerException, PostCreationFailedException;
    boolean update(T object);
    boolean delete(UUID object);
    T searchById(UUID object) throws SQLException;
    List<T> listAll();
}
