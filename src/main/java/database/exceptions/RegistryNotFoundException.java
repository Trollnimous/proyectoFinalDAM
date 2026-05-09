package database.exceptions;

public class RegistryNotFoundException extends Exception
{	
	private static final long serialVersionUID = 1L;

	public RegistryNotFoundException(String registryType, String searchedKey)
	{
		super(String.format("%s no encontrado. Buscado con %s",registryType, searchedKey));
	}
}
