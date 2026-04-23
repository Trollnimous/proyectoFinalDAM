package database;

import java.util.List;
import java.util.UUID;

public interface DAC<T>
{
	void insert(T object);
    void uptadte(T object);
    void delete(UUID object);
    T searchById(UUID object);
    List<T> listAll();
}
