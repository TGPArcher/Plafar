package plafar.persistence.abstractions;

import java.util.List;

/**
 * This interface describes the basic operations of data persistence at which objects can be exposed
 * @param <T> This parameter is the object being persisted
 */
public interface Persistent<T> {
	/**
	 * This method adds a new object of type T to the database file
	 * @param object - This is the object being added to the database
	 * @return boolean - The status of the operation (true - success, false - failure)
	 */
	boolean addObject(T object);
	
	/**
	 * This method is used to retrieve an object from the database
	 * @param objectId - This is the ID of the desired object
	 * @return T The object which id is equal to objectId
	 */
	T getObject(int objectId);
	
	/**
	 * This method is used to edit an existing object from database
	 * @param object - This is the edited object
	 * @return boolean - The status of the operation (true - success, false - failure)
	 */
	boolean editObject(T object);
	
	/**
	 * This method is used to delete an existing object from the database
	 * @param objectId - This is the ID of the object to be deleted
	 * @return boolean - The status of the operation (true - success, false - failure)
	 */
	boolean deleteObject(int objectId);
	
	/**
	 * This method returns all the available objects of type T from the database
	 * @return List < T > - A list of all the objects
	 */
	List<T> getAllObjects();
}
