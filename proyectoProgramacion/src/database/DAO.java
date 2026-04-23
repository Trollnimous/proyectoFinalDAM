package database;

import java.util.List;
import java.util.UUID;

public interface DAO<T>
{
	boolean insert(T object);
    boolean update(T object);
    boolean delete(UUID object);
    T searchById(UUID object);
    List<T> listAll();
}
